package com.workloom.workloom.repository;

import com.workloom.workloom.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByApplicantId(Long userId);
    List<JobApplication> findByJobPostId(Long jobPostId);
}
