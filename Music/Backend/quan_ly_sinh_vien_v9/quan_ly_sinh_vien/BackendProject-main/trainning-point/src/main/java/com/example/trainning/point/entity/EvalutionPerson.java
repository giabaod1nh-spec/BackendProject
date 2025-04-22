package com.example.trainning.point.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "tbl_evalution_person",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_user", "evalution_standard_id", "semeter_id"})
        })
public class EvalutionPerson extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "student_score")
    Double studentScore;

    @Column(name = "monitor_score")
    Double monitorScore;

    @Column(name = "teacher_score")
    Double teacherScore;

//    @Column(name = "evalution_status")
//    String evalutionStatus;

    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;

    @ManyToOne
    @JoinColumn(name = "evalution_standard_id")
    EvalutionStandard evalutionStandard;

    @ManyToOne
    @JoinColumn(name = "semeter_id")
    Semester semester;
}
