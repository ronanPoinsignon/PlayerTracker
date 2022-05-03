package service;

import java.awt.SystemTray;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import com.dustinredmond.fxtrayicon.FXTrayIcon;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import modele.joueur.Joueur;
import modele.joueur.JoueurFx;

public class TrayIconService implements IService {

	private FileManager fm = ServiceManager.getInstance(FileManager.class);
	private AlertFxService alertFxService = ServiceManager.getInstance(AlertFxService.class);
	private WebRequestScheduler scheduler = ServiceManager.getInstance(WebRequestScheduler.class);

	private HashMap<JoueurFx, BooleanProperty> binds = new HashMap<>();

	Stage stage;
	FXTrayIcon trayIcon;
	MenuItem miExit;

	public void createFXTrayIcon(Stage stage) {
		this.stage = stage;
		URL iconURL;
		try {
			iconURL = fm.getFileFromResources("images/loupe.PNG").toURI().toURL();
			trayIcon = new FXTrayIcon.Builder(stage, iconURL).show().build();
			miExit = new MenuItem("Quitter");
			miExit.setOnAction(e -> quitter());
			trayIcon.addMenuItem(miExit);
		} catch (IOException e1) {
			alertFxService.alert(e1);
		}
	}

	public void quitter() {
		scheduler.stopScheduler();
		trayIcon.removeMenuItem(miExit);
		trayIcon.clear();
		Platform.exit();
		System.exit(0);
	}

	public void bind(JoueurFx joueur) {
		if(binds.containsKey(joueur)) {
			return;
		}
		BooleanProperty property = new SimpleBooleanProperty();
		property.bind(joueur.getIsConnecteProperty());
		property.addListener((obs, oldValue, newValue) -> {
			if(!newValue.booleanValue()) {
				return;
			}
			TrayIconService.this.notifier(joueur);
		});
		binds.put(joueur, property);
	}

	public void unbind(JoueurFx joueur) {
		BooleanProperty property = binds.get(joueur);
		if(property == null) {
			return;
		}
		property.unbind();
		binds.remove(joueur);
	}

	public void notifier(Joueur joueur) {
		if(!SystemTray.isSupported()) {
			return;
		}
		var t = new Thread(() -> trayIcon.showMessage("Player tracker", joueur.getAppellation() + " est en jeu"));
		t.setDaemon(true);
		t.start();
	}
}
