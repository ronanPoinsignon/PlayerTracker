package service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import modele.request.data.player.PlayerById;
import modele.request.data.player.PlayerByName;
import modele.request.result.SummonerDataResult;
import modele.request.result.SummonerInGameResult;
import modele.request.result.SummonersDataResult;
import modele.request.result.SummonersInGameResult;
import modele.web.request.ClientWeb;
import modele.web.request.StringConverter;

public class WebService implements IService {

	PropertiesService propertiesService;

	private static final String API = "api/";
	private static final String GET_SUMMONER_BY_ID = "summoner/getById/";
	private static final String GET_SUMMONER_BY_NAME = "summoner/getByName/";
	private static final String GET_SUMMONER_GAME = "summoner/getGame/";

	private static final String GET_SUMMONER_BY_IDS = "summoner/getByIds/";
	private static final String GET_SUMMONER_BY_NAMES = "summoner/getByNames/";
	private static final String GET_SUMMONER_GAMES = "summoner/getGames/";

	private final StringConverter converter = liste -> new Gson().toJsonTree(liste).getAsJsonArray().toString();
	private String baseUrl;

	public SummonerDataResult getSummonerByName(final PlayerByName data) {
		final var name = URLEncoder.encode(data.getPlayerName(), StandardCharsets.UTF_8).replaceAll("\\+", "%20");
		return getValue(SummonerDataResult.class, baseUrl + WebService.API + data.getServerId() + "/" + WebService.GET_SUMMONER_BY_NAME + name);
	}

	public SummonerDataResult getSummonerBySummonerId(final PlayerById data) {
		return getValue(SummonerDataResult.class, baseUrl + WebService.API + data.getServerId() + "/" + WebService.GET_SUMMONER_BY_ID + data.getPlayerId());
	}

	public SummonerInGameResult getSummonerGame(final PlayerById data) {
		return getValue(SummonerInGameResult.class, baseUrl + WebService.API + data.getServerId() + "/" + WebService.GET_SUMMONER_GAME + data.getPlayerId());
	}

	public SummonersDataResult getSummonerByNames(final List<PlayerByName> players) {
		final var nameMap = new HashMap<>();
		nameMap.put("summoners", converter.convert(players));
		return postValue(SummonersDataResult.class, baseUrl + WebService.API + WebService.GET_SUMMONER_BY_NAMES, nameMap);
	}

	public SummonersDataResult getSummonerBySummonerIds(final List<PlayerById> players) {
		final var idMap = new HashMap<>();
		idMap.put("summoners", converter.convert(players));
		return postValue(SummonersDataResult.class, baseUrl + WebService.API + WebService.GET_SUMMONER_BY_IDS, idMap);
	}

	public SummonersInGameResult getSummonerGames(final List<PlayerById> players) {
		final var idMap = new HashMap<>();
		idMap.put("summoners", converter.convert(players));
		return postValue(SummonersInGameResult.class, baseUrl + WebService.API + WebService.GET_SUMMONER_GAMES, idMap);
	}

	private <T> T getValue(final Class<T> clazz, final String url) {
		try {
			return getClient(clazz).get(url);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch(final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
	private <T> T postValue(final Class<T> clazz, final String url, final Map<Object, Object> params) {
		try {
			return getClient(clazz).post(url, params);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch(final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}

	private <T> ClientWeb<T> getClient(final Class<T> clazz){
		return new ClientWeb<>(clazz);
	}

	@Override
	public void init() {
		propertiesService = ServiceManager.getInstance(PropertiesService.class);
		baseUrl = propertiesService.get("base_url_api");
	}
}