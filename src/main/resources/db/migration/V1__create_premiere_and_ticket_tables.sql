drop table if exists tickets;
drop table if exists premieres;

create table premieres (
                        id uuid not null,
                        name varchar(255),
                        description varchar(255),
                        age_category integer,
                        quantity_of_seats integer,
                        seats_used integer,
                        primary key (id)
);

create table tickets (
                         id uuid not null,
                         place varchar(255),
                         premiere uuid,
                         primary key (id),
                         foreign key (premiere) references premieres(id)
);


insert into premieres values ( 'A0EEBC99-9C0B-4EF8-BB6D-6BB9BD380A11', 'ABC', '123a', 6, 400, 100 );
insert into premieres values ( 'A0EEBC99-9C0B-4EF8-BB6D-6BB9BD380A22', 'DEF', 'qwerty', 16, 1000, 600 );
insert into premieres values ( 'A0EEBC99-9C0B-4EF8-BB6D-6BB9BD380A33', 'GHI', '31415926535', 18, 2000, 10 );

insert into tickets values ( 'A1EEBC99-9C0B-4EF8-BB6D-6BB9BD380A11', '4a-12', 'A0EEBC99-9C0B-4EF8-BB6D-6BB9BD380A11');
insert into tickets values ( 'A2EEBC99-9C0B-4EF8-BB6D-6BB9BD380A11', '2b-48', 'A0EEBC99-9C0B-4EF8-BB6D-6BB9BD380A11');
insert into tickets values ( 'A3EEBC99-9C0B-4EF8-BB6D-6BB9BD380A11', '5a-26', 'A0EEBC99-9C0B-4EF8-BB6D-6BB9BD380A22');

select * from premieres;
select * from tickets;
