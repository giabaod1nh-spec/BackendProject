package com.example.trainning.point.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CriterionPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    @ManyToOne
    @JoinColumn(name = "evaluate_id")
    @JsonBackReference
    @ToString.Exclude
    private Evaluate evaluate;

    @ManyToOne
    @JoinColumn(name = "criterion_id")
    private Criterion criterion;

    @OneToMany(mappedBy = "criterionPoint", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<DetailPoint> detailPoints = new ArrayList<>();
}

