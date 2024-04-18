package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "risk_factor")
public class RiskFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "id", insertable = false, updatable = false)
    private IllnessCase illnessCase;
    @Column(name = "case_id")
    private UUID caseId;
    private String riskFactorId;
    private String condition;
    private Boolean voided;
}
