ALTER TABLE pregnant
DROP COLUMN number_of_previous_pregnancies;

ALTER TABLE pregnant
    ADD COLUMN vaginal_deliveries INTEGER,
    ADD COLUMN cesarean_sections INTEGER,
    ADD COLUMN abortions INTEGER,
    ADD COLUMN fetal_deaths INTEGER;