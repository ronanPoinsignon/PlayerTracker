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

	private FileManager fm;
	private AlertFxService alertFxService;
	private WebRequestScheduler scheduler;
	PropertiesService ps;
	DictionnaireService dictionnaire;

	private final HashMap<JoueurFx, BooleanProperty> binds = new HashMap<>();

	Stage stage;
	FXTrayIcon trayIcon;
	MenuItem miExit;

	public void createFXTrayIcon(final Stage stage) {
		this.stage = stage;
		URL iconURL;
		try {
			iconURL = fm.getFileFromResources("images/icon.png").toURI().toURL();
			trayIcon = new FXTrayIcon.Builder(stage, iconURL).show().build();
			miExit = new MenuItem();
			miExit.textProperty().bind(dictionnaire.getText("trayIconServiceQuitter"));
			miExit.setOnAction(e -> quitter());
			trayIcon.addMenuItem(miExit);
		} catch (final IOException e1) {
			alertFxService.alert(e1);
		}
	}

	public void quitter() {
		scheduler.stopScheduler();
		if(trayIcon != null) {
			trayIcon.removeMenuItem(miExit);
			trayIcon.clear();
		}
		Platform.exit();
		System.exit(0);
	}

	public void bind(final JoueurFx joueur) {
		if(binds.containsKey(joueur)) {
			return;
		}
		final BooleanProperty property = new SimpleBooleanProperty();
		property.bind(joueur.getIsConnecteProperty());
		property.addListener((obs, oldValue, newValue) -> {
			if(!newValue.booleanValue()) {
				return;
			}
			TrayIconService.this.notifier(joueur);
		});
		binds.put(joueur, property);
	}

	public void unbind(final JoueurFx joueur) {
		final var property = binds.get(joueur);
		if(property == null) {
			return;
		}
		property.unbind();
		binds.remove(joueur);
	}

	public void notifier(final Joueur joueur) {
		if(!SystemTray.isSupported() || stage.focusedProperty().getValue().booleanValue()) {
			return;
		}
		final var t = new Thread(() -> trayIcon.showMessage(ps.get("application_name"), joueur.getAppellation() + " " + dictionnaire.getText("trayIconServiceEnJeu").getValue()));
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
		alertFxService = ServiceManager.getInstance(AlertFxService.class);
		scheduler = ServiceManager.getInstance(WebRequestScheduler.class);
		ps = ServiceManager.getInstance(PropertiesService.class);
		dictionnaire = ServiceManager.getInstance(DictionnaireService.class);
	}
}