package com.example.trainning.point.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class DetailPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    @ManyToOne
    private CriterionPoint criterionPoint;

    @ManyToOne
    private Detail detail;
}
