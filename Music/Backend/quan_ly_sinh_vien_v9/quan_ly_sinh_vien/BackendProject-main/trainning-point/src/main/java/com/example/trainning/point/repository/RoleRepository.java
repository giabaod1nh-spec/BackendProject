package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role , String> {
    Role findByName(String name);
}
