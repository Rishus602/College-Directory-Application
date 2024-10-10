package com.sahni.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "administrator_profile")
public class AdministratorProfile {

    @Id
    private Long userId; // This is also the foreign key referencing the User entity.

    private String photo;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

//    @Id
//    private Long userId; // This is also the foreign key referencing the User entity.
//
//    private String photo;
//
//    @ManyToOne
//    @JoinColumn(name = "department_id", nullable = false)
//    @JsonBackReference
//    private Department department;
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "user_id")
//    @JsonBackReference
//    private User user;
}
