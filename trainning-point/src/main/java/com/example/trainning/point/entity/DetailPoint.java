package com.example.trainning.point.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    @ManyToOne
    @JoinColumn(name = "criterion_point_id")
    @JsonBackReference
    private CriterionPoint criterionPoint;

    @ManyToOne
    @JoinColumn(name = "detail_id")
    private Detail detail;
}
