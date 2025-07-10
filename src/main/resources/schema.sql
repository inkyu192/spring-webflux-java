drop sequence if exists member_seq;
create sequence member_seq start with 1 increment by 50;

drop table if exists member;
create table member
(
    birth_date date,
    created_at timestamp(6) with time zone,
    id         bigint not null,
    updated_at timestamp(6) with time zone,
    account    varchar(255),
    name       varchar(255),
    password   varchar(255),
    phone      varchar(255),
    primary key (id)
);