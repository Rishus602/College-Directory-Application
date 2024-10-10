package com.sahni.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {

    private Long id;
    private String studentName;
    private String courseTitle;
    private Double grade;
    private Integer attendance;
    private CourseDto course;
}
