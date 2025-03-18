package com.sinchana.jobportal.repository;

import com.sinchana.jobportal.entity.JobSeekerProfile;
import com.sinchana.jobportal.entity.RecruiterProfile;
import com.sinchana.jobportal.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Integer> {

   // Optional<RecruiterProfile> findByUserId(int userId);
}
