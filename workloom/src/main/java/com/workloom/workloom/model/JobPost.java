package com.workloom.workloom.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_posts")
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    private String companyName;
    private String location;
    private String type; // full-time, part-time, etc.
    private Double salary;

    private LocalDateTime postedAt;

    // Link to CompanyProfile (ensure company only)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_profile_id")
    private CompanyProfile companyProfile;

    // For auditing (optional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by_user")
    private User postedBy;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public LocalDateTime getPostedAt() { return postedAt; }
    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }

    public CompanyProfile getCompanyProfile() { return companyProfile; }
    public void setCompanyProfile(CompanyProfile companyProfile) { this.companyProfile = companyProfile; }

    public User getPostedBy() { return postedBy; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }
}
