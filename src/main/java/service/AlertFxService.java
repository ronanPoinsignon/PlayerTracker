package service;

public class AlertFxService implements IService {

	public void alert(Exception e) {
		e.printStackTrace();
	}

	public void alert(NoPlayerFoundException e) {
		System.out.println(e.getMessage());
	}
}
