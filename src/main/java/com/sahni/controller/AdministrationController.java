package com.sahni.controller;


import com.sahni.Dto.*;
import com.sahni.Entity.AdministratorProfile;
import com.sahni.Entity.FacultyProfile;
import com.sahni.Entity.StudentProfile;
import com.sahni.Entity.User;
import com.sahni.service.AdministratorService;
import com.sahni.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdministrationController {


    @Autowired
    private AdministratorService administratorService;

    @PostMapping("/createStudentProfile")
    public ResponseEntity<StudentProfileCreationDto> createStudentProfile(@RequestBody StudentProfileCreationDto dto){
        return administratorService.createStudentProfile(dto);
    }

    @PutMapping("/updateStudentProfile")
    public ResponseEntity<StudentProfileCreationDto> updateStudentProfile(@RequestBody StudentProfileCreationDto dto){
        return administratorService.updateStudentProfile(dto);
    }

    @DeleteMapping("/deleteStudentProfile")
    public ResponseEntity<String> deleteStudentProfile(@RequestBody Map<String, String> payload){
        String username = payload.get("username");
        return administratorService.deleteStudentProfile(username);
    }


    @PostMapping("/createFacultyProfile")
    public ResponseEntity<FacultyProfileDto> createFacultyProfile(@RequestBody FacultyProfileDto dto){
        return administratorService.createFacultyProfile(dto);
    }

    @PutMapping("/updateFacultyProfile")
    public ResponseEntity<FacultyProfileDto> updateFacultyProfile(@RequestBody FacultyProfileDto dto){
        return administratorService.updateFacultyProfile(dto);
    }

    @DeleteMapping("/deleteFacultyProfile")
    public ResponseEntity<String> deleteFacultyProfile(@RequestBody Map<String, String> payload){
        String username = payload.get("username");
        return administratorService.deleteFacultyProfile(username);
    }

    @GetMapping("/getAllUserDetails")
    public ResponseEntity<List<User>> getAllAdminDetails(){
        return administratorService.getAllAdminDetails();
    }


    @GetMapping("/getFacultyDetails")
    public ResponseEntity<List<FacultyProfile>> getFacultyDetails(){
        return administratorService.getFacultyDetails();
    }

    @GetMapping("/getStudentDetails")
    public ResponseEntity<List<StudentProfile>> getStudentDetails(){
        return administratorService.getStudentDetails();
    }



    // Academic creation - enrollment , courses

    @PostMapping("/createCourse")
    public ResponseEntity<CourseCreationDto> createCourse(@RequestBody CourseCreationDto dto){
        return administratorService.createCourse(dto);
    }


    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentRequestDto> enrollStudentInCourse(@RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        return administratorService.enrollStudentInCourse(enrollmentRequestDto);

    }

}
