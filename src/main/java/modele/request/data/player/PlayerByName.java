package modele.request.data.player;

public class PlayerByName extends PlayerRequest {

	private final String name;

	public PlayerByName(final String name, final String server) {
		super(server);
		this.name = name;
	}

	public String getPlayerName() {
		return name;
	}

}
