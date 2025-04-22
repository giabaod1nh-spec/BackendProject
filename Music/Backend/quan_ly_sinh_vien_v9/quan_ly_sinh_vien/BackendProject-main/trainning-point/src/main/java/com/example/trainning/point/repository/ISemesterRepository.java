package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISemesterRepository extends JpaRepository<Semester, Long> {
}
