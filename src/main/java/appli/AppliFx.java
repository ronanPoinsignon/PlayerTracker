package appli;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import appli.exception.ApplicationDejaEnCoursException;
import appli.exception.BadOsException;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.save.DataObject;
import service.AlertFxService;
import service.DictionnaireService;
import service.DirectoryManager;
import service.FileManager;
import service.LangagesManager;
import service.LoadService;
import service.PropertiesService;
import service.ServiceManager;
import service.StageManager;
import service.TrayIconService;
import service.exception.SauvegardeCorrompueException;

public class AppliFx extends Application {

	private final FileManager fm = ServiceManager.getInstance(FileManager.class);
	private final TrayIconService trayIconService = ServiceManager.getInstance(TrayIconService.class);
	private final AlertFxService alertService = ServiceManager.getInstance(AlertFxService.class);
	private final PropertiesService ps = ServiceManager.getInstance(PropertiesService.class);
	private final DirectoryManager directoryManager = ServiceManager.getInstance(DirectoryManager.class);
	private final LoadService loadService = ServiceManager.getInstance(LoadService.class);
	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);
	private final LangagesManager langagesManager = ServiceManager.getInstance(LangagesManager.class);

	public static void start(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) throws IOException {
		DataObject data = null;
		try {
			data = loadService.load();
		} catch (final SauvegardeCorrompueException e) {
			dictionnaire.setLangue(langagesManager.getDefaultLangage());
			alertService.alert(e);
			data = new DataObject();
		} catch (final IOException e) {
			dictionnaire.setLangue(langagesManager.getDefaultLangage());
			alertService.alert(e);
			return;
		}

		final var joueurs = data.getJoueurs();

		final var options = data.getOptions();
		if(options.getLolPath() != null) {
			directoryManager.setDirectory(new File(options.getLolPath()), "LoL");
		}

		if(options.getLangage() != null) {
			dictionnaire.setLangue(options.getLangage());
		}
		else {
			dictionnaire.setLangue(langagesManager.getDefaultLangage());
		}

		try {
			checkAlreadyRunning();
			checkOs();
		} catch (final ApplicationDejaEnCoursException | BadOsException e) {
			alertService.alert(e);
			return;
		}
		
		ServiceManager.getInstance(StageManager.class).setCurrentStage(stage);

		stage.getIcons().add(fm.getImageFromResource("images/icon.png"));
		stage.setTitle(ps.get("application_name"));
		stage.setMinWidth(400);
		stage.setMinHeight(600);

		stage.setWidth(1100);
		stage.setHeight(700);

		final var file = fm.getFileFromResources("fxml/main.fxml");
		final var loader = new FXMLLoader(file.toURI().toURL());

		final var scene = new Scene(loader.load());
		scene.getStylesheets().add(fm.getFileFromResources("css/main.css").toURI().toString());

		stage.setScene(scene);
		((MainController) loader.getController()).setJoueurs(joueurs);
		trayIconService.createFXTrayIcon(stage);
		stage.show();
	}

	private void checkAlreadyRunning() throws ApplicationDejaEnCoursException {
		try(var s = new ServerSocket(1044, 0, InetAddress.getByName("localhost"))) {
			// teste juste la possibilité d'ouvrir un serveur à cette adresse
		} catch (final IOException e1) {
			throw new ApplicationDejaEnCoursException();
		}
		final var t = new Thread(() -> {
			try(var server = new ServerSocket(1044, 0, InetAddress.getByName("localhost"))) {
				server.accept();
			} catch (final IOException e) {
				alertService.alert(e);
			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void checkOs() {
		final var os = System.getProperty("os.name");
		if( os == null || !os.startsWith("Windows")) {
			throw new BadOsException();
		}
	}

}