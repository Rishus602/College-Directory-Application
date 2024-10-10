package com.sahni.repository;

import com.sahni.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {


//    @Query(value = "select fp. from faculty_profile fp\n" +
//            "join course c\n" +
//            "on c.faculty_id = fp.user_id\n" +
//            "where c.faculty_id = :id;" , nativeQuery = true)
//    Optional<Course> findByFacultyId(@Param("id") Long id);

//    @Query(value = "SELECT " +
//            "fp.user_id ui, " +  // Change this to your required fields
//            "fp.photo, " +
//            "fp.office_hours, " +
//            "c.title, " +
//            "c.description, " +
//            "c.faculty_id as fi " +  // Give alias to avoid conflict
//            "FROM faculty_profile fp " +
//            "JOIN course c ON c.faculty_id = fp.user_id " +
//            "WHERE c.faculty_id = :id",
//            nativeQuery = true)

    @Query(value = "SELECT " +
            "fp.user_id AS ui, " +  // Faculty user ID
            "fp.photo, " +
            "fp.office_hours, " +
            "c.* " + // Explicitly select the course ID
            "FROM faculty_profile fp " +
            "JOIN course c ON c.faculty_id = fp.user_id " +
            "WHERE c.faculty_id = :id",
            nativeQuery = true)
    List<Course> findByFacultyId(@Param("id") Long id);

    
}
