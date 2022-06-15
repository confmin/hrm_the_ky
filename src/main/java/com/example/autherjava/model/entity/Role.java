package com.example.autherjava.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id ;
    @Column
    private String name ;
    @ManyToMany(mappedBy="roles")
    private Collection<Account> accounts ;
    @ManyToMany
    @JoinTable(name ="roles_permissions", joinColumns = {@JoinColumn(name ="id_role")},
    inverseJoinColumns = {@JoinColumn(name = "id_permission")})
    private Collection<Permission> permissions ;

}
