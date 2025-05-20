package spring.webflux.presentation.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import spring.webflux.infrastructure.common.ResponseWriter;
import spring.webflux.infrastructure.common.UriFactory;

@Component
@RequiredArgsConstructor
public class AccessDeniedExceptionHandler implements ServerAccessDeniedHandler {

	private final ResponseWriter responseWriter;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException exception) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
		problemDetail.setType(UriFactory.createApiDocUri(exchange, status));

		return responseWriter.writeResponse(exchange, problemDetail);
	}
}
