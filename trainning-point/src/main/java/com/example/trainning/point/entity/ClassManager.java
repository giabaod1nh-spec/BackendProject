package com.example.trainning.point.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ClassManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long classManagerId;

    @OneToOne
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    Semester semester;
}
