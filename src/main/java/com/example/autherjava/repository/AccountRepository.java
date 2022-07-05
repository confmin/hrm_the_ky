package com.example.autherjava.repository;

import com.example.autherjava.model.entity.Account;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account,Integer> {
    Boolean existsAccountByEmail(String mail);
    Boolean existsAccountByUsername(String user);

    Account findByUsername(String user);
    Account findByEmail(String email);
    @Query(value = "select password from accounts where email = ?1",nativeQuery = true)
    String findByPwd(String email);
    Account findAccountByUsername(String username);




}