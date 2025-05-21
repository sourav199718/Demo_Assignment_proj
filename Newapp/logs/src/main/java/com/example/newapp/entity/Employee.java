package com.example.newapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
public class Employee {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String department;

    // No-arg constructor
    public Employee() {
    }

    // Two-arg constructor
    public Employee(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Four-arg constructor (fix this)
    public Employee(long id, String name, String email, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
    }
}
