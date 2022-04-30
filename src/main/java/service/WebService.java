package service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import modele.request.result.SummonerDataResult;
import modele.request.result.SummonerInGameResult;
import modele.web.request.ClientWeb;

public class WebService implements IService {

	PropertiesService propertiesService = ServiceManager.getInstance(PropertiesService.class);

	private static final String GET_SUMMONER_BY_ID = "api/summoner/getById/";
	private static final String GET_SUMMONER_BY_NAME = "api/summoner/getByName/";
	private static final String GET_SUMMONER_GAME = "api/summoner/getGame/";

	private final String baseUrl;

	public WebService() {
		baseUrl = propertiesService.get("base_url_api");
	}

	public SummonerDataResult getSummonerByName(String name) {
		name = URLEncoder.encode(name, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
		return getValue(SummonerDataResult.class, baseUrl + WebService.GET_SUMMONER_BY_NAME + name);
	}

	public SummonerDataResult getSummonerBySummonerId(String id) {
		return getValue(SummonerDataResult.class, baseUrl + WebService.GET_SUMMONER_BY_ID + id);
	}

	public SummonerInGameResult getSummonerGame(String id) {
		return getValue(SummonerInGameResult.class, baseUrl + WebService.GET_SUMMONER_GAME + id);
	}

	private <T> T getValue(Class<T> clazz, String url) {
		try {
			return getClient(clazz).get(url);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> ClientWeb<T> getClient(Class<T> clazz){
		return new ClientWeb<>(clazz);
	}
}
