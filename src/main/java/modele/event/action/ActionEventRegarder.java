package modele.event.action;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import modele.event.action.exception.NoLolInstallationFound;
import modele.event.action.exception.RegarderProcessException;
import modele.joueur.Joueur;
import service.AlertFxService;
import service.DirectoryManager;
import service.FileManager;
import service.ServiceManager;
import service.StageManager;

public class ActionEventRegarder extends ActionEventHandler {

	AlertFxService alerteService = ServiceManager.getInstance(AlertFxService.class);
	FileManager fm = ServiceManager.getInstance(FileManager.class);
	DirectoryManager dm = ServiceManager.getInstance(DirectoryManager.class);
	StageManager sm = ServiceManager.getInstance(StageManager.class);

	private final Joueur joueur;

	public ActionEventRegarder(final Joueur joueur) {
		this.joueur = joueur;
	}

	@Override
	public void handle(final ActionEvent event) {
		if(joueur == null) {
			return;
		}
		final var partie = joueur.getPartie();
		if(partie == null) {
			return;
		}
		final var possibleFile = setLoLDirectory(findLoLInstallation());
		if(possibleFile == null) {
			dm.setDirectory(null, "LoL");
			alerteService.alert(new NoLolInstallationFound());
			return;
		}
		final var t = new Thread(() -> {
			try {
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
				final var process = new ProcessBuilder(cmd).directory(possibleFile).start();
				if(process.exitValue() != 0) {
					alerteService.alert(new RegarderProcessException());
				}
			} catch (final IOException e) {
				alerteService.alert(e);
			}
		});
		t.setDaemon(true);
		t.start();
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
