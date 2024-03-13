UPDATE public.illness_case
SET emr_id = e.id
FROM public.emr e INNER JOIN public.illness_case ic ON e.emr_name = ic.emr
where e.emr_name = public.illness_case.emr
