package com.sahni.controller;


import com.sahni.Dto.FacultyProfileDto;
import com.sahni.Dto.StudentInfoDto;
import com.sahni.Dto.StudentProfileCreationDto;
import com.sahni.Entity.FacultyProfile;
import com.sahni.Entity.StudentProfile;
import com.sahni.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/getFacultyDetails")
    public ResponseEntity<List<FacultyProfile>> getFacultyDetails(){
        return facultyService.getFacultyDetails();
    }

    @GetMapping("/getStudentDetails")
    public ResponseEntity<List<StudentProfile>> getStudentDetails(){
        return facultyService.getStudentDetails();
    }


    @PostMapping("/getFacultyByName")
    public ResponseEntity<List<FacultyProfile>> getFacultyByName(@RequestBody FacultyProfileDto n){
        String name = n.getName();
        return facultyService.getFacultyByName(name);
    }

    @PostMapping("/class-list")
    public StudentInfoDto getClassList(@RequestBody FacultyProfileDto dto) {
        Long facultyId = dto.getId();
        return  facultyService.getFacultyStudentDetails(facultyId);

    }

}
