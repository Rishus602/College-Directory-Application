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
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    @JsonBackReference
    private FacultyProfile faculty;

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments;





//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String title;
//    private String description;
//
//    @ManyToOne
//    @JoinColumn(name = "department_id", nullable = false)
//    @JsonBackReference
//    private Department department;
//
//    @ManyToOne
//    @JoinColumn(name = "faculty_id", nullable = false)
//    @JsonBackReference // Back reference
//    private FacultyProfile faculty;
//
//    @OneToMany(mappedBy = "course")
//    @JsonIgnore // Prevent serialization
//    private Set<Enrollment> enrollments;
}
