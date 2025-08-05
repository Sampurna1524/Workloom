package com.workloom.workloom.dto;

import java.time.LocalDateTime;

public class JobPostDto {
    private Long id;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private String type;
    private Double salary;
    private LocalDateTime postedAt;
    private Long companyProfileId;
    private Long postedByUserId;

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

    public Long getCompanyProfileId() { return companyProfileId; }
    public void setCompanyProfileId(Long companyProfileId) { this.companyProfileId = companyProfileId; }

    public Long getPostedByUserId() { return postedByUserId; }
    public void setPostedByUserId(Long postedByUserId) { this.postedByUserId = postedByUserId; }
}
