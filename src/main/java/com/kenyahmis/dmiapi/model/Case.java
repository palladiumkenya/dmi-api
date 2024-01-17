package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Entity
@Table(name = "illness_case")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JoinColumn(name = "batchId", referencedColumnName = "id")
    private UUID batchId;
    @JoinColumn(name = "subjectId", referencedColumnName = "id")
    private UUID subjectId;
    private String visitUniqueId;
    private String mflCode;
    private String emr;
    private LocalDateTime interviewDate;
    private LocalDate admissionDate;
    private LocalDate outpatientDate;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Complaint> complaints;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Lab> labs;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Diagnosis> diagnosis;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime loadDate;
    public Case() {}
}
