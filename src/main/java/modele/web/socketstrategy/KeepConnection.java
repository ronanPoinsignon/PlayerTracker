package modele.web.socketstrategy;

import java.io.IOException;

public class KeepConnection extends ASocketStrategy {

	private boolean isListening = false;

	public KeepConnection(String address, int port) {
		super(address, port);
	}

	@Override
	public void listen() {
		var t = new Thread(() -> {
			while(true) {
				if(isListening) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					continue;
				}
				isListening = true;
				try {
					var info = new byte[10000];
					var input = clientSocket.getInputStream();
					var reponsePrec = "";
					String resultat = null;
					while (true) {
						input.read(info);
						resultat = new String(info);
						info = new byte[10000];
						resultat = resultat.trim();
						if(resultat != null && !resultat.equals("") && !resultat.equals(reponsePrec)) {
							System.out.println(resultat);
							onReceive(resultat);
							reponsePrec = resultat;
						}
					}
				} catch (IOException e) {
					onError(e);
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void onError(Exception e) {
		isListening = false;
		e.printStackTrace();
	}

}
