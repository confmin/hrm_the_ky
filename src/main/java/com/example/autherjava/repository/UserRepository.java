package com.example.autherjava.repository;


import com.example.autherjava.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer id);
    User getById(Integer id);
    @Query(value = "SELECT * from users join statuses on statuses.id = users.status_id order by statuses.level , users.time  ",nativeQuery = true)
    List<User> getUser();
    @Query(value = "SELECT * from users join statuses on statuses.id = users.status_id order by statuses.level , users.time limit 1",nativeQuery = true)
    List<User> getBest();
    @Query(value = "SELECT e from User e , Status q where e.status = q")
    Page<User> getPage(Pageable pageable);
    @Query(value = "SELECT count(users.id) from users join statuses on statuses.id = users.status_id WHERE statuses.level BETWEEN 1 and ?1",nativeQuery = true)
    Integer countIdPage(Integer level);


}
