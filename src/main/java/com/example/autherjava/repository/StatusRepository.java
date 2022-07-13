package com.example.autherjava.repository;

import com.example.autherjava.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {
    Status findStatusById(Integer id);
    @Query(value = "SELECT * from statuses order by level ",nativeQuery = true)
    List<Status> getOrderByUutien() ;
    Status getById(Integer id);
    @Query(value = "SELECT statuses.level from users join statuses on statuses.id = users.status_id WHERE users.status_id = ?1 limit 1",nativeQuery = true)
    Integer getByLevel(Integer level) ;
    @Query(value = "select e from Status  e where e.name=?1")
    Optional<Status> getName(String name );
    @Query(value = "select e from Status e where e.level =?1")
    Optional<Status> getLevel(Integer id );
    @Query(value = "select id from statuses", nativeQuery = true)
    List<Integer> getAllById() ;
}
