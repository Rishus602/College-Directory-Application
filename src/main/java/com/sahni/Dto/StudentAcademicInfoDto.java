package com.sahni.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAcademicInfoDto {
    private String studentName;
    private List<EnrollmentDto> enrollments;

}
