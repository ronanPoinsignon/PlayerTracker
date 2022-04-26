package modele.web.socketstrategy;

import java.io.IOException;

public class PushConnection extends ASocketStrategy {

	protected PushConnection(String address, int port) {
		super(address, port);
	}

	@Override
	public void listen() {
		var t = new Thread(() -> {
			try {
				if(clientSocket == null) {
					connect();
				}
				var info = new byte[10000];
				var input = clientSocket.getInputStream();
				var reponsePrec = "";
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
