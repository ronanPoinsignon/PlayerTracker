package modele.webrequestrunnable.builder;

import modele.webrequestrunnable.RequestPlayerData;

public abstract class WebRequestBuilder<T extends RequestPlayerData> {

	public abstract T build();

	public static WebRequestBuilderForPlayerList withPlayerList() {
		return new WebRequestBuilderForPlayerList();
	}

	public static WebRequestBuilderForPlayer withPlayer() {
		return new WebRequestBuilderForPlayer();
	}

}
