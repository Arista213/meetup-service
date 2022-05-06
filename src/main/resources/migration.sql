--liquibase formatted sql

--changeset author:1
create table users
(
    id       bigint not null
        constraint users_pk
            primary key,
    fullname varchar,
    email    varchar
);
--rollback drop table users

--changeset author:2
create table meetups
(
    id    bigint       not null
        constraint meetups_pk
            primary key,
    name  varchar(100) not null,
    start varchar(100) not null,
    "end" varchar(100) not null
);
--rollback drop table meetups

--changeset author:3
create table invites
(
    user_id   bigint  not null
        constraint invites_users_id_fk
            references users
            on delete cascade,
    meetup_id bigint  not null
        constraint invites_meetups_id_fk
            references meetups
            on delete cascade,
    status    varchar not null
);

create index invites_user_id_index
    on invites (user_id);

create index invites_meetup_id_index
    on invites (meetup_id);
--rollback drop table invites
