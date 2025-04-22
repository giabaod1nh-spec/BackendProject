package com.example.trainning.point.repository;

import com.example.trainning.point.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission , String> {

}
