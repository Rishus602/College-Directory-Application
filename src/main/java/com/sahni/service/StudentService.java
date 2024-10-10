package com.sahni.service;

import com.sahni.Dto.CourseDto;
import com.sahni.Dto.EnrollmentDto;
import com.sahni.Dto.StudentAcademicInfoDto;
import com.sahni.Dto.StudentProfileCreationDto;
import com.sahni.Entity.Enrollment;
import com.sahni.Entity.StudentProfile;
import com.sahni.Entity.User;
import com.sahni.Exception.ResourceNotFoundException;
import com.sahni.repository.EnrollmentRepository;
import com.sahni.repository.StudentRepository;
import com.sahni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
     private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public ResponseEntity<List<StudentProfile>> getStudentDetails(){

        List<StudentProfile> studentProfile = studentRepository.findAll();

        return new ResponseEntity<>(studentProfile, HttpStatus.OK);
    }




    public ResponseEntity<List<StudentProfile>> getStudentByName(String name) {

        List<StudentProfile> studentProfiles = studentRepository.findByName(name);

        // Check if the list is empty and throw an exception if necessary
        if (studentProfiles.isEmpty()) {
            throw new ResourceNotFoundException("No students found with the name: " + name);
        }
        return new ResponseEntity<>(studentProfiles, HttpStatus.OK);
    }



public ResponseEntity<StudentAcademicInfoDto> getsStudentAcademicInfo(String username){


    // Fetch the User by username
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));

    // Fetch the StudentProfile by user
    StudentProfile studentProfile = studentRepository.findById(user.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for user " + username));

//    StudentProfile studentProfile = studentRepository.findByUserName(username).orElseThrow(() -> new ResourceNotFoundException("user with name " + username + "not found"));

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(studentProfile);

        List<EnrollmentDto> enrollmentDtos = enrollments.stream()
                .map(enrollment -> new EnrollmentDto(
                        enrollment.getId(),
                        enrollment.getStudent().getUser().getName(),
                        enrollment.getCourse().getTitle(),
                        enrollment.getGrade(),
                        enrollment.getAttendance(),
                        new CourseDto( // Assuming you want to populate the course details
                                enrollment.getCourse().getId(),
                                enrollment.getCourse().getTitle(),
                                enrollment.getCourse().getDescription()
                        )
                )).collect(Collectors.toList());

    // Create and return the StudentProfileCreationDto with academic information
//    StudentProfileCreationDto studentDto = new StudentProfileCreationDto();
//    studentDto.setUsername(user.getUsername());
//    studentDto.setName(user.getName());
//    studentDto.setEmail(user.getEmail());
//    studentDto.setPhone(user.getPhone());
//    studentDto.setPhoto(studentProfile.getPhoto());
//    studentDto.setYear(studentProfile.getYear());
//    studentDto.setDepartmentName(studentProfile.getDepartment().getName());
//    studentDto.setEnrollments(enrollmentDtos); // Set the list of enrollments

    StudentAcademicInfoDto studentAcademicInfoDto = new StudentAcademicInfoDto();
    studentAcademicInfoDto.setEnrollments(enrollmentDtos);
    studentAcademicInfoDto.setStudentName(studentProfile.getUser().getName());
    return new ResponseEntity<>(studentAcademicInfoDto, HttpStatus.OK);
}


}
