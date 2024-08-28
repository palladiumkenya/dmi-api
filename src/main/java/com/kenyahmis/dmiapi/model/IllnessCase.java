package com.kenyahmis.dmiapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Entity
@Table(name = "illness_case")
public class IllnessCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    private UUID batchId;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
    @JoinColumn(name = "emrId", referencedColumnName = "id")
    private UUID emrId;
    private String visitUniqueId;
    private String mflCode;
    private LocalDateTime interviewDate;
    private LocalDate admissionDate;
    private LocalDate outpatientDate;
    private String status;
    private String finalOutcome;
    private LocalDateTime finalOutcomeDate;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Complaint> complaints;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<RiskFactor> riskFactors;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<FlaggedCondition> flaggedConditions;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Vaccination> vaccinations;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<VitalSign> vitalSigns;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Lab> labs;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Diagnosis> diagnosis;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime loadDate;
    public IllnessCase() {}
}
