package com.workloom.workloom.repository;

import com.workloom.workloom.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByCompanyProfileId(Long companyProfileId);
    List<JobPost> findByPostedById(Long userId);
}
