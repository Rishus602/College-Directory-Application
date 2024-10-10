package com.sahni.repository;

import com.sahni.Entity.Enrollment;
import com.sahni.Entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {



    List<Enrollment> findByStudent(StudentProfile student);

//    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId")
//    List<Enrollment> findByCourse(@Param("courseId") Long courseId);
}
