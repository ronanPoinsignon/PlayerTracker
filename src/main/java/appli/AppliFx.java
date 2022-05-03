package appli;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.FileManager;
import service.ServiceManager;
import service.TrayIconService;

public class AppliFx extends Application {

	private FileManager fm = ServiceManager.getInstance(FileManager.class);
	private TrayIconService notificationService = ServiceManager.getInstance(TrayIconService.class);

	public static void start(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		stage.getIcons().add(fm.getImageFromResource("images/loupe.PNG"));
		stage.setTitle("Player tracker");
		var file = fm.getFileFromResources("fxml/page_principale.fxml");
		var loader = new FXMLLoader(file.toURI().toURL());
		Parent sceneVideo = loader.load();
		var scene = new Scene(sceneVideo);
		stage.setScene(scene);
		notificationService.createFXTrayIcon(stage);
		stage.show();
	}

}