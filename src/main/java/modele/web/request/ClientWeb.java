package modele.web.request;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class ClientWeb<T> {

	private Class<T> clazz;

	public ClientWeb(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T get(String url) throws IOException, InterruptedException {
		var client = HttpClient.newHttpClient();

		var request = HttpRequest.newBuilder(
				URI.create(url))
				.header("accept", "application/json")
				.build();

		return client.send(request, new JsonBodyHandler<>(clazz)).body().get();
	}

}
