drop table if exists jwt;

create table jwt (
                        username varchar(255) not null,
                        token varchar(255),
                        primary key (username)
);