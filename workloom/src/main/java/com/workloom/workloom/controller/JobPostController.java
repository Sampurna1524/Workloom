package com.workloom.workloom.controller;

import com.workloom.workloom.dto.JobPostDto;
import com.workloom.workloom.model.User;
import com.workloom.workloom.model.UserRole;
import com.workloom.workloom.service.JobPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobPostController {

    private final JobPostService jobPostService;

    public JobPostController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody JobPostDto dto, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        if (user.getRole() != UserRole.COMPANY) {
            return ResponseEntity.status(403).body("Only company accounts can post jobs.");
        }
        JobPostDto createdJob = jobPostService.createJobPost(dto, user);
        return ResponseEntity.ok(createdJob);
    }

    @GetMapping
    public ResponseEntity<List<JobPostDto>> listAllJobs() {
        List<JobPostDto> jobs = jobPostService.getAllJobPosts();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        JobPostDto job = jobPostService.getJobPostById(id);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(job);
    }
}
