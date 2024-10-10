package com.sahni.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")

public class Department {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    private String description;
//
//    @OneToMany(mappedBy = "department")
//    @JsonBackReference
//    private Set<StudentProfile> students;
//
//    @OneToMany(mappedBy = "department")
//    @JsonIgnore
//    private Set<FacultyProfile> faculties;
//
//    @OneToMany(mappedBy = "department")
//    private Set<AdministratorProfile> administrators;
//
//    @OneToMany(mappedBy = "department")
//    @JsonIgnore
//    private Set<Course> courses;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "department")
    @JsonIgnore // Prevent serialization
    private Set<StudentProfile> students;

    @OneToMany(mappedBy = "department")
    @JsonIgnore// Prevent serialization
    private Set<FacultyProfile> faculties;

    @OneToMany(mappedBy = "department")
    @JsonIgnore // Prevent serialization
    private Set<AdministratorProfile> administrators;

    @OneToMany(mappedBy = "department")
    @JsonIgnore // Prevent serialization
    private Set<Course> courses;
}
