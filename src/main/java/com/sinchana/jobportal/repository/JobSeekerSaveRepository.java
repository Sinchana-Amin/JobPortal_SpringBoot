package com.sinchana.jobportal.repository;

import com.sinchana.jobportal.entity.JobPostActivity;
import com.sinchana.jobportal.entity.JobSeekerProfile;
import com.sinchana.jobportal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
    Optional<JobSeekerSave> findByUserIdAndJob(JobSeekerProfile userId, JobPostActivity job);

}
