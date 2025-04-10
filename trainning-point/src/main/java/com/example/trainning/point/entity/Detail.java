package com.example.trainning.point.entity;
import jakarta.persistence.*;

@Entity
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}