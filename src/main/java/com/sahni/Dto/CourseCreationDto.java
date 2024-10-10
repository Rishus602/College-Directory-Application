package com.sahni.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreationDto {

    private String title;
    private String description;
    private Long departmentId;  // ID of the department to associate the course with
    private Long facultyId;

}
