package com.sahni.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyProfileDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String photo;
    private Long departmentId;
    private String departmentName;
    private String officeHours;
}
