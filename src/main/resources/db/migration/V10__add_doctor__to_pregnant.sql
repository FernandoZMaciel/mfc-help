alter table pregnant
    add column id_doctor uuid,
    add constraint fk_doctor foreign key (id_doctor) references users (id) on delete set null;
