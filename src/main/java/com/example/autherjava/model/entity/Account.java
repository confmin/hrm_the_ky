package com.example.autherjava.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private String verify_code ;
    private  boolean enable ;



@ManyToMany(fetch = FetchType.LAZY)
private Collection<Role> roles = new ArrayList<>();



    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
@JoinTable(name = "accounts_premissions",joinColumns = {@JoinColumn(name = "id_accounts")},
        inverseJoinColumns = {@JoinColumn(name = "id_permissions")})
    private Collection<Permission> permissions ;
}

