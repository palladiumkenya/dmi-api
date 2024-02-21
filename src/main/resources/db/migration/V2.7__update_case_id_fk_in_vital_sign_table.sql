ALTER TABLE public.vital_sign DROP CONSTRAINT IF EXISTS fk_risk_factor_case_id;
ALTER TABLE public.risk_factor DROP CONSTRAINT IF EXISTS fk_risk_factor_case_id;
ALTER TABLE public.risk_factor  ADD CONSTRAINT fk_risk_factor_case_id FOREIGN KEY (case_id) REFERENCES public.illness_case(id);