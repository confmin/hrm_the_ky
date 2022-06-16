package com.example.autherjava.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonIgnore

    @ManyToMany(mappedBy="roles")
    private Collection<Account> accounts ;
    @JsonIgnore

    @ManyToMany
    @JoinTable(name ="roles_permissions", joinColumns = {@JoinColumn(name ="id_role")},
    inverseJoinColumns = {@JoinColumn(name = "id_permission")})
    private Collection<Permission> permissions ;

}