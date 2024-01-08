
CREATE TABLE IF NOT EXISTS public.batch_operations (
                                         id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                         input_count int4 NULL,
                                         status varchar(10) NULL,
                                         processed_count int4 NULL,
                                         start_time timestamp NULL,
                                         complete_time timestamp NULL,
                                         mfl_code varchar(10) NULL,
                                         external_reference_id varchar(150) NULL,
                                         CONSTRAINT batch_operations_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.respiratory_illness_case (
                                                 id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                                 batch_id uuid NULL,
                                                 nupi varchar(50) NULL,
                                                 patient_unique_id varchar(100) NULL,
                                                 visit_unique_id varchar(100) NULL,
                                                 mfl_code varchar(10) NULL,
                                                 interview_date timestamp NULL,
                                                 emr varchar(20) NULL,
                                                 date_of_birth date NULL,
                                                 age_in_months int4 NULL,
                                                 age_in_years int4 NULL,
                                                 sex varchar(10) NULL,
                                                 address varchar(100) NULL,
                                                 admission_date date NULL,
                                                 outpatient_date date NULL,
                                                 temperature numeric NULL,
                                                 county varchar(50) NULL,
                                                 sub_county varchar(30) NULL,
                                                 created_at timestamp NULL,
                                                 updated_at timestamp NULL,
                                                 load_date timestamp NULL,
                                                 voided bool NULL,
                                                 CONSTRAINT respiratory_illness_case_pkey PRIMARY KEY (id)
);
ALTER TABLE public.respiratory_illness_case DROP CONSTRAINT IF EXISTS fk_respiratory_illness_case_batch_id;
ALTER TABLE public.respiratory_illness_case ADD CONSTRAINT fk_respiratory_illness_case_batch_id FOREIGN KEY (batch_id) REFERENCES public.batch_operations(id);

CREATE TABLE IF NOT EXISTS public.complaint (
                                  id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                  case_id uuid NULL,
                                  complaint_id varchar(50) NULL,
                                  complaint varchar(100) NULL,
                                  voided bool NULL,
                                  onset_date date NULL,
                                  duration int4 NULL,
                                  CONSTRAINT complaint_pkey PRIMARY KEY (id)
);

ALTER TABLE public.complaint DROP CONSTRAINT IF EXISTS fk_complaint_case_id;
ALTER TABLE public.complaint ADD CONSTRAINT fk_complaint_case_id FOREIGN KEY (case_id) REFERENCES public.respiratory_illness_case(id);


CREATE TABLE IF NOT EXISTS public.diagnosis (
                                  id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                  case_id uuid NULL,
                                  diagnosis_id varchar(50) NULL,
                                  diagnosis varchar(100) NULL,
                                  voided bool NULL,
                                  diagnosis_date timestamp NULL,
                                  CONSTRAINT diagnosis_pkey PRIMARY KEY (id)
);
ALTER TABLE public.diagnosis DROP CONSTRAINT IF EXISTS fk_diagnosis_case_id;
ALTER TABLE public.diagnosis ADD CONSTRAINT fk_diagnosis_case_id FOREIGN KEY (case_id) REFERENCES public.respiratory_illness_case(id);


CREATE TABLE IF NOT EXISTS public.lab (
                            id uuid NOT NULL DEFAULT uuid_generate_v4(),
                            case_id uuid NULL,
                            order_id varchar(50) NULL,
                            test_result varchar(100) NULL,
                            voided bool NULL,
                            lab_date timestamp NULL,
                            test_name varchar(150) NULL,
                            CONSTRAINT lab_pkey PRIMARY KEY (id)
);

ALTER TABLE public.lab DROP CONSTRAINT IF EXISTS fk_lab_case_id;
ALTER TABLE public.lab ADD CONSTRAINT fk_lab_case_id FOREIGN KEY (case_id) REFERENCES public.respiratory_illness_case(id);