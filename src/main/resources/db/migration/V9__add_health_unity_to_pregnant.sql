alter table pregnant
    add column id_health_unity bigint,
    add constraint fk_health_unity foreign key (id_health_unity) references health_unity (id) on delete set null;
