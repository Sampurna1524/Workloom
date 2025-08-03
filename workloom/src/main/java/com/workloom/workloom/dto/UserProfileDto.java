package com.workloom.workloom.dto;

import java.util.List;

/**
 * Data Transfer Object for User Profile including all nested profile sections.
 */
public class UserProfileDto {

    private String fullName;
    private String headline;
    private String summary;
    private String profilePhotoUrl;
    private String skills;          // Comma-separated string of skills
    private String linkedinUrl;
    private String resumeUrl;       // URL to uploaded resume PDF
    private String resumeLink;      // External resume link (optional)

    private List<ExperienceDto> experiences;
    private List<EducationDto> educations;
    private List<CertificationDto> certifications;
    private List<ProjectDto> projects;
    private List<LanguageDto> languages;

    // No-argument constructor
    public UserProfileDto() {
    }

    // All-argument constructor (optional)
    public UserProfileDto(String fullName, String headline, String summary, String profilePhotoUrl, String skills, 
                          String linkedinUrl, String resumeUrl, String resumeLink, List<ExperienceDto> experiences,
                          List<EducationDto> educations, List<CertificationDto> certifications,
                          List<ProjectDto> projects, List<LanguageDto> languages) {
        this.fullName = fullName;
        this.headline = headline;
        this.summary = summary;
        this.profilePhotoUrl = profilePhotoUrl;
        this.skills = skills;
        this.linkedinUrl = linkedinUrl;
        this.resumeUrl = resumeUrl;
        this.resumeLink = resumeLink;
        this.experiences = experiences;
        this.educations = educations;
        this.certifications = certifications;
        this.projects = projects;
        this.languages = languages;
    }

    // Getters and Setters

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public List<ExperienceDto> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceDto> experiences) {
        this.experiences = experiences;
    }

    public List<EducationDto> getEducations() {
        return educations;
    }

    public void setEducations(List<EducationDto> educations) {
        this.educations = educations;
    }

    public List<CertificationDto> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<CertificationDto> certifications) {
        this.certifications = certifications;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }

    public List<LanguageDto> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageDto> languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return "UserProfileDto{" +
                "fullName='" + fullName + '\'' +
                ", headline='" + headline + '\'' +
                ", summary='" + summary + '\'' +
                ", profilePhotoUrl='" + profilePhotoUrl + '\'' +
                ", skills='" + skills + '\'' +
                ", linkedinUrl='" + linkedinUrl + '\'' +
                ", resumeUrl='" + resumeUrl + '\'' +
                ", resumeLink='" + resumeLink + '\'' +
                ", experiences=" + experiences +
                ", educations=" + educations +
                ", certifications=" + certifications +
                ", projects=" + projects +
                ", languages=" + languages +
                '}';
    }
}
