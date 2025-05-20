package spring.webflux.infrastructure.common;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseWriter {

	private final ObjectMapper objectMapper;

	public Mono<Void> writeResponse(ServerWebExchange exchange, ProblemDetail problemDetail) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.valueOf(problemDetail.getStatus()));
		response.getHeaders().set(
			HttpHeaders.CONTENT_TYPE,
			"%s;charset=%s".formatted(MediaType.APPLICATION_JSON_VALUE, StandardCharsets.UTF_8)
		);

		try {
			byte[] bytes = objectMapper.writeValueAsBytes(problemDetail);
			DataBuffer buffer = response.bufferFactory().wrap(bytes);
			return response.writeWith(Mono.just(buffer));
		} catch (JsonProcessingException e) {
			log.error("Failed to write error response", e);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			return response.setComplete();
		}
	}
}
