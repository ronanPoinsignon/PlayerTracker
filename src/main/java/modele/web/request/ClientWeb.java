package modele.web.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

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
		HttpResponse<Supplier<T>> response = client.send(request, new JsonBodyHandler<>(clazz));
		if(response.statusCode() == HttpURLConnection.HTTP_NOT_FOUND) {
			throw new DataNotFoundException();
		}
		return response.body().get();
	}

	public T post(String url, Map<Object, Object> data) throws IOException, InterruptedException {
		var client = HttpClient.newHttpClient();

		var request = HttpRequest.newBuilder()
				.POST(ClientWeb.buildFormDataFromMap(data))
				.uri(URI.create(url))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.build();

		return client.send(request, new JsonBodyHandler<>(clazz)).body().get();
	}

	private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
		var builder = new StringBuilder("&");
		for (Entry<Object, Object> entry : data.entrySet()) {
			builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
			builder.append("=");
			builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
		}
		System.out.println(builder.toString());
		return HttpRequest.BodyPublishers.ofString(builder.toString());
	}

}
