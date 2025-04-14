package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Student;
import com.example.trainning.point.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student , Long> {
       Optional<Student> findByStudentId(Long studentId);
       boolean existsByUser(User user);
       //boolean existsByUserId(String userId);
}
