package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EvaluateRepository extends JpaRepository<Evaluate, Long> {
    List<Evaluate> findByStudent_StudentId(Long studentId);

}
