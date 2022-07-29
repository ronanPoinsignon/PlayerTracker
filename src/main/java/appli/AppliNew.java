package appli;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import appli.exception.ApplicationDejaEnCoursException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.AlertFxService;
import service.FileManager;
import service.ServiceManager;
import service.TrayIconService;

public class AppliNew extends Application {

	private final FileManager fm = ServiceManager.getInstance(FileManager.class);
	private final TrayIconService trayIconService = ServiceManager.getInstance(TrayIconService.class);
	private final AlertFxService alertService = ServiceManager.getInstance(AlertFxService.class);

	public static void start(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) throws IOException {
		stage.getIcons().add(fm.getImageFromResource("images/icon.png"));
		stage.setTitle("Player tracker");
		stage.setResizable(false);

		final var file = fm.getFileFromResources("fxml/main.fxml");
		final var loader = new FXMLLoader(file.toURI().toURL());

		final var scene = new Scene(loader.load());
		scene.getStylesheets().add(fm.getFileFromResources("css/main.css").toURI().toString());

		stage.setScene(scene);
		trayIconService.createFXTrayIcon(stage);
		stage.show();
	}

	private void checkAlreadyRunning() throws ApplicationDejaEnCoursException {
		try(var s = new ServerSocket(1044, 0, InetAddress.getByName("localhost"));) {
			// teste juste la possibilité d'ouvrir un serveur à cette adresse
		} catch (final IOException e1) {
			throw new ApplicationDejaEnCoursException();
		}
		final var t = new Thread(() -> {
			try(var server = new ServerSocket(1044, 0, InetAddress.getByName("localhost"));) {
				server.accept();
			} catch (final IOException e) {
				alertService.alert(e);
			}
		});
		t.setDaemon(true);
		t.start();
	}
}
