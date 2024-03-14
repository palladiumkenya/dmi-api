//package com.kenyahmis.dmiapi.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.UUID;
//
//@Data
//@Entity
//@Table(name = "flaggable_condition")
//public class FlaggableCondition {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @JoinColumn(name = "condition_classification_id", referencedColumnName = "id")
//    private UUID conditionClassificationId;
//
//    private String condition;
//
//    private Integer conceptId;
//
//    private String icd10Code;
//}
