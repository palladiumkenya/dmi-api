package com.kenyahmis.dmiapi.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
@Data
@Entity
@Table(name = "flagged_condition")
public class FlaggedCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "id", insertable = false, updatable = false)
    private IllnessCase illnessCase;
    @Column(name = "case_id")
    private UUID caseId;
    private String conditionId;
    private String conditionName;
    private Boolean voided;

    public FlaggedCondition() {}
}



