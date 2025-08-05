package com.workloom.workloom.service;

import com.workloom.workloom.dto.JobPostDto;
import com.workloom.workloom.model.CompanyProfile;
import com.workloom.workloom.model.JobPost;
import com.workloom.workloom.model.User;
import com.workloom.workloom.repository.JobPostRepository;
import com.workloom.workloom.repository.CompanyProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    private final CompanyProfileRepository companyProfileRepository;

    public JobPostService(JobPostRepository jobPostRepository, CompanyProfileRepository companyProfileRepository) {
        this.jobPostRepository = jobPostRepository;
        this.companyProfileRepository = companyProfileRepository;
    }

    public JobPostDto createJobPost(JobPostDto dto, User companyUser) {
        // Check company profile
        CompanyProfile companyProfile = companyProfileRepository.findByUserId(companyUser.getId())
                .orElseThrow(() -> new RuntimeException("Company profile not found"));

        JobPost job = new JobPost();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setCompanyName(companyProfile.getCompanyName());
        job.setLocation(dto.getLocation());
        job.setType(dto.getType());
        job.setSalary(dto.getSalary());
        job.setPostedAt(LocalDateTime.now());
        job.setCompanyProfile(companyProfile);
        job.setPostedBy(companyUser);

        job = jobPostRepository.save(job);
        return toDto(job);
    }

    public List<JobPostDto> getAllJobPosts() {
        return jobPostRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public JobPostDto getJobPostById(Long id) {
        return jobPostRepository.findById(id).map(this::toDto).orElse(null);
    }

    private JobPostDto toDto(JobPost job) {
        JobPostDto dto = new JobPostDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCompanyName(job.getCompanyName());
        dto.setLocation(job.getLocation());
        dto.setType(job.getType());
        dto.setSalary(job.getSalary());
        dto.setPostedAt(job.getPostedAt());
        dto.setCompanyProfileId(job.getCompanyProfile() != null ? job.getCompanyProfile().getId() : null);
        dto.setPostedByUserId(job.getPostedBy() != null ? job.getPostedBy().getId() : null);
        return dto;
    }
}
