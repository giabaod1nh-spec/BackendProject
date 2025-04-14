package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Instructor;
import com.example.trainning.point.entity.Student;
import com.example.trainning.point.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor , Long> {
    Optional<Instructor> findByInstructorId(Long studentId);
    boolean existsByUser(User user);
}
