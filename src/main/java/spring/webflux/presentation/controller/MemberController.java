package spring.webflux.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.webflux.application.service.MemberService;
import spring.webflux.domain.model.entity.Member;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	public Flux<Member> findAll() {
		log.info("MemberController: {}", Thread.currentThread().getName());
		return memberService.findAll().log();
	}

	@GetMapping("{id}")
	public Mono<Member> findById(@PathVariable Long id) {
		log.info("MemberController id: {}", id);
		log.info("MemberController: {}", Thread.currentThread().getName());
		return memberService.findById().log();
	}
}
