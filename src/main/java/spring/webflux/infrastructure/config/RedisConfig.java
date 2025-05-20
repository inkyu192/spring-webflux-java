package spring.webflux.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import lombok.RequiredArgsConstructor;
import spring.webflux.infrastructure.properties.RedisProperties;

// @RequiredArgsConstructor
// @Configuration(proxyBeanMethods = false)
// public class RedisConfig {
//
// 	private final RedisProperties redisProperties;
//
// 	@Bean
// 	public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
// 		return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
// 	}
//
// 	@Bean
// 	public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(
// 		ReactiveRedisConnectionFactory connectionFactory
// 	) {
// 		RedisSerializationContext<String, String> context = RedisSerializationContext
// 			.<String, String>newSerializationContext(RedisSerializer.string())
// 			.value(RedisSerializer.string())
// 			.build();
//
// 		return new ReactiveRedisTemplate<>(connectionFactory, context);
// 	}
// }
