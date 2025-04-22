package com.example.trainning.point.repository;

import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEvalutionPersonRepository extends JpaRepository<EvalutionPerson, Long> {
    @Query("""
            select ep from EvalutionPerson ep where ep.isDelete = false 
            order by ep.createDate
            """)
    List<EvalutionPerson> findAll();

    @Query("""
            select ev from EvalutionPerson ev where 
            ev.semester.id = :idSemester and ev.user.userId = :idUser
            """)
    List<EvalutionPerson> findBySemesterIdAndUserUserId(@Param("idSemester") Long idSemester,
                                                        @Param("idUser") String idUser);

    @Query("select ev from EvalutionPerson ev where ev.user.userId = :userId")
    EvalutionPerson findByUserId(@Param("userId") String id);

    @Query("select ev from EvalutionPerson ev where ev.user.userId = :userId and ev.evalutionStandard.id = :standardId")
    EvalutionPerson findByUserIdAndStandardId(@Param("userId") String userId,
                                              @Param("standardId") Long standardId);
}
