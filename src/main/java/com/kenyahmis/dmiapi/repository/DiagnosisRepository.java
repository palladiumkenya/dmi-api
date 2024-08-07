package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, UUID> {}
