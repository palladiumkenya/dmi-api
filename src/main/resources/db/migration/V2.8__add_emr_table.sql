CREATE TABLE IF NOT EXISTS public.emr (
                                              id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                              emr_name varchar(150) NULL,
                                              voided bool default false,
                                              created_at timestamp NULL,
                                              updated_at timestamp NULL,
                                              CONSTRAINT emr_pkey PRIMARY KEY (id)
);

INSERT INTO public.emr (emr_name, created_at, updated_at)
VALUES ('Funsoft', current_timestamp, current_timestamp),
       ('MedBoss', current_timestamp, current_timestamp),
       ('CHIS', current_timestamp, current_timestamp),
       ('KenyaEMR', current_timestamp, current_timestamp),
       ('SmartCare', current_timestamp, current_timestamp),
       ('QAfya', current_timestamp, current_timestamp);


