package modele.joueur;

public class Champion {

	private String championName;
	private String championImage;

	public Champion(String championName, String championImage) {
		this.championName = championName;
		this.championImage = championImage;
	}

	public String getChampionName() {
		return championName;
	}

	public void setChampionName(String championName) {
		this.championName = championName;
	}

	public String getChampionImage() {
		return championImage;
	}

	public void setChampionImage(String championImage) {
		this.championImage = championImage;
	}

}
