package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.Diagnosis;
import com.kenyahmis.dmiapi.model.Emr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmrRepository extends JpaRepository<Emr, UUID> {
    Optional<Emr> findByEmrNameAndVoided(String emrName, Boolean voided);
}
