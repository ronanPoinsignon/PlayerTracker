package modele.event.eventaction;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import modele.event.action.exception.NoLolInstallationFound;
import modele.event.action.exception.RegarderProcessException;
import modele.joueur.Joueur;
import service.DirectoryManager;
import service.SaveService;
import service.ServiceManager;
import service.StageManager;

public class RegarderEvent extends RunnableEvent {

	private final DirectoryManager dm = ServiceManager.getInstance(DirectoryManager.class);
	private final StageManager sm = ServiceManager.getInstance(StageManager.class);
	private final SaveService saveService = ServiceManager.getInstance(SaveService.class);

	private final Joueur element;

	public RegarderEvent(final Joueur element) {
		this.element = element;
	}

	@Override
	public Void execute() {
		if(element == null) {
			return null;
		}

		final var partie = element.getPartie();

		if(partie == null) {
			return null;
		}

		var directoryFile = dm.getDirectory("LoL");
		if(directoryFile == null) {
			directoryFile = setLoLDirectory(findLoLInstallation());
		}
		else {
			directoryFile = findExeFromDirectory(directoryFile); // le chemin peut exister tout en étant incorrect
		}

		if(directoryFile == null) {
			dm.setDirectory(null, "LoL");
			alerteService.alert(new NoLolInstallationFound());
			return null;
		}

		final var file = findExeFromDirectory(directoryFile);
		saveService.setLolPath(file);

		final var t = new Thread(() -> {
			final String[] cmd = {
					"cmd.exe",
					"/c",
					"start",
					"\"\"",
					"\"League of Legends.exe\"",
					"\"spectator spectator.euw1.lol.riotgames.com:80 " +  partie.getEncryptionKey() + " " + partie.getGameId() + " EUW1\"",
					"-UseRads",
					"-GameBaseDir=..",
					"\"-Locale=fr_FR\"",
					"-SkipBuild",
					"-EnableCrashpad=true",
					"-EnableLNP"
			};
			try {
				final var process = new ProcessBuilder(cmd).directory(file).start();
				// on attend que le process se finisse pour vérifier si la commande a fonctionné ou non
				process.onExit().thenRun(() -> {
					if(process.exitValue() != 0) {
						alerteService.alert(new RegarderProcessException());
					}
				});
			} catch (final IOException e) {
				alerteService.alert(e);
			}
		});
		t.setDaemon(true);
		t.start();

		return null;
	}

	private File setLoLDirectory(File possibleFile) {
		if(possibleFile != null) {
			dm.setDirectory(possibleFile, "LoL");
		} else {
			possibleFile = dm.getDirectory("LoL");
			if(possibleFile == null) {
				possibleFile = dm.setFolderDirectory(sm.getCurrentStage(), "LoL");
			}
		}
		return findExeFromDirectory(possibleFile);
	}

	private File findLoLInstallation() {
		final File[] possibleFiles = {
				new File("C:\\Riot Games\\League of Legends"),
				new File("D:\\Riot Games\\League of Legends"),
				new File("C:\\Program Files\\Riot Games\\League of Legends"),
				new File("C:\\Program Files (x86)\\Riot Games\\League of Legends"),
				new File("C:\\Program Files\\League of Legends"),
				new File("C:\\Program Files (x86)\\League of Legends"),
				new File("D:\\Program Files\\Riot Games\\League of Legends"),
				new File("D:\\Program Files (x86)\\Riot Games\\League of Legends"),
				new File("D:\\Program Files\\League of Legends"),
				new File("D:\\Program Files (x86)\\League of Legends")
		};
		for(final File file : possibleFiles) {
			if(file.exists()) {
				return file;
			}
		}
		return null;
	}

	private File findExeFromDirectory(final File directory) {
		if(directory == null) {
			return null;
		}
		if("Game".equals(directory.getName()) && "League of Legends".equals(directory.getParentFile().getName())) {
			return directory;
		}
		if("League of Legends".equals(directory.getName())) {
			return Stream.of(directory.listFiles()).filter(file -> "Game".equals(file.getName())).findFirst().orElse(null);
		}
		return null;
	}

}
