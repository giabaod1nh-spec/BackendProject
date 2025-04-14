package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Criterion;
import com.example.trainning.point.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriterionRepository extends JpaRepository<Criterion , Long> {
    boolean existsByDescription(String description);
    Optional<Criterion> findByCriterionId(Long criterionId);
}
