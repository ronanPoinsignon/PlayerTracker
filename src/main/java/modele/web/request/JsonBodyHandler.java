package modele.web.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonBodyHandler<T> implements BodyHandler<Supplier<T>> {

	private final Class<T> targetClass;

	public JsonBodyHandler(Class<T> targetClass) {
		this.targetClass = targetClass;
	}

	@Override
	public BodySubscriber<Supplier<T>> apply(ResponseInfo responseInfo) {
		return JsonBodyHandler.asJSON(this.targetClass);
	}


	public static <W> BodySubscriber<Supplier<W>> asJSON(Class<W> targetType) {
		BodySubscriber<InputStream> upstream = BodySubscribers.ofInputStream();

		return BodySubscribers.mapping(
				upstream,
				inputStream -> JsonBodyHandler.toSupplierOfType(inputStream, targetType));
	}

	public static <W> Supplier<W> toSupplierOfType(InputStream inputStream, Class<W> targetType) {
		return () -> {
			try (var stream = inputStream) {
				var objectMapper = new ObjectMapper();
				return objectMapper.readValue(stream, targetType);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		};
	}
}