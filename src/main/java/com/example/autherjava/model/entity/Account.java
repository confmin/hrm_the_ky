package com.example.autherjava.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "accounts")
public class Account {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
@Column
    private String username ;
@Column
    private String email ;
@Column
    private String password ;
@ManyToMany
@JoinTable(name ="accounts_roles", joinColumns = {@JoinColumn(name = "id_accounts")},
        inverseJoinColumns = {@JoinColumn(name = "id_roles")})
    private Collection<Role> roles ;
@ManyToMany
@JoinTable(name = "accounts_premissions",joinColumns = {@JoinColumn(name = "id_accounts")},
        inverseJoinColumns = {@JoinColumn(name = "id_permissions")})
    private Collection<Permission> permissions ;
}
