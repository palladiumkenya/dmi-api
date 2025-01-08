CREATE TABLE IF NOT EXISTS public.art_linkage (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    case_id uuid NULL,
    linkage_id varchar(50) NULL,
    art_start_date timestamp,
    CONSTRAINT art_linkage_pkey PRIMARY KEY (id)
    );
ALTER TABLE public.art_linkage DROP CONSTRAINT IF EXISTS fk_art_linkage_case_id;
ALTER TABLE public.art_linkage ADD CONSTRAINT fk_art_linkage_case_id FOREIGN KEY (case_id) REFERENCES public.illness_case(id);