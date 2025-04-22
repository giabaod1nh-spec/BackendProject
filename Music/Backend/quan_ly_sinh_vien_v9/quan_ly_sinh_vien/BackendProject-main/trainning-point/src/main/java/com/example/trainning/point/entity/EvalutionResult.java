package com.example.trainning.point.entity;

import com.example.trainning.point.enums.EvalutionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "tbl_evalution_reult")
public class EvalutionResult extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "status")
    String status = EvalutionStatus.WAIT_CONFIRMATION.name();

//    @Column(name = "committee_mark")
//    Boolean committeeMark = false;
//
//    @Column(name = "counselor_mark")
//    Boolean lectureMark = false;

    @ManyToOne
    @JoinColumn(name = "semeter_id")
    Semester semester;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
