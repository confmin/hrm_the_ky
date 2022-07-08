package com.example.autherjava.model.entity;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "permissions")
public class Permission  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Column
    private String name ;
    @ManyToMany(mappedBy = "permissions")
    private Collection<Account> accounts ;
    @ManyToMany(mappedBy ="permissions")
    private Collection<Role> roles ;
}
