package com.example.autherjava.repository;

import com.example.autherjava.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    Permission findByName(String name);
}
