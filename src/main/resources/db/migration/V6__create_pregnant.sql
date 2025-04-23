create table pregnant (
    id uuid primary key,
    name varchar(255) not null,
    birth_date date not null,
    cns varchar(15),
    last_menstruation_date date,
    first_ultrasound_date date,
    gestational_age_in_weeks integer,
    gestational_age_in_days integer,
    job_position varchar(255),
    planned_pregnancy boolean,
    dental_avaliation boolean,
    number_of_previous_pregnancies integer,
    race varchar(50),
    baby_id uuid,
    constraint fk_baby foreign key (baby_id) references pregnant_baby (id) on delete set null
);
