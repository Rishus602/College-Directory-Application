package com.sahni.repository;

import com.sahni.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    boolean  existsByUsername(String username);



    @Query(value = "select fp.user_id, u.name as faculty_name, u.email, u.phone, fp.photo, \n" +
            "c.id as course_id, c.title as course_title, c.description, \n" +
            "e.id as enrollment_id, e.grade, e.attendance, \n" +
            "s.user_id as student_id, u.name as student_name \n" +
            "from faculty_profile fp \n" +
            "join course c on c.faculty_id = fp.user_id \n" +
            "join enrollment e on e.course_id = c.id \n" +
            "join student_profile s on s.user_id = e.student_id \n" +
            "join users u on u.id = fp.user_id\n" +
//            "join users u on u.id = s.user_id \n" +
            "where fp.user_id = :userId",
            nativeQuery = true)
    List<Object[]> getFacultyStudentDetails(@Param("userId") Long userId);
}
