package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;



public interface StudentRepository extends JpaRepository<Student, Long> {}
