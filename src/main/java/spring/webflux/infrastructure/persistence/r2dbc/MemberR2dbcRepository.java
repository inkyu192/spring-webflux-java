package spring.webflux.infrastructure.persistence.r2dbc;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import spring.webflux.domain.model.entity.Member;

public interface MemberR2dbcRepository extends R2dbcRepository<Member, Long> {
}
