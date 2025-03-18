package com.sinchana.jobportal.controller;

import com.sinchana.jobportal.entity.JobPostActivity;
import com.sinchana.jobportal.entity.JobSeekerProfile;
import com.sinchana.jobportal.entity.JobSeekerSave;
import com.sinchana.jobportal.entity.Users;
import com.sinchana.jobportal.services.JobPostActivityService;
import com.sinchana.jobportal.services.JobSeekerProfileService;
import com.sinchana.jobportal.services.JobSeekerSaveService;
import com.sinchana.jobportal.services.UsersService;
import org.apache.juli.logging.Log;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class JobSeekerSaveController {

    private final UsersService usersService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerSaveService jobSeekerSaveService;

    public JobSeekerSaveController(UsersService usersService, JobSeekerProfileService jobSeekerProfileService, JobPostActivityService jobPostActivityService, JobSeekerSaveService jobSeekerSaveService) {
        this.usersService = usersService;
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerSaveService = jobSeekerSaveService;
    }

    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);

            if (seekerProfile.isPresent() && jobPostActivity != null) {
                Optional<JobSeekerSave> existingJobSeekerSave = jobSeekerSaveService.findByUserAndJob(seekerProfile.get(), jobPostActivity);

                JobSeekerSave jobSeekerSave;
                if (existingJobSeekerSave.isPresent()) {
                    jobSeekerSave = existingJobSeekerSave.get();
                } else {
                    jobSeekerSave = new JobSeekerSave();
                }

                jobSeekerSave.setJob(jobPostActivity);
                jobSeekerSave.setUserId(seekerProfile.get());

                jobSeekerSaveService.addNew(jobSeekerSave);
            } else {
                throw new RuntimeException("User not found");
            }
        }
        return "redirect:/dashboard/";
    }


    @GetMapping("saved-jobs/")
    public String savedJobs(Model model) {

        List<JobPostActivity> jobPost = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();

        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJob((JobSeekerProfile) currentUserProfile);
        for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
            jobPost.add(jobSeekerSave.getJob());
        }

        model.addAttribute("jobPost", jobPost);
        model.addAttribute("user", currentUserProfile);

        return "saved-jobs";
    }
}