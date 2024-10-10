package com.sahni.repository;

import com.sahni.Entity.AdministratorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository  extends JpaRepository<AdministratorProfile , Long> {
}
