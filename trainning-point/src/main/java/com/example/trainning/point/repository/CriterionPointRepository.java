package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Criterion;
import com.example.trainning.point.entity.CriterionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CriterionPointRepository extends JpaRepository<CriterionPoint , Long> {
    //public CriterionPoint findBycriterionPointId(Long criterionPointId);
    Set<CriterionPoint> findByCriterionPointId(Long criterionPointId);
}
