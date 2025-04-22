package com.example.trainning.point.repository;

import com.example.trainning.point.entity.ClassManager;
import com.example.trainning.point.entity.EvalutionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClassManagerRepository extends JpaRepository<ClassManager, Long> {
    @Query("""
            select cla from ClassManager cla where cla.isDelete = false 
            order by cla.createDate
            """)
    List<ClassManager> findAll();
}
