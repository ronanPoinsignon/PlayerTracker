package modele.web.socketstrategy;

import java.io.IOException;
import java.io.InputStream;

public class PushConnection extends ASocketStrategy {

	protected PushConnection(String address, int port) {
		super(address, port);
	}

	@Override
	public void listen() {
		Thread t = new Thread(() -> {
			try {
				byte[] info = new byte[10000];
				InputStream input = clientSocket.getInputStream();
				String reponsePrec = "";
				String resultat = null;
				while (true) {
					input.read(info);
					resultat = new String(info).trim();
					if(resultat != null && !resultat.equals("") && !resultat.equals(reponsePrec)) {
						onReceive(resultat);
						System.out.println("finito pipo");
						return;
					}
				}
			} catch (IOException e) {
				onError(e);
			}
		});
		t.setDaemon(true);
		t.start();
	}
}
