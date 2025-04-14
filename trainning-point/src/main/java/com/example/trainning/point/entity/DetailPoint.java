package com.example.trainning.point.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DetailPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    @ManyToOne
    @JoinColumn(name = "criterion_point_id")
    @JsonBackReference
    @ToString.Exclude
    private CriterionPoint criterionPoint;

    @ManyToOne
    @JoinColumn(name = "detail_id")
    private Detail detail;
}
