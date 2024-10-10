package com.sahni.service;

import com.sahni.Dto.CourseDto;
import com.sahni.Dto.EnrollmentDto;
import com.sahni.Dto.FacultyProfileDto;
import com.sahni.Dto.StudentInfoDto;
import com.sahni.Entity.Course;
import com.sahni.Entity.Enrollment;
import com.sahni.Entity.FacultyProfile;
import com.sahni.Entity.StudentProfile;
import com.sahni.Exception.ResourceNotFoundException;
import com.sahni.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FacultyService {


    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<List<FacultyProfile>> getFacultyDetails(){
        List<FacultyProfile> studentProfile = facultyRepository.findAll();
        return new ResponseEntity<>(studentProfile, HttpStatus.OK);
    }


    public ResponseEntity<List<StudentProfile>> getStudentDetails(){
        List<StudentProfile> studentProfile = studentRepository.findAll();
        return new ResponseEntity<>(studentProfile, HttpStatus.OK);
    }



    public ResponseEntity<List<FacultyProfile>> getFacultyByName(String name) {

        List<FacultyProfile> facultyProfiles = facultyRepository.findByName(name);

        // Check if the list is empty and throw an exception if necessary
        if (facultyProfiles.isEmpty()) {
            throw new ResourceNotFoundException("No students found with the name: " + name);
        }
        return new ResponseEntity<>(facultyProfiles, HttpStatus.OK);
    }



//    public ResponseEntity<List<StudentInfoDto>> getClassList(Long id){
//
//        FacultyProfile facultyProfile = facultyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id not found "));
//
//
////        List<Course> course = courseRepository.findByFacultyId(facultyProfile.getUserId());
//
//        System.out.println(course);
////        System.out.println(course.stream().toList());
//
////        System.out.println(facultyProfile.getDepartment().getName());
////        System.out.println(facultyProfile.getUser());
//
////        System.out.println("Courses " + " -> " +  facultyProfile.getCourses());
////        Set<Course> courses = facultyProfile.getCourses();
//
//        List<StudentInfoDto> studentInfos = new ArrayList<>();
////
////        for (Course courseName : courses){
////            // Get enrollments for each course
////            List<Enrollment> enrollments = enrollmentRepository.findByCourse(courseName.getId());
////            for (Enrollment enrollment : enrollments) {
////                // Fetch the student associated with the enrollment
////                StudentProfile student = enrollment.getStudent();
////
////                // Create a DTO for the student info
////                StudentInfoDto studentInfo = new StudentInfoDto(
////                        student.getUserId(),
////                        student.getUser().getName(),
////                        student.getPhoto(),
////                        student.getUser().getEmail(),
////                        student.getUser().getPhone()
////                );
////
////                // Add to the list of student info
////                studentInfos.add(studentInfo);
////            }
////        }
//
//        return new ResponseEntity<>(studentInfos, HttpStatus.CREATED); // Return the list of student information
//    }

    @Transactional(readOnly = true)
    public StudentInfoDto getFacultyStudentDetails(Long userId) {
        List<Object[]> resultSet = userRepository.getFacultyStudentDetails(userId);

//        StudentProfile studentProfile = studentRepository.findByUserName().orElseThrow(()-> new ResourceNotFoundException("User id is not found with assosciated"));
        // Initialize DTO
        StudentInfoDto dto = new StudentInfoDto();
        Set<CourseDto> courseSet = new HashSet<>();
        List<EnrollmentDto> enrollmentList = new ArrayList<>();

        System.out.println(resultSet.toString());
//        // Iterate through the result set to populate the DTO
        for (Object[] result : resultSet) {
            // Map the fields correctly
            Long fetchedUserId = (Long) result[0]; // userId
            String name = (String) result[1]; // name
            String email = (String) result[2]; // email
            String phone = result[3].toString(); // phone
            String photo = (String) result[4]; // photo

            // Set user-level details
            dto.setUserId(fetchedUserId);
            dto.setName(name);
            dto.setEmail(email);
            dto.setPhone(phone);
            dto.setPhoto(photo);

            // Set course details
            Long courseId = (Long) result[5]; // courseId
            String title =  result[6].toString();
            String description =  result[7].toString();

            CourseDto courseDTO = new CourseDto();
            courseDTO.setId(courseId);
            courseDTO.setTitle(title);
            courseDTO.setDescription(description);

            courseSet.add(courseDTO); // Add to set of courses

            // Set enrollment details
            Long enrollmentId = (Long) result[8]; // enrollmentId
            Double grade = (Double) result[9];
            Integer attendance = (Integer) result[10];

            EnrollmentDto enrollmentDTO = new EnrollmentDto();
            enrollmentDTO.setId(enrollmentId);
            enrollmentDTO.setGrade(grade);
            enrollmentDTO.setAttendance(attendance);
            enrollmentDTO.setCourse(courseDTO);


            StudentInfoDto studentInfoDto = new StudentInfoDto();
//            enrollmentDTO.setStudentName(studentProfile.getUser().getName());
            enrollmentDTO.setCourseTitle(courseDTO.getTitle());

            enrollmentList.add(enrollmentDTO); // Add to list of enrollments
        }

        // Set the courses and enrollments in the DTO
        dto.setCourses(courseSet);
        dto.setEnrollmentList(enrollmentList);

        return dto;

    }



}
