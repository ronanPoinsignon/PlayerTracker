package modele.request.data;

import java.util.Arrays;

public class Game {

	private boolean success;
	private SummonerInGame[] summoners;

	public Game() {

	}

	public Game(final boolean success, final SummonerInGame[] summoners) {
		this.success = success;
		this.summoners = summoners;
	}

	public boolean isSuccess() {
		return success;
	}

	public SummonerInGame[] getSummoners() {
		return summoners;
	}

	@Override
	public String toString() {
		return "Game [success=" + success + ", summoners=" + Arrays.toString(summoners) + "]";
	}

}