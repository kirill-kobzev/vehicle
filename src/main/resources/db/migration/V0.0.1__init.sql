CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;
CREATE TABLE companies (
    id   INT4 NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE drivers (
    id         INT4 NOT NULL,
    name       VARCHAR(255),
    vehicle_id INT4,
    PRIMARY KEY (id)
);
CREATE TABLE vehicles (
    id            INT4 NOT NULL,
    color         VARCHAR(255),
    license_plate VARCHAR(255),
    model         VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE vehicle_company (
    vehicle_id INT4 NOT NULL,
    company_id INT4 NOT NULL
);
ALTER TABLE if EXISTS drivers ADD CONSTRAINT drivers_fk FOREIGN KEY (vehicle_id) REFERENCES vehicles;
ALTER TABLE if EXISTS vehicle_company ADD CONSTRAINT vehicle_company_company_id_fk FOREIGN KEY (company_id) REFERENCES companies;
ALTER TABLE if EXISTS vehicle_company ADD CONSTRAINT vehicle_company_vehicle_id_fk FOREIGN KEY (vehicle_id) REFERENCES vehicles;