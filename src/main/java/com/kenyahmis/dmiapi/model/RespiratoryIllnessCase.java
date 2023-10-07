package com.kenyahmis.dmiapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "respiratory_illness_case")
public class RespiratoryIllnessCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JoinColumn(name = "batchId", referencedColumnName = "id")
    private UUID batchId;
    private String patientUniqueId;
    private String nupi;
    private String visitUniqueId;
    private String mflCode;
    private String emr;
    private LocalDateTime interviewDate;
    private LocalDate dateOfBirth;
    private Integer ageInMonths;
    private Integer ageInYears;
    private String sex;
    private String address;
    private LocalDate admissionDate;
    private LocalDate outpatientDate;
    private Double temperature;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Complaint> complaints;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Lab> labs;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "illnessCase", cascade = CascadeType.ALL)
    private List<Diagnosis> diagnosis;
    private String county;
    private String subCounty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime loadDate;
    private Boolean voided;
    public RespiratoryIllnessCase() {}
}
