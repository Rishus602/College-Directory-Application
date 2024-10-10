package com.sahni.repository;

import com.sahni.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {


    @Query(
            value = "SELECT d.id, d.name,d.description FROM department d WHERE d.name = :name",
            nativeQuery = true
    )
    Optional<Department> findIdByDepartmentName(@Param("name") String name);


}
