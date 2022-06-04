package service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import modele.request.result.SummonerDataResult;
import modele.request.result.SummonerInGameResult;
import modele.web.request.ClientWeb;

public class WebService implements IService {

	PropertiesService propertiesService;

	private static final String GET_SUMMONER_BY_ID = "api/summoner/getById/";
	private static final String GET_SUMMONER_BY_NAME = "api/summoner/getByName/";
	private static final String GET_SUMMONER_GAME = "api/summoner/getGame/";

	private String baseUrl;

	public SummonerDataResult getSummonerByName(String name) {
		name = URLEncoder.encode(name, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
		return getValue(SummonerDataResult.class, baseUrl + WebService.GET_SUMMONER_BY_NAME + name);
	}

	public SummonerDataResult getSummonerBySummonerId(final String id) {
		return getValue(SummonerDataResult.class, baseUrl + WebService.GET_SUMMONER_BY_ID + id);
	}

	public SummonerInGameResult getSummonerGame(final String id) {
		return getValue(SummonerInGameResult.class, baseUrl + WebService.GET_SUMMONER_GAME + id);
	}

	private <T> T getValue(final Class<T> clazz, final String url) {
		try {
			return getClient(clazz).get(url);
		} catch (IOException | InterruptedException e) {
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