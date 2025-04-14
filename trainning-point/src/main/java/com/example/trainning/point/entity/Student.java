package com.example.trainning.point.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long studentId ;
    String className;
    String fullName;
    LocalDate dob;
    Gender gender;
    String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @Builder.Default
    Boolean isActive = true ;
}
