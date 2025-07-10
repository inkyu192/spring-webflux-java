package spring.webflux.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.webflux.domain.model.entity.Member;
import spring.webflux.domain.model.repository.MemberRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Flux<Member> findAll() {
		log.info("MemberService: {}", Thread.currentThread().getName());
		return memberRepository.findAll();
	}

	public Mono<Member> findById() {
		log.info("MemberService: {}", Thread.currentThread().getName());
		return memberRepository.findById();
	}
}
