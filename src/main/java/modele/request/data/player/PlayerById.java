package modele.request.data.player;

public class PlayerById extends PlayerRequest {

	private final String id;

	public PlayerById(final String playerId, final String serverId) {
		super(serverId);
		id = playerId;
	}

	public String getPlayerId() {
		return id;
	}

}