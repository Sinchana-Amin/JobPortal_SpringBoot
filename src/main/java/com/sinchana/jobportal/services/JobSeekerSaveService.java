package com.sinchana.jobportal.services;

import com.sinchana.jobportal.entity.JobPostActivity;
import com.sinchana.jobportal.entity.JobSeekerProfile;
import com.sinchana.jobportal.entity.JobSeekerSave;
import com.sinchana.jobportal.repository.JobSeekerSaveRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;
    private static final Logger log = LoggerFactory.getLogger(JobSeekerSaveService.class);

    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
        return jobSeekerSaveRepository.findByJob(job);
    }

    @Transactional
    public void addNew(JobSeekerSave jobSeekerSave) {
        Optional<JobSeekerSave> existingJob = jobSeekerSaveRepository.findByUserIdAndJob(jobSeekerSave.getUserId(), jobSeekerSave.getJob());

        if (existingJob.isPresent()) {
            log.info("Existing JobSeekerSave found: " + existingJob.get().getId());
            jobSeekerSave.setId(existingJob.get().getId()); // Prevent duplicate insertions
        } else {
            log.info("No existing JobSeekerSave found, inserting new");
        }

        jobSeekerSaveRepository.save(jobSeekerSave);
    }


    public Optional<JobSeekerSave> findByUserAndJob(JobSeekerProfile user, JobPostActivity job) {
        return jobSeekerSaveRepository.findByUserIdAndJob(user, job);
    }
}
