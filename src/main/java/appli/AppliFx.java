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
import service.PropertiesService;
import service.ServiceManager;
import service.StageManager;
import service.TrayIconService;

public class AppliFx extends Application {

	private FileManager fm;
	private TrayIconService trayIconService;
	private AlertFxService alertService;
	PropertiesService ps;

	public static void start(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) throws IOException {
		initService(stage);
		try {
			checkAlreadyRunning();
		} catch (final ApplicationDejaEnCoursException e) {
			alertService.alert(e);
			return;
		}
		stage.getIcons().add(fm.getImageFromResource("images/icon.png"));
		stage.setTitle(ps.get("application_name"));
		final var file = fm.getFileFromResources("fxml/page_principale.fxml");
		final var loader = new FXMLLoader(file.toURI().toURL());
		stage.setScene(new Scene(loader.load()));
		trayIconService.createFXTrayIcon(stage);
		stage.show();
	}

	private void initService(final Stage stage) {
		fm = ServiceManager.getInstance(FileManager.class);
		trayIconService = ServiceManager.getInstance(TrayIconService.class);
		alertService = ServiceManager.getInstance(AlertFxService.class);
		ps = ServiceManager.getInstance(PropertiesService.class);
		ServiceManager.getInstance(StageManager.class).setCurrentStage(stage);
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