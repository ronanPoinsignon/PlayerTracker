package modele.joueur;

public class Champion {

	private String championName;
	private String base64ChampionImage;

	public Champion(final String championName, final String championImage) {
		this.championName = championName;
		base64ChampionImage = championImage;
	}

	public String getChampionName() {
		return championName;
	}

	public void setChampionName(final String championName) {
		this.championName = championName;
	}

	public String getBase64ChampionImage() {
		return base64ChampionImage;
	}

	public void setBase64ChampionImage(final String championImage) {
		base64ChampionImage = championImage;
	}

}