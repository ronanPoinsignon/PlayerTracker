package modele.event.action;

import javafx.event.ActionEvent;

/**
 * Evénement permettant une nouvelle sauvegarde.
 * @author ronan
 *
 */
public class ActionEventSaveSous extends ActionEventHandler {

	public ActionEventSaveSous() {
	}

	@Override
	public void handle(ActionEvent event) {
		//		DirectoryChooserManager.getInstance(Utils.DIRECTORY_CHOOSER_SAVE).setInitialDirectory(null);
		//		selection.sauvegarder();
	}
}
