package com.example.trainning.point.repository;

import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEvalutionStandardRepository extends JpaRepository<EvalutionStandard, Long> {
    @Query("""
            select st from EvalutionStandard st where st.isDelete = false 
            order by st.createDate
            """)
    List<EvalutionStandard> findAll();
}
