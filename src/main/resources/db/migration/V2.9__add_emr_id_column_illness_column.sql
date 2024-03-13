ALTER TABLE public.illness_case ADD COLUMN emr_id uuid default null;

ALTER TABLE public.illness_case DROP CONSTRAINT IF EXISTS fk_illness_case_emr_id;
ALTER TABLE public.illness_case ADD CONSTRAINT fk_illness_case_emr_id FOREIGN KEY (emr_id) REFERENCES public.emr(id);