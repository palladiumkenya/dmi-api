CREATE TABLE IF NOT EXISTS public.flagged_condition (
                                                  id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                                  case_id uuid NULL,
                                                  condition_id varchar(100) NULL,
                                                  condition_name varchar(250) NULL,
                                                  voided bool default false,
                                                  CONSTRAINT flagged_condition_pkey PRIMARY KEY (id)
);
ALTER TABLE public.flagged_condition DROP CONSTRAINT IF EXISTS fk_flagged_condition_case_id;
ALTER TABLE public.flagged_condition ADD CONSTRAINT fk_flagged_condition_case_id FOREIGN KEY (case_id) REFERENCES public.illness_case(id);