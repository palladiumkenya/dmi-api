package com.kenyahmis.dmiapi.repository;

import com.kenyahmis.dmiapi.model.BatchOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BatchOperationsRepository extends JpaRepository<BatchOperation, UUID> {
    @Query(value = """
            select b.id as ref_id,b.input_count,b.status,(select count(1) from respiratory_illness_case where batch_id = ?1) as recent_count,
                   b.start_time,b.complete_time
            from batch_operations b
            where b.id = ?1""", nativeQuery = true)
    List<Object[]> findByRefId(UUID refId);

    Optional<BatchOperation> findByExternalReferenceIdAndMflCode(String externalReferenceId, String mlfCode);

}
