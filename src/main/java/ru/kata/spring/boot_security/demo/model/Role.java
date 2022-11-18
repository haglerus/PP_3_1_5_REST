package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return this.id;
    }

    @Column(name = "name")
    private String name;

    public String getName() {
        return this.name;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;

        return getId().equals(role.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
