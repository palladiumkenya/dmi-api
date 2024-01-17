package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

}
