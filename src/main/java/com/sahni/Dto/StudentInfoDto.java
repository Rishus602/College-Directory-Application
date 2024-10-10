package com.sahni.Dto;


import com.sahni.Entity.Course;
import com.sahni.Entity.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfoDto {

    private Long userId;
    private String name;
    private String photo;
    private String email;
    private String phone;
    Set<CourseDto> courses;
    List<EnrollmentDto> enrollmentList;

}
