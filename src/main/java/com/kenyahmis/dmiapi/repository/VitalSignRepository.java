package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.VitalSign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VitalSignRepository extends JpaRepository<VitalSign,UUID> {}
