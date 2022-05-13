package modele.event.action;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
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

	private Joueur joueur;

	public ActionEventRegarder(Joueur joueur) {
		this.joueur = joueur;
	}

	@Override
	public void handle(ActionEvent event) {
		if(joueur == null) {
			return;
		}
		var partie = joueur.getPartie();
		if(partie == null) {
			return;
		}
		var cmd = new String[] {"cmd.exe", "/c", "start", "\"\"", "\"League of Legends.exe\"", "\"spectator spectator.euw1.lol.riotgames.com:80 " +  partie.getEncryptionKey() + " " + partie.getGameId() + " EUW1\"", "-UseRads", "-GameBaseDir=..", "\"-Locale=fr_FR\"", "-SkipBuild", "-EnableCrashpad=true", "-EnableLNP"};
		var p = new ProcessBuilder(cmd);
		var possibleFile = findLolInstallation();
		if(possibleFile != null) {
			dm.setDirectory(possibleFile, "LoL");
			p.directory(possibleFile);
		} else {
			var f = dm.getDirectory("LoL");
			if(f == null) {
				f = dm.setFolderDirectory(sm.getCurrentStage(), "LoL");
			}
			if(f == null) {
				return;
			}
			p.directory(f);
		}
		try {
			p.start();
		} catch (IOException e) {
			alerteService.alert(e);
		}
	}

	private File findLolInstallation() {
		var possibleFiles = new File[] {
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
		for(File file : possibleFiles) {
			if(file.exists()) {
				return file;
			}
		}
		return null;
	}

}
