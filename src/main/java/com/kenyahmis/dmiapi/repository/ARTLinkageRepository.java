package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.ARTLinkage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ARTLinkageRepository extends JpaRepository<ARTLinkage, UUID> {}
