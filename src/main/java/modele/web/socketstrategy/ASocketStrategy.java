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
	}

	@Override
	public void write(String msg) throws IOException {
		System.out.println("write");
		os.writeBytes(msg);
	}
}
