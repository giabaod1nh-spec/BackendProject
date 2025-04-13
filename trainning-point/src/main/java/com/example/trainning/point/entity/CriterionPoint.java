package com.example.trainning.point.entity;

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
    Integer criterionPointId;

    @ManyToOne
    @JoinColumn(name = "evaluate_id", nullable = false)
    Evaluate evaluate;

    @OneToOne
    @JoinColumn(name = "criterion_id")
    Criterion criterion;

    @Column(name = "student_total_point")
    private Integer studentPoint = 0;

    @Column(name = "bcs_total_point")
    private Integer bcsPoint = 0;

    @Column(name = "instructor_total_point")
    private Integer instructorPoint = 0;

    String description;

}
