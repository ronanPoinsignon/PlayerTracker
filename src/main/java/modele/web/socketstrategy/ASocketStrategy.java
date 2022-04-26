package modele.web.socketstrategy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
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
	}

	@Override
	public void write(String msg) throws IOException {
		if(clientSocket == null) {
			connect();
		}
		os.write(msg.getBytes());
	}

	public void connect() {
		try {
			clientSocket = new Socket();
			clientSocket.setSoTimeout(5000);
			clientSocket.connect(new InetSocketAddress(address, port));
			os = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
