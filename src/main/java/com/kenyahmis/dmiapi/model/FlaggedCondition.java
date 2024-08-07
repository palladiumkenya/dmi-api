package com.kenyahmis.dmiapi.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    private IllnessCase illnessCase;
    private String conditionId;
    private String conditionName;
    private Boolean voided;

    public FlaggedCondition() {}
}



