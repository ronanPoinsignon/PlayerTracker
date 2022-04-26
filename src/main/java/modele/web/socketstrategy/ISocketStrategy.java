package modele.web.socketstrategy;

import java.io.IOException;

public interface ISocketStrategy {

	void listen();
	void write(String msg) throws IOException;
}
