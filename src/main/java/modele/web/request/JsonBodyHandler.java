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

	public JsonBodyHandler(final Class<T> targetClass) {
		this.targetClass = targetClass;
	}

	@Override
	public BodySubscriber<Supplier<T>> apply(final ResponseInfo responseInfo) {
		return JsonBodyHandler.asJSON(this.targetClass);
	}


	public static <W> BodySubscriber<Supplier<W>> asJSON(final Class<W> targetType) {
		final var upstream = BodySubscribers.ofInputStream();

		return BodySubscribers.mapping(
				upstream,
				inputStream -> JsonBodyHandler.toSupplierOfType(inputStream, targetType));
	}

	public static <W> Supplier<W> toSupplierOfType(final InputStream inputStream, final Class<W> targetType) {
		return () -> {
			try (var stream = inputStream) {
				final var objectMapper = new ObjectMapper();
				return objectMapper.readValue(stream, targetType);
			} catch (final IOException e) {
				throw new UncheckedIOException(e);
			}
		};
	}
}