package spring.webflux.presentation.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import spring.webflux.infrastructure.common.ResponseWriter;
import spring.webflux.infrastructure.common.UriFactory;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionHandler implements WebFilter {

	private final ResponseWriter responseWriter;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		log.info("JwtExceptionHandler: {}", Thread.currentThread().getName());

		return chain.filter(exchange)
			.onErrorResume(JwtException.class, ex ->
				handleException(exchange, HttpStatus.UNAUTHORIZED, ex.getMessage())
			)
			.onErrorResume(Exception.class, ex -> {
				log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
				return handleException(exchange, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
			});
	}

	private Mono<Void> handleException(ServerWebExchange exchange, HttpStatus status, String message) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
		problemDetail.setType(UriFactory.createApiDocUri(exchange, status));

		return responseWriter.writeResponse(exchange, problemDetail);
	}
}
