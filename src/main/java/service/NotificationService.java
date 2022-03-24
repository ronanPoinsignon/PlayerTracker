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
import utils.FileManager;

public class NotificationService extends Service {

	private FileManager fm = FileManager.getInstance();
	private HashMap<JoueurFx, BooleanProperty> binds = new HashMap<>();

	public void bind(JoueurFx joueur) {
		BooleanProperty property = new SimpleBooleanProperty();
		property.bind(joueur.getIsConnecteProperty());
		property.addListener((obs, oldValue, newValue) -> {
			if(!newValue.booleanValue()) {
				return;
			}
			try {
				NotificationService.this.notifier(joueur);
			} catch (AWTException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	private void notifier(Joueur joueur) throws AWTException, IOException {
		String nom = joueur.getNom() != null && !joueur.getNom().trim().isEmpty() ? joueur.getNom() : joueur.getPseudo();
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage(Files.readAllBytes(fm.getFileFromResources("images/exclamation.png").toPath()));
		TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("System tray icon demo");
		tray.add(trayIcon);
		trayIcon.displayMessage("Player tracker", nom + " est en jeu", MessageType.INFO);
	}
}
