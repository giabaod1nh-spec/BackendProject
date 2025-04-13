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
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long instructorId;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    String fullName;
    LocalDate dob;
    String phone;

    Gender gender;

    @Builder.Default
    boolean isActive = true ;
}
