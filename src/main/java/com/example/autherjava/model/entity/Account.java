package com.example.autherjava.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.transaction.Transactional;
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

    @LazyCollection(LazyCollectionOption.FALSE)
@ManyToMany(cascade = CascadeType.ALL)
private Collection<Role> roles = new ArrayList<>();


    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany( cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)

    @JoinTable(name = "accounts_premissions",joinColumns = {@JoinColumn(name = "id_accounts")},
        inverseJoinColumns = {@JoinColumn(name = "id_permissions", referencedColumnName = "id")})
    private Collection<Permission> permissions = new ArrayList<>();
}

