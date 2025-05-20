package spring.webflux.presentation.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import spring.webflux.infrastructure.common.ResponseWriter;
import spring.webflux.infrastructure.common.UriFactory;

@Component
@RequiredArgsConstructor
public class AuthenticationExceptionHandler implements ServerAuthenticationEntryPoint {

	private final ResponseWriter responseWriter;

	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException exception) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
		problemDetail.setType(UriFactory.createApiDocUri(exchange, HttpStatus.UNAUTHORIZED));

		return responseWriter.writeResponse(exchange, problemDetail);
	}
}
