package com.example.trainning.point.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriterionPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    @ManyToOne
    @JoinColumn(name = "evaluate_id")
    private Evaluate evaluate;

    @OneToMany(mappedBy = "criterionPoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailPoint> detailPoints = new ArrayList<>();
}
