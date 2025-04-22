package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SemesterRepository extends JpaRepository<Semester, Long> {}

