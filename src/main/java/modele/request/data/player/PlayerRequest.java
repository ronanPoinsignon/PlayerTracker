package modele.request.data.player;

public class PlayerRequest {

	String server;

	public PlayerRequest(final String serverId) {
		server = serverId;
	}

	public String getServerId() {
		return server;
	}

}
