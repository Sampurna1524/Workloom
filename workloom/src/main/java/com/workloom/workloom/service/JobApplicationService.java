package com.workloom.workloom.service;

import com.workloom.workloom.dto.JobApplicationDto;
import com.workloom.workloom.model.JobApplication;
import com.workloom.workloom.model.JobPost;
import com.workloom.workloom.model.User;
import com.workloom.workloom.repository.JobApplicationRepository;
import com.workloom.workloom.repository.JobPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostRepository jobPostRepository;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository, JobPostRepository jobPostRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobPostRepository = jobPostRepository;
    }

    public JobApplicationDto apply(Long jobPostId, String coverLetter, MultipartFile resume, User applicant) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Prevent duplicate application
        boolean alreadyApplied = jobApplicationRepository.findByApplicantId(applicant.getId())
                .stream()
                .anyMatch(app -> app.getJobPost().getId().equals(jobPostId));
        if (alreadyApplied) {
            throw new RuntimeException("Already applied to this job.");
        }

        String resumeUrl = null;

        if (resume != null && !resume.isEmpty()) {
            String originalFilename = resume.getOriginalFilename();
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                originalFilename = "resume.pdf"; // fallback filename
            }
            String filename = UUID.randomUUID() + "-" + originalFilename.replaceAll("\\s+", "_");

            // Use absolute path here, change to a directory with write permission on your system
            String uploadDir = "C:\\workloom\\uploads\\resumes";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                boolean created = uploadDirFile.mkdirs();
                if (!created) {
                    throw new RuntimeException("Failed to create upload directory: " + uploadDirFile.getAbsolutePath());
                }
            }

            File dest = new File(uploadDirFile, filename);
            try {
                resume.transferTo(dest);
                resumeUrl = "/files/resumes/" + filename; // Adjust this URL based on your static resource config
                System.out.println("Resume saved to: " + dest.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload resume: " + e.getMessage(), e);
            }
        }

        JobApplication application = new JobApplication();
        application.setApplicant(applicant);
        application.setJobPost(jobPost);
        application.setCoverLetter(coverLetter);
        application.setAppliedAt(LocalDateTime.now());
        application.setStatus("APPLIED");
        application.setResumeUrl(resumeUrl);

        application = jobApplicationRepository.save(application);
        return toDto(application);
    }

    public List<JobApplicationDto> getApplicationsForJob(Long jobPostId) {
        return jobApplicationRepository.findByJobPostId(jobPostId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<JobApplicationDto> getApplicationsByApplicant(Long applicantId) {
        return jobApplicationRepository.findByApplicantId(applicantId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private JobApplicationDto toDto(JobApplication app) {
        JobApplicationDto dto = new JobApplicationDto();
        dto.setId(app.getId());
        dto.setJobPostId(app.getJobPost().getId());
        dto.setApplicantId(app.getApplicant().getId());
        dto.setApplicantName(app.getApplicant().getFullName());
        dto.setCoverLetter(app.getCoverLetter());
        dto.setAppliedAt(app.getAppliedAt());
        dto.setStatus(app.getStatus());
        dto.setResumeUrl(app.getResumeUrl());
        return dto;
    }
}
