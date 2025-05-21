package com.example.newapp.entity;



import jakarta.persistence.*;

import lombok.Getter;

import lombok.Setter;



@Entity

@Table(name = "departments")

public class Department {



    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Getter @Setter

    private Long id;



    @Getter @Setter

    private String name;



// No-args constructor

    public Department() {

    }



// Constructor with id and name

    public Department(Long id, String name) {

        this.id = id;

        this.name = name;

    }



// Constructor with only name (optional)

    public Department(String name) {

        this.name = name;

    }

}