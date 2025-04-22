package com.example.trainning.point.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ClassManager extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "code_class")
    String codeClass;
    String name;

    @Column(name = "academic_cohort")
    String academicCohort;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "end_date")
    LocalDate endDate;
//    @ManyToOne
//    @JoinColumn(name = "semester_id")
//    Semester semester;

    @OneToMany(mappedBy = "classManager")
    List<User> users;
}
