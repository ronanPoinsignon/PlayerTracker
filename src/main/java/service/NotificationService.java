package service;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import modele.joueur.Joueur;
import modele.joueur.JoueurFx;

public class NotificationService implements IService {

	private FileManager fm = ServiceManager.getInstance(FileManager.class);
	private AlertFxService alertFxService = ServiceManager.getInstance(AlertFxService.class);

	private HashMap<JoueurFx, BooleanProperty> binds = new HashMap<>();

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
			NotificationService.this.notifier(joueur);
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

	private void notifier(Joueur joueur) {
		Thread t = new Thread(() -> {
			try {
				String nom = joueur.getNom() != null && !joueur.getNom().trim().isEmpty() ? joueur.getNom() : joueur.getPseudo();
				SystemTray tray = SystemTray.getSystemTray();
				Image image = Toolkit.getDefaultToolkit().createImage(Files.readAllBytes(fm.getFileFromResources("images/exclamation.png").toPath()));
				TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
				trayIcon.setImageAutoSize(true);
				tray.add(trayIcon);
				trayIcon.displayMessage("Player tracker", nom + " est en jeu", MessageType.INFO);
			}
			catch(AWTException | IOException  e) {
				alertFxService.alert(e);
			}
		});
		t.setDaemon(true);
		t.start();
	}
}
