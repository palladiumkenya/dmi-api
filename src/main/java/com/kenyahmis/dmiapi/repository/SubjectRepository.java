package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.dto.SubjectSummary;
import com.kenyahmis.dmiapi.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    @Query("select new com.kenyahmis.dmiapi.dto.SubjectSummary(s.id, s.patientUniqueId, c.mflCode) from Subject s" +
            " left join IllnessCase c on c.subject.id = s.id where s.patientUniqueId = :patientUniqueId and c.mflCode = :mflCode")
    Page<SubjectSummary> findByPatientUniqueIdAndSiteCode(@Param("patientUniqueId") String patientUniqueId, @Param("mflCode") String mflCode, Pageable pageable);

}
