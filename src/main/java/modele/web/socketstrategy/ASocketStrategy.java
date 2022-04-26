package modele.web.socketstrategy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import modele.web.SocketEvent;

public abstract class ASocketStrategy implements ISocketStrategy, SocketEvent {

	protected String address;
	protected int port;
	protected Socket clientSocket;
	protected DataOutputStream os;

	protected ASocketStrategy(String address, int port) {
		this.address = address;
		this.port = port;
		try {
			clientSocket = new Socket(address, port);
			os = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void write(String msg) throws IOException {
		os.writeBytes(msg);
	}
}
