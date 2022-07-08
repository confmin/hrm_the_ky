package com.example.autherjava.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role  {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id ;
    @Column
    private String name ;

    @ManyToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinTable(name ="roles_permissions", joinColumns = {@JoinColumn(name ="id_role")},
    inverseJoinColumns = {@JoinColumn(name = "id_permission")})
    private Collection<Permission> permissions ;


}
