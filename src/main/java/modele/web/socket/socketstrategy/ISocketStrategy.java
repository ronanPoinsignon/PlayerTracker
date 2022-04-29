package modele.web.socket.socketstrategy;

import java.io.IOException;

public interface ISocketStrategy {

	void listen();
	void write(String msg) throws IOException;
}
