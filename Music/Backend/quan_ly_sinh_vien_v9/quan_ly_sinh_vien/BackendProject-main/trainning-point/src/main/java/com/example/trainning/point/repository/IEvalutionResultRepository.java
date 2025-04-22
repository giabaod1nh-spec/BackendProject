package com.example.trainning.point.repository;

import com.example.trainning.point.entity.EvalutionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface IEvalutionResultRepository extends JpaRepository<EvalutionResult, Long> {

    @Query("""
                select ev from EvalutionResult ev where ev.semester.id = :semesterId 
                and ev.user.userId = :userId
            """)
    EvalutionResult findBySemesterIdAndUserId(@Param("semesterId") Long semesterId,
                                              @Param("userId") String userId);

    @Query("select ev from EvalutionResult ev where ev.user.userId = :idUser")
    EvalutionResult findByUser(@Param("idUser") String userId);
}
