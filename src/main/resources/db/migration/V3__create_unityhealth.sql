create table health_unity (
    id serial primary key,
    cnes bigint not null,
    name varchar(255) not null,
    zipcode varchar(20),
    address_number varchar(50)
);