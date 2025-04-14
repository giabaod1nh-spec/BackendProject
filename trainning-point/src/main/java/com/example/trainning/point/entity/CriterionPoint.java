package com.example.trainning.point.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class CriterionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long criterionPointId;

    @ManyToOne
    @JoinColumn(name = "evaluate_id")
    Evaluate evaluate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criterion_id")
    @JsonBackReference
    Criterion criterion;

    @Column(name = "student_total_point")
    Integer studentPoint = 0;

    @Column(name = "bcs_total_point")
    Integer bcsPoint = 0;

    @Column(name = "instructor_total_point")
    Integer instructorPoint = 0;

    String description;
    Integer maxPoint;
}
