package com.example.autherjava.model.entity;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
public class Permission  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String name ;
    @ManyToMany( mappedBy = "permissions")
    private Collection<Account> accounts ;
    @ManyToMany(mappedBy ="permissions")
    private Collection<Role> roles ;

    public Permission(String name)
    {
        this.name = name ;
    }

}
