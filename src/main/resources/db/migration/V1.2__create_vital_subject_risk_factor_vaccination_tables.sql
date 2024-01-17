CREATE TABLE IF NOT EXISTS public.vital_sign (
                                          id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                          case_id uuid NULL,
                                          order_id varchar(50) NULL,
                                          temperature numeric NULL,
                                          temperature_mode varchar(100) NULL,
                                          respiratory_rate int4 NULL,
                                          oxygen_saturation int4 NULL,
                                          oxygen_saturation_mode varchar(150) NULL,
                                          CONSTRAINT vital_sign_pkey PRIMARY KEY (id)
);
ALTER TABLE public.vital_sign DROP CONSTRAINT IF EXISTS fk_vital_sign_case_id;
ALTER TABLE public.vital_sign ADD CONSTRAINT fk_vital_sign_case_id FOREIGN KEY (case_id) REFERENCES public.respiratory_illness_case(id);

CREATE TABLE IF NOT EXISTS public.subject (
                                                 id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                                 patient_unique_id varchar(100) NULL,
                                                 nupi varchar(50) NULL,
                                                 sex varchar(10) NULL,
                                                 address varchar(100) NULL,
                                                 county varchar(100) NULL,
                                                 sub_county varchar(100) NULL,
                                                 CONSTRAINT subject_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.risk_factor (
                                                 id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                                 case_id uuid NULL,
                                                 condition varchar(100) NULL,
                                                 voided bool NULL,
                                                 CONSTRAINT risk_factor_pkey PRIMARY KEY (id)
);
ALTER TABLE public.vital_sign DROP CONSTRAINT IF EXISTS fk_risk_factor_case_id;
ALTER TABLE public.vital_sign ADD CONSTRAINT fk_risk_factor_case_id FOREIGN KEY (case_id) REFERENCES public.respiratory_illness_case(id);

CREATE TABLE IF NOT EXISTS public.vaccination (
                                                  id uuid NOT NULL DEFAULT uuid_generate_v4(),
                                                  case_id uuid NULL,
                                                  vaccination_id varchar(100) NULL,
                                                  vaccination varchar(100) NULL,
                                                  condition varchar(100) NULL,
                                                  doses int4 NULL,
                                                  verified bool NULL,
                                                  voided bool NULL,
                                                  CONSTRAINT vaccination_pkey PRIMARY KEY (id)
);
ALTER TABLE public.vaccination DROP CONSTRAINT IF EXISTS fk_vaccination_case_id;
ALTER TABLE public.vaccination ADD CONSTRAINT fk_vaccination_case_id FOREIGN KEY (case_id) REFERENCES public.respiratory_illness_case(id);



