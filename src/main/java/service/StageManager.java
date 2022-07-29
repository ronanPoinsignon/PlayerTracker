package service;

import javafx.stage.Stage;

public class StageManager implements IService {

	Stage currentStage;

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(final Stage currentStage) {
		this.currentStage = currentStage;
	}

}