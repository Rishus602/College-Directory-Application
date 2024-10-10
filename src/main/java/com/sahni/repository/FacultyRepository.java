package com.sahni.repository;

import com.sahni.Entity.Department;
import com.sahni.Entity.Enrollment;
import com.sahni.Entity.FacultyProfile;
import com.sahni.Entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyProfile, Long> {

    boolean existsByUserName(String name);


    @Query(value = "SELECT * FROM faculty_profile fp " +
            "JOIN users u ON fp.user_id = u.id " +
            "WHERE u.username = :username",
            nativeQuery = true)
    Optional<FacultyProfile> findByUserName(@Param("username") String username);

//    @Query(
//            value = "SELECT fp.department_id, d.name\n" +
//                    "FROM faculty_profile fp\n" +
//                    "JOIN department d \n" +
//                    "ON fp.department_id = d.id\n" +
//                    "where d.name = :name"
//    )




    @Query(value = "SELECT * FROM faculty_profile sp " +
            "JOIN users u ON sp.user_id = u.id " +
            "WHERE u.name LIKE CONCAT('%', :name, '%')",
            nativeQuery = true)
    List<FacultyProfile> findByName(@Param("name") String name);



}
