package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modele.observer.Observateur;
import modele.web.socket.SocketClient;

public class SocketService implements IService {

	PropertiesService propertiesService = ServiceManager.getInstance(PropertiesService.class);

	private List<Observateur> observateurs = new ArrayList<>();

	private SocketClient client;

	public SocketService() throws IOException {
		String address = propertiesService.get("url");
		var port = Integer.parseInt(propertiesService.get("port"));
		client = new SocketClient(address, port) {

			@Override
			public void onReceive(String msg) {
				setConnected(true);
				notifyPlayerOnline(msg);
			}

			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
		};
		client.listen();
	}

	public void write(String msg) throws IOException {
		client.write(msg);
	}

	public boolean addObs(Observateur obs) {
		return observateurs.add(obs);
	}

	public boolean removeObs(Observateur obs) {
		return observateurs.remove(obs);
	}

	public void notifyPlayerOnline(String data) {
		observateurs.forEach(obs -> obs.notifyNewData(data));
	}

	public void setConnected(boolean isOnline) {
		System.out.println("en ligne : " + isOnline);
	}

}