package com.example.trainning.point.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Criterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long criterionId;

     String description ;
     Integer maxPoint;

     @OneToOne
     CriterionPoint criterionPoint;
}

