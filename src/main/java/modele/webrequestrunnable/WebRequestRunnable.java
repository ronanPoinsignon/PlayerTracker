package modele.webrequestrunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import modele.joueur.Joueur;
import modele.request.data.SummonerInGame;
import modele.request.data.player.PlayerById;
import modele.request.data.player.PlayerRequest;

public class WebRequestRunnable extends RequestPlayerData {

	private List<Joueur> joueurs = new ArrayList<>();
	private Predicate<List<? extends PlayerRequest>> filter;

	public WebRequestRunnable() {

	}

	@Override
	public Map<Joueur, SummonerInGame> request() {
		final var map = new HashMap<Joueur, SummonerInGame>();
		if(joueurs == null || filter == null) {
			return map;
		}

		final var data = joueurs.stream()
				.filter(joueur -> joueur != null && joueur.getPlayerId() != null && !joueur.getPlayerId().isBlank())
				.map(joueur -> new PlayerById(joueur.getPlayerId(), joueur.getServer().getServerId()))
				.collect(Collectors.toList());

		// Une liste vide à donner au webService entraine une erreur dans JsonBodyHandler#toSupplierOfType
		// Le mapper prendra alors la map vide pour une liste et n'arrivera pas à faire la conversion
		if(filter.test(data)) {
			return map;
		}

		final var result = webService.getSummonerGames(data);
		final var maps = result.getData().entrySet().stream().collect(Collectors.partitioningBy(joueur -> joueur.getValue() != null));

		final var failedPlayerIds = maps.get(false).stream().map(Entry::getKey).collect(Collectors.toList());
		final var goodPlayers = maps.get(true);

		goodPlayers.forEach(playerEntry -> {
			final var player = playerEntry.getValue();
			final var joueur = joueurs.stream().filter(j -> j.getPlayerId().equals(player.getSummoner_id())).findFirst().orElse(null);
			map.put(joueur, player);
		});
		return map;
	}

	public void setFilter(final Predicate<List<? extends PlayerRequest>> filter) {
		this.filter = filter;
	}

	public void setPlayers(final List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public boolean add(final Joueur joueur) {
		return joueurs.add(joueur);
	}

	public boolean remove(final Joueur joueur) {
		return joueurs.remove(joueur);
	}

}