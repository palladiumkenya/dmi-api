ALTER TABLE public.illness_case
    ADD COLUMN final_outcome varchar(255),
    ADD COLUMN final_outcome_date timestamp,
    ADD COLUMN status varchar(50);