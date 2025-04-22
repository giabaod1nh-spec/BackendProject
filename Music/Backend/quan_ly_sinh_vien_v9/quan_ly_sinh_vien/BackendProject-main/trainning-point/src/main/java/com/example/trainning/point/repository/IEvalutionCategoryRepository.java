package com.example.trainning.point.repository;

import com.example.trainning.point.entity.EvalutionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEvalutionCategoryRepository extends JpaRepository<EvalutionCategory, Long> {
    @Query("""
            select ct from EvalutionCategory ct where ct.isDelete = false 
            order by ct.createDate
            """)
    List<EvalutionCategory> findAll();
}
