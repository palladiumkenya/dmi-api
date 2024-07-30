package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {}

