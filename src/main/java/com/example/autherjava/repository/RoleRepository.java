package com.example.autherjava.repository;

import com.example.autherjava.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
    Role findRoleByName(String role);

    Role getById(Integer id);
   Boolean existsByName(String name);
}
