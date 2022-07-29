package modele.event.action;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import service.DirectoryManager;
import service.ServiceManager;

/**
 *
 * @author ronan
 *
 */
public class ActionEventSetLolDirectory extends ActionEventHandler {

	private final DirectoryManager dm = ServiceManager.getInstance(DirectoryManager.class);
	private final Stage stage;

	public ActionEventSetLolDirectory(final Stage stage) {
		this.stage = stage;
	}

	@Override
	public void handle(final ActionEvent event) {
		final var directory = dm.getDirectory("LoL");
		dm.setDirectory(null, "LoL");
		final var newDirectory = dm.setFileDirectory(stage, "LoL");
		if(newDirectory == null) {
			dm.setDirectory(directory, "LoL");
		}
	}
}