ALTER TABLE public.respiratory_illness_case ADD COLUMN subject_id uuid;

ALTER TABLE public.respiratory_illness_case DROP CONSTRAINT IF EXISTS fk_respiratory_illness_case_subject_id;
ALTER TABLE public.respiratory_illness_case ADD CONSTRAINT fk_respiratory_illness_case_subject_id FOREIGN KEY (subject_id) REFERENCES public.respiratory_illness_case(id);