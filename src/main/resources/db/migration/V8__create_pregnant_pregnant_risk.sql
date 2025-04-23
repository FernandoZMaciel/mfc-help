create table pregnant_pregnant_risk (
    pregnant_id uuid not null,
    pregnant_risk_id bigint not null,
    primary key (pregnant_id, pregnant_risk_id),
    constraint fk_pregnant foreign key (pregnant_id) references pregnant (id) on delete cascade,
    constraint fk_pregnant_risk foreign key (pregnant_risk_id) references pregnant_risk (id) on delete cascade
);
