package com.sahni.repository;

import com.sahni.Entity.StudentProfile;
import com.sahni.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentProfile, Long> {

    @Query(value = "SELECT * FROM student_profile sp " +
            "JOIN users u ON sp.user_id = u.id " +
            "WHERE u.name LIKE CONCAT('%', :name, '%')",
            nativeQuery = true)
    List<StudentProfile> findByName(@Param("name") String name);


    @Query(value = "SELECT * FROM student_profile sp " +
            "JOIN users u ON sp.user_id = u.id " +
            "WHERE u.username = :username",
            nativeQuery = true)
    Optional<StudentProfile> findByUserName(@Param("username") String username);



    boolean existsByUserName(String username);

}
