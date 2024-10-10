package com.sahni.Dto;


import com.sahni.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileCreationDto {

    private Long userId;
    private String name;
    private String email;
    private String username;
    private String password;
    private String departmentName;
    private Long departmentId;
    private Role role;
    private String phone;
    private String photo;
    private String year;
    private List<EnrollmentDto> enrollments;
    
}
