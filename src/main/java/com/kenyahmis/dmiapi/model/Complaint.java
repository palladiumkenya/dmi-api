package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "id", insertable = false, updatable = false)
    private IllnessCase illnessCase;
    @Column(name = "case_id")
    private UUID caseId;
    private String complaintId;
    private String complaint;
    private Boolean voided;
    private LocalDate onsetDate;
    private Integer duration;

    public Complaint() {}

}
