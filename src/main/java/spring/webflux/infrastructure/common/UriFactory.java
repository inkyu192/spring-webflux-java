package spring.webflux.infrastructure.common;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;

@EnableWebFlux
@Component
@RequiredArgsConstructor
public class UriFactory {

	public static URI createApiDocUri(ServerWebExchange exchange, int statusCode) {
		HttpStatus status = HttpStatus.resolve(statusCode);
		if (status == null) {
			return URI.create("about:blank");
		}
		return createApiDocUri(exchange, status);
	}

	public static URI createApiDocUri(ServerWebExchange exchange, HttpStatus status) {
		String baseUri = getBaseUri(exchange);
		String typeUri = String.format("%s/docs/index.html#%s", baseUri, status.name());
		return URI.create(typeUri);
	}

	private static String getBaseUri(ServerWebExchange exchange) {
		URI uri = exchange.getRequest().getURI();
		String scheme = uri.getScheme();
		String host = uri.getHost();
		int port = uri.getPort();

		boolean isDefaultPort = (scheme.equals("http") && port == 80)
			|| (scheme.equals("https") && port == 443);

		return isDefaultPort
			? String.format("%s://%s", scheme, host)
			: String.format("%s://%s:%d", scheme, host, port);
	}
}
