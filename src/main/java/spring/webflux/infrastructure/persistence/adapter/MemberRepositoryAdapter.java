package spring.webflux.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.webflux.domain.model.entity.Member;
import spring.webflux.domain.model.repository.MemberRepository;
import spring.webflux.infrastructure.persistence.r2dbc.MemberR2dbcRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepository {

	private final MemberR2dbcRepository r2dbcRepository;

	@Override
	public Flux<Member> findAll() {
		log.info("MemberRepositoryAdapter: {}", Thread.currentThread().getName());
		return r2dbcRepository.findAll();
	}

	@Override
	public Mono<Member> findById() {
		log.info("MemberRepositoryAdapter: {}", Thread.currentThread().getName());
		return r2dbcRepository.findById(1L);
	}
}
