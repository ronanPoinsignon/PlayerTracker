package modele.web.socketstrategy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class KeepConnection extends ASocketStrategy {

	private boolean isListening = false;

	public KeepConnection(String address, int port) {
		super(address, port);
	}

	@Override
	public void listen() {
		Thread t = new Thread(() -> {
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
					System.out.println("avant co");
					clientSocket = new Socket(address, port);
					System.out.println("après co");
					os = new DataOutputStream(clientSocket.getOutputStream());
					byte[] info = new byte[10000];
					InputStream input = clientSocket.getInputStream();
					String reponsePrec = "";
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
