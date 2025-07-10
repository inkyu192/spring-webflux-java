package spring.webflux.domain.model.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.webflux.domain.model.entity.Member;

public interface MemberRepository {
	Flux<Member> findAll();
	Mono<Member> findById();
}
