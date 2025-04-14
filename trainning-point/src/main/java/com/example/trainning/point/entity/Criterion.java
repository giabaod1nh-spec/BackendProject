package com.example.trainning.point.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

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

     @OneToMany(mappedBy = "criterion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     @JsonBackReference
     Set<CriterionPoint> criterionPoints;
}

