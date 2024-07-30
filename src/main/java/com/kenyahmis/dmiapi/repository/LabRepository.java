package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.Lab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LabRepository extends JpaRepository<Lab, UUID> {}
