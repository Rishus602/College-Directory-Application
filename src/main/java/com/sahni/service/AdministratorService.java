package com.sahni.service;

import com.sahni.Dto.*;
import com.sahni.Entity.*;
import com.sahni.Exception.InvalidFieldException;
import com.sahni.Exception.ResourceNotFoundException;
import com.sahni.Exception.UserAlreadyExistsException;
import com.sahni.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdministratorService {



    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private  CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    // FOR STUDENT PROFILE - CREATE, UPDATE, DELETE
    @Transactional
    public ResponseEntity<StudentProfileCreationDto> createStudentProfile(StudentProfileCreationDto dto) {



        if (dto.getDepartmentId() == null) {
            throw new InvalidFieldException("departmentId", "Department ID must not be null.");
        }

        if (studentRepository.existsByUserName(dto.getUsername())) {
            throw new UserAlreadyExistsException("User already exists with username: " + dto.getUsername());
        }

        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Department Id is not found in the database."));


        // Step 2: Validate the department name
        if (!department.getName().equals(dto.getDepartmentName())) {
            // Step 3: Set the department in the student profile
            throw new IllegalArgumentException("Department name does not match the ID provided.");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.STUDENT);
        user.setPhone(dto.getPhone());

// Encode the password before saving it to the database
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
//        dto.setUserId(user.getId());// Set the encoded password
        userRepository.save(user);


        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setUser(user);


//        studentProfile.setDepartment(fetchDepartmentByName(dto.getDepartmentName()));
        studentProfile.setPhoto(dto.getPhoto());
        studentProfile.setYear(dto.getYear());

        studentProfile.setDepartment(department);
        studentRepository.save(studentProfile);
        dto.setUserId(user.getId());
        dto.setRole(user.getRole());
        System.out.println(dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @Transactional
    public ResponseEntity<StudentProfileCreationDto> updateStudentProfile(StudentProfileCreationDto dto){

        if (dto.getDepartmentId() == null) {
            throw new InvalidFieldException("departmentId", "Department ID must not be null.");
        }

        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Department Id is not found in the database."));


        // Check if the department name matches the provided name
        if (!department.getName().equals(dto.getDepartmentName())) {
            throw new IllegalArgumentException("Department name does not match the provided department ID.");
        }

        StudentProfile studentProfile = studentRepository.findByUserName(dto.getUsername()).orElseThrow(()-> new ResourceNotFoundException("username does not found , Please Create new Student Profile"));

        // Update the User details if there are changes
        User user = studentProfile.getUser(); // Get the existing user associated with the student profile

        boolean userUpdated = false; // Track if the user was updated

        // Check and update each field if necessary
        if (!user.getName().equals(dto.getName()) && dto.getName() != null) {
            user.setName(dto.getName());
            userUpdated = true;
        }


        if (!user.getEmail().equals(dto.getEmail()) && dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
            userUpdated = true;
        }
        if (!user.getPhone().equals(dto.getPhone()) && dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
            userUpdated = true;
        }

        // Only update the password if it is provided and differs from the current one
        if (dto.getPassword() != null && !dto.getPassword().isEmpty() &&
                !user.getPassword().equals(passwordEncoder.encode(dto.getPassword()))) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            userUpdated = true;
        }

        // Save the updated user entity if any field was modified
        if (userUpdated) {
            userRepository.save(user);
        }

        // Update the StudentProfile details only if there are changes
        boolean profileUpdated = false; // Track if the profile was updated

        if (!studentProfile.getPhoto().equals(dto.getPhoto()) && dto.getPhoto() != null) {
            studentProfile.setPhoto(dto.getPhoto());
            profileUpdated = true;
        }
        if (!studentProfile.getYear().equals(dto.getYear())) {
            studentProfile.setYear(dto.getYear());
            profileUpdated = true;
        }

        if (!studentProfile.getDepartment().equals(department) && dto.getDepartmentName() != null) {
            studentProfile.setDepartment(department); // Update the department if changed
            profileUpdated = true;
        }

        // Save the updated StudentProfile if any field was modified
        if (profileUpdated) {
            studentRepository.save(studentProfile);
        }

        dto.setPassword(user.getPassword());
        // Prepare the response DTO
        dto.setUserId(user.getId());
        dto.setRole(user.getRole());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteStudentProfile(String username){
        StudentProfile studentProfile = studentRepository.findByUserName(username).orElseThrow(()-> new ResourceNotFoundException("User with name " + username + " not found"));


        // Optional: Fetch the associated User if needed
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));

        studentRepository.delete(studentProfile);

        userRepository.delete(user);

        return new ResponseEntity<>("Student profile with username " + username + " has been deleted successfully.", HttpStatus.OK);
    }


    // FOR FACULTY PROFILE - CREATE, UPDATE, DELETE

    @Transactional
    public ResponseEntity<FacultyProfileDto> createFacultyProfile(FacultyProfileDto dto) {

        if (dto.getDepartmentId() == null) {
            throw new InvalidFieldException("departmentId", "Department ID must not be null.");
        }

        if (facultyRepository.existsByUserName(dto.getUsername())) {
            throw new UserAlreadyExistsException("User already exists with username: " + dto.getUsername());
        }

        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Department Id is not found in the database."));

        User user  = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(Role.FACULTY_MEMBER);
        user.setUsername(dto.getUsername());
        userRepository.save(user);

        FacultyProfile facultyProfile = new FacultyProfile();
        facultyProfile.setPhoto(dto.getPhoto());
        facultyProfile.setUser(user);
        facultyProfile.setDepartment(department);
        facultyProfile.setOfficeHours(dto.getOfficeHours());
        facultyRepository.save(facultyProfile);
        return new ResponseEntity<>(dto,  HttpStatus.CREATED);
    }


    @Transactional
    public ResponseEntity<FacultyProfileDto> updateFacultyProfile(FacultyProfileDto dto){

//        if (dto.getDepartmentId() == null) {
//            throw new InvalidFieldException("departmentId", "Department ID must not be null.");
//        }
//        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Department Id is not found in the database."));
//        // Check if the department name matches the provided name
//        if (!department.getName().equals(dto.getDepartmentName())) {
//            throw new IllegalArgumentException("Department name does not match the provided department ID.");
//        }
//        // If department doesn't exist, throw an exception
//        if (department == null) {
//            throw new EntityNotFoundException("Department with name " + dto.getDepartmentName() + " not found.");
//        }
//        Department department =    departmentRepository.findIdByDepartmentName(dto.getDepartmentName()).orElseThrow(()-> new ResourceNotFoundException("Department with name " + dto.getDepartmentName() + " not found."));
//        //old values
//        String oldDepartmentName = facultyProfile.getDepartment().getName();
//        String OldName = user.getName();
//        String oldEmail = user.getEmail();
//        String oldPassword = user.getPassword();
//        String oldPhone  = user.getPhone();
//        String oldPhoto = facultyProfile.getPhoto();
//        String oldOfficeHours = facultyProfile.getOfficeHours();

        FacultyProfile facultyProfile = facultyRepository.findByUserName(dto.getUsername()).orElseThrow(()-> new ResourceNotFoundException("username does not found , Please Create new Faculty Profile"));
        // Update the User details if there are changes
        User user = facultyProfile.getUser(); // Get the existing user associated with the faculty profile
        // Handle department name update
        Department department;




        boolean userUpdated = false; // Track if the user was updated

        // Check and update each field if necessary
        if (!user.getName().equals(dto.getName()) && dto.getName() != null) {
            user.setName(dto.getName());
            userUpdated = true;
        }


        if (!user.getEmail().equals(dto.getEmail()) && dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
            userUpdated = true;
        }
        if (!user.getPhone().equals(dto.getPhone()) && dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
            userUpdated = true;
        }

        // Only update the password if it is provided and differs from the current one
        if (dto.getPassword() != null && !dto.getPassword().isEmpty() &&
                !user.getPassword().equals(passwordEncoder.encode(dto.getPassword()))) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            userUpdated = true;
        }

        // Save the updated user entity if any field was modified
        if (userUpdated) {
            userRepository.save(user);
        }

        // Update the FacultyProfile details only if there are changes
        boolean profileUpdated = false; // Track if the profile was updated

        if (!facultyProfile.getPhoto().equals(dto.getPhoto()) && dto.getPhoto() != null) {
            facultyProfile.setPhoto(dto.getPhoto());
            profileUpdated = true;
        }
        if (!facultyProfile.getOfficeHours().equals(dto.getOfficeHours()) && dto.getOfficeHours() != null) {
            facultyProfile.setOfficeHours(dto.getOfficeHours());
            profileUpdated = true;
        }

        if (dto.getDepartmentName() != null) {
            // If department name is provided, check if it exists and update
            department = departmentRepository.findIdByDepartmentName(dto.getDepartmentName())
                    .orElseThrow(() -> new ResourceNotFoundException("Department with name " + dto.getDepartmentName() + " not found."));

            // Update the faculty's department
            facultyProfile.setDepartment(department);
            profileUpdated = true;
        } else {
            // If departmentName is null, keep the old department
            department = facultyProfile.getDepartment();
        }



// Update the faculty's department with the found department ID
//        facultyProfile.setDepartment(department);

//        if (!facultyProfile.getDepartment().equals(dto.getDepartmentName())) {
//            facultyProfile.setDepartment(department); // Update the department if changed
//            profileUpdated = true;
//        }

        // Save the updated StudentProfile if any field was modified
        if (profileUpdated) {
            facultyRepository.save(facultyProfile);
        }


        dto.setDepartmentId(department.getId());


        //old values


//        if (!oldEmail.equals(user.getEmail())){
//            dto.setEmail(user.getEmail());
//        }

        // Set the updated values back to the DTO for the response
        dto.setPhoto(facultyProfile.getPhoto());
        dto.setOfficeHours(facultyProfile.getOfficeHours());
        dto.setDepartmentId(facultyProfile.getDepartment().getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setPassword(user.getPassword());
        dto.setDepartmentName(facultyProfile.getDepartment().getName());



        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteFacultyProfile(String username){
        FacultyProfile facultyProfile = facultyRepository.findByUserName(username).orElseThrow(()-> new ResourceNotFoundException("User with name " + username + " not found"));


        // Optional: Fetch the associated User if needed
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));

        facultyRepository.delete(facultyProfile);

        userRepository.delete(user);

        return new ResponseEntity<>("Faculty profile with username " + username + " has been deleted successfully.", HttpStatus.OK);
    }


    // GET ALL DETAILS - ALL USERS, STUDENT DETAILS, FACULTY DETAILS

    public ResponseEntity<List<User>> getAllAdminDetails(){
        List<User> userList = userRepository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


    public ResponseEntity<List<StudentProfile>> getStudentDetails(){
        List<StudentProfile> studentProfile = studentRepository.findAll();

        return new ResponseEntity<>(studentProfile, HttpStatus.OK);
    }


    public ResponseEntity<List<FacultyProfile>> getFacultyDetails(){
        List<FacultyProfile> studentProfile = facultyRepository.findAll();

        return new ResponseEntity<>(studentProfile, HttpStatus.OK);
    }


    public ResponseEntity<CourseCreationDto> createCourse(CourseCreationDto dto){


        Course course = new Course();

        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(()-> new ResourceNotFoundException("Id not found with associated Department"));

        FacultyProfile facultyProfile = facultyRepository.findById(dto.getFacultyId()).orElseThrow(()-> new ResourceNotFoundException("Id not found with Associate faculty profile"));
        course.setDepartment(department);
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setFaculty(facultyProfile);

        courseRepository.save(course);



        return new ResponseEntity<>(dto,HttpStatus.CREATED);



    }


    @Transactional
    public ResponseEntity<EnrollmentRequestDto> enrollStudentInCourse(EnrollmentRequestDto enrollmentRequestDto) {
        // Fetch student by ID
        StudentProfile student = studentRepository.findById(enrollmentRequestDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        // Fetch course by ID
        Course course = courseRepository.findById(enrollmentRequestDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        // Create a new enrollment entry
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(null); // Grade is null initially
        enrollment.setAttendance(0); // Attendance starts at 0
        // Save enrollment in the database
        enrollmentRepository.save(enrollment);
        // Return DTO with enrollment details
       return new ResponseEntity<>(enrollmentRequestDto, HttpStatus.CREATED);
    }
}
