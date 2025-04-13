package com.example.trainning.point.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalScore;

    @OneToMany(mappedBy = "evaluate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CriterionPoint> criterionPoints = new ArrayList<>();
}
