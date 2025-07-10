package spring.webflux.infrastructure.security;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

	private final JwtProvider jwtProvider;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		log.info("JwtAuthenticationFilter: {}", Thread.currentThread().getName());

		String token = extractToken(exchange.getRequest());

		if (StringUtils.hasText(token)) {
			return chain.filter(exchange)
				.contextWrite(ReactiveSecurityContextHolder.withAuthentication(createAuthentication(token)));
		}

		return chain.filter(exchange);
	}

	private String extractToken(ServerHttpRequest request) {
		String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
			return authorization.replace("Bearer ", "");
		}

		return null;
	}

	private Authentication createAuthentication(String token) {
		Claims claims = jwtProvider.parseAccessToken(token);

		Long memberId = claims.get("memberId", Long.class);
		List<?> permissions = claims.get("permissions", List.class);

		List<SimpleGrantedAuthority> authorities = permissions.stream()
			.map(String::valueOf)
			.map(SimpleGrantedAuthority::new)
			.toList();

		return new UsernamePasswordAuthenticationToken(memberId, token, authorities);
	}
}
