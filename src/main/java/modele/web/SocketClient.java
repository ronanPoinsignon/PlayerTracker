package modele.web;

import java.io.IOException;

import modele.web.socketstrategy.ASocketStrategy;
import modele.web.socketstrategy.KeepConnection;

public class SocketClient implements SocketEvent {

	private ASocketStrategy socketStrategy;

	protected SocketClient(String address, int port) {
		socketStrategy = new KeepConnection(address, port) {
			@Override
			public void onReceive(String msg) {
				SocketClient.this.onReceive(msg);
			}
			@Override
			public void onError(Exception e) {
				SocketClient.this.onError(e);
			}
		};
	}

	public void listen() {
		socketStrategy.listen();
	}

	/**
	 * Permet d'écrire dans la socket.
	 * @param msg
	 * @throws IOException
	 */
	public void write(String msg) throws IOException {
		socketStrategy.write(msg);
	}
}