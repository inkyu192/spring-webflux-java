package spring.webflux.domain.model.entity;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

@Table("member")
public class Member {

	@Id
	private Long id;
	private String account;
	private String password;
	private String name;
	private String phone;
	private LocalDate birthDate;

	@CreatedDate
	private Instant createdAt;

	@LastModifiedDate
	private Instant updatedAt;
}
