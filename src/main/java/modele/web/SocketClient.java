package modele.web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketClient implements SocketEvent {

	private String address;
	private int port;
	private Socket clientSocket;
	private DataOutputStream os;

	protected SocketClient(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void listen() {
		Thread t = new Thread(() -> {
			try {
				clientSocket = new Socket(address, port);
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
		});
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Permet d'écrire dans la socket.
	 * @param msg
	 * @throws IOException
	 */
	public void write(String msg) throws IOException {
		if(os != null) {
			os.writeBytes(msg);
		}
	}

	@Override
	public void onError(Exception e) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		listen();
	}
}