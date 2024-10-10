package com.sahni.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_profile")

public class StudentProfile {

    @Id
    private Long userId; // This is also the foreign key referencing the User entity.

    private String photo;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
//    @JsonManagedReference
    private Department department;

    private String year;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
//    @JsonManagedReference
    private User user;





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
//    private String year;
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "user_id")
//    @JsonBackReference // Forward reference
//    private User user;
}
