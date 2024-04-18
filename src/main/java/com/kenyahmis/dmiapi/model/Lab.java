package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "lab")
public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "id", insertable = false, updatable = false)
    private IllnessCase illnessCase;
    @Column(name = "case_id")
    private UUID caseId;
    private String orderId;
    private String testName;
    private String unit;
    private String upperLimit;
    private String lowerLimit;
    private String testResult;
    private LocalDateTime labDate;
    private Boolean voided;

    public Lab() { }
}
