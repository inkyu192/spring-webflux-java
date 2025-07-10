package spring.webflux.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import spring.webflux.infrastructure.common.ResponseWriter;
import spring.webflux.infrastructure.properties.CorsProperties;
import spring.webflux.infrastructure.security.JwtAuthenticationFilter;
import spring.webflux.infrastructure.security.JwtProvider;
import spring.webflux.presentation.exception.handler.AccessDeniedExceptionHandler;
import spring.webflux.presentation.exception.handler.AuthenticationExceptionHandler;
import spring.webflux.presentation.exception.handler.JwtExceptionHandler;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(
		ServerHttpSecurity serverHttpSecurity,
		CorsProperties corsProperties,
		AuthenticationExceptionHandler authenticationExceptionHandler,
		AccessDeniedExceptionHandler accessDeniedExceptionHandler,
		JwtProvider jwtProvider,
		ResponseWriter responseWriter
	) {
		return serverHttpSecurity
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.logout(ServerHttpSecurity.LogoutSpec::disable)
			.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
			.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
			.exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
				.authenticationEntryPoint(authenticationExceptionHandler)
				.accessDeniedHandler(accessDeniedExceptionHandler)
			)
			.cors(corsSpec -> corsSpec.configurationSource(createCorsConfig(corsProperties)))
			.addFilterAt(new JwtAuthenticationFilter(jwtProvider), SecurityWebFiltersOrder.AUTHENTICATION)
			.addFilterAt(new JwtExceptionHandler(responseWriter), SecurityWebFiltersOrder.FIRST)
			.build();
	}

	private CorsConfigurationSource createCorsConfig(CorsProperties corsProperties) {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(corsProperties.getAllowedOrigins());
		config.setAllowedMethods(corsProperties.getAllowedMethods());
		config.setAllowedHeaders(corsProperties.getAllowedHeaders());
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
