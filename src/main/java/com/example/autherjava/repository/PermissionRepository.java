package com.example.autherjava.repository;

import com.example.autherjava.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    boolean findByName(String name);
    Permission getById(Integer id);
}
