package com.sahni.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enrollment")

public class Enrollment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "student_id", nullable = false)
//    @JsonIgnore
//    private StudentProfile student;
//
//
//    private Double grade; // Field for storing the student's grade for this course
//    private Integer attendance; // Field for storing attendance percentage
//
//
//    @ManyToOne
//    @JoinColumn(name = "course_id", nullable = false)
//    @JsonBackReference
//    private Course course;





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore // Prevent serialization
    private StudentProfile student;

    private Double grade; // Field for storing the student's grade for this course
    private Integer attendance; // Field for storing attendance percentage

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore  // Prevent serialization
    private Course course;
}
