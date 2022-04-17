package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modele.observer.Observateur;
import modele.web.SocketClient;

public class SocketService implements IService {

	PropertiesService propertiesService = ServiceManager.getInstance(PropertiesService.class);

	private List<Observateur> observateurs = new ArrayList<>();

	private SocketClient client;

	public SocketService() throws IOException {
		String address = propertiesService.get("url");
		int port = Integer.parseInt(propertiesService.get("port"));
		client = new SocketClient(address, port) {

			@Override
			public void onReceive(String msg) {
				System.out.println(msg);
			}

			@Override
			public void onError(Exception e) {
				System.out.println("erreur");
				e.printStackTrace();
				super.onError(e);
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

	public void notifyPlayerOnline(String id, boolean inGame) {
		observateurs.forEach(obs -> obs.notifyPlayerInGame(id, inGame));
	}

}