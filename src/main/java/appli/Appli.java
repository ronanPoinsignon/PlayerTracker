package appli;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.FileManager;

public class Appli extends Application {

	private FileManager fm = FileManager.getInstance();

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		stage.getIcons().add(fm.getImageFromResource("images/loupe.PNG"));
		stage.setTitle("Player tracker");
		File file = fm.getFileFromResources("fxml/page_principale.fxml");
		FXMLLoader loader = new FXMLLoader(file.toURI().toURL());
		Parent sceneVideo = loader.load();
		Scene scene = new Scene(sceneVideo);
		stage.setScene(scene);
		stage.show();
	}

}