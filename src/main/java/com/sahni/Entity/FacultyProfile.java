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
@Table(name = "faculty_profile")
public class FacultyProfile {

    @Id
    private Long userId; // This is also the foreign key referencing the User entity.

    private String photo;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
//    @JsonManagedReference
    private Department department;

    private String officeHours;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "faculty" , fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonManagedReference
    private Set<Course> courses;




//    @Id
//    private Long userId; // This is also the foreign key referencing the User entity.
//
//    private String photo;
//
//    @ManyToOne
//    @JoinColumn(name = "department_id", nullable = false)
//    @JsonBackReference // Forward reference
//    private Department department;
//
//    private String officeHours;
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "user_id")
//    @JsonBackReference // Forward reference
//    private User user;
//
//    @OneToMany(mappedBy = "faculty")
//    @JsonManagedReference // Prevent serialization
//    private Set<Course> courses;
}
