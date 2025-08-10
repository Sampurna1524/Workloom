package com.workloom.workloom.controller;

import com.workloom.workloom.dto.JobApplicationDto;
import com.workloom.workloom.model.User;
import com.workloom.workloom.model.UserRole;
import com.workloom.workloom.service.JobApplicationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    // Apply with file upload (resume)
    @PostMapping(value = "/apply/{jobPostId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyToJob(
            @PathVariable Long jobPostId,
            @RequestParam("coverLetter") String coverLetter,
            @RequestParam(value = "resume", required = false) MultipartFile resume,
            Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof User user) ||
                user.getRole() != UserRole.JOB_SEEKER) {
            return ResponseEntity.status(403).body("Only job seekers can apply to jobs.");
        }

        try {
            JobApplicationDto application = jobApplicationService.apply(jobPostId, coverLetter, resume, user);
            return ResponseEntity.ok(application);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/mine")
    public ResponseEntity<?> myApplications(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User user) ||
                user.getRole() != UserRole.JOB_SEEKER) {
            return ResponseEntity.status(403).body("Only job seekers can view their applications.");
        }
        List<JobApplicationDto> apps = jobApplicationService.getApplicationsByApplicant(user.getId());
        return ResponseEntity.ok(apps);
    }

    @GetMapping("/for-job/{jobPostId}")
    public ResponseEntity<?> getApplicationsForJob(@PathVariable Long jobPostId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User user) ||
                user.getRole() != UserRole.COMPANY) {
            return ResponseEntity.status(403).body("Only companies can see applicants for their jobs.");
        }
        List<JobApplicationDto> apps = jobApplicationService.getApplicationsForJob(jobPostId);
        return ResponseEntity.ok(apps);
    }
}
