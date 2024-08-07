package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VaccinationRepository extends JpaRepository<Vaccination, UUID> {}
