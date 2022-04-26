package appli;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.FileManager;
import service.ServiceManager;
import service.SocketService;

public class AppliFx extends Application {

	private FileManager fm = ServiceManager.getInstance(FileManager.class);
	private SocketService socketService = ServiceManager.getInstance(SocketService.class);

	public static void start(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		var th = new Thread(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			while(true) {
				try {
					var t = "['type' => 'tracker:players','players' => ['a5Gwv5NZxa6WVtJKOFBy8sX7uBagkv0pODShPv9IjPTtslE', 'l2b1wQIobGI3FfnM_HM_9ppRWE9WTip-v3hhbpOX2MX3mFA', 'lEMlQTR5NnNM61BLdvLNVvgHVqDpUO8pTCewgSGyD42wXGc']]";
					System.out.println("write avant");
					socketService.write(t);
					System.out.println("write apres");
				} catch(Exception e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		th.setDaemon(true);
		//th.start();

		stage.getIcons().add(fm.getImageFromResource("images/loupe.PNG"));
		stage.setTitle("Player tracker");
		var file = fm.getFileFromResources("fxml/page_principale.fxml");
		var loader = new FXMLLoader(file.toURI().toURL());
		Parent sceneVideo = loader.load();
		var scene = new Scene(sceneVideo);
		stage.setScene(scene);
		stage.show();
	}

}