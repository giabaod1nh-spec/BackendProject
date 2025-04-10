package com.example.trainning.point.entity;
import jakarta.persistence.*;


@Entity
public class Criterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}