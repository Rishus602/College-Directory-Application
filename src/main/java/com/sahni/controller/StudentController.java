package com.sahni.controller;


import com.sahni.Dto.StudentAcademicInfoDto;
import com.sahni.Dto.StudentProfileCreationDto;
import com.sahni.Entity.StudentProfile;
import com.sahni.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private StudentService studentService;



    @GetMapping("/getStudentDetails")
    public ResponseEntity<List<StudentProfile>> getStudentDetails(){
        return studentService.getStudentDetails();
    }
//
    @PostMapping("/getStudentByName")
    public ResponseEntity<List<StudentProfile>> getStudentByName(@RequestBody StudentProfileCreationDto n){
        String name = n.getName();
        return studentService.getStudentByName(name);
    }


    @PostMapping("/getStudentAcademicInfo")
    public ResponseEntity<StudentAcademicInfoDto> getAcademicInfo(@RequestBody Map<String, String> payload){
        String username = payload.get("username");
        return   studentService.getsStudentAcademicInfo(username);
//         ResponseEntity.ok(studentAcademicInfoDto);
    }

}
