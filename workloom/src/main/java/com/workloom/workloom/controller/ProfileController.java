package com.workloom.workloom.controller;

import com.workloom.workloom.dto.*;
import com.workloom.workloom.model.User;
import com.workloom.workloom.repository.UserRepository;
import com.workloom.workloom.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    private final UserRepository userRepository;
    private final ProfileService profileService;

    public ProfileController(UserRepository userRepository, ProfileService profileService) {
        this.userRepository = userRepository;
        this.profileService = profileService;
    }

    // DTO conversion helpers for response mapping

    private ExperienceDto toDto(com.workloom.workloom.model.Experience e) {
        if (e == null) return null;
        ExperienceDto dto = new ExperienceDto();
        dto.setId(e.getId());
        dto.setTitle(e.getTitle());
        dto.setCompany(e.getCompany());
        dto.setLocation(e.getLocation());
        dto.setStartDate(e.getStartDate());
        dto.setEndDate(e.getEndDate());
        dto.setDescription(e.getDescription());
        return dto;
    }

    private EducationDto toDto(com.workloom.workloom.model.Education e) {
        if (e == null) return null;
        EducationDto dto = new EducationDto();
        dto.setId(e.getId());
        dto.setSchool(e.getSchool());
        dto.setDegree(e.getDegree());
        dto.setFieldOfStudy(e.getFieldOfStudy());
        dto.setStartDate(e.getStartDate());
        dto.setEndDate(e.getEndDate());
        dto.setDescription(e.getDescription());
        return dto;
    }

    private CertificationDto toDto(com.workloom.workloom.model.Certification c) {
        if (c == null) return null;
        CertificationDto dto = new CertificationDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setIssuingOrganization(c.getIssuingOrganization());
        dto.setIssueDate(c.getIssueDate());
        dto.setExpirationDate(c.getExpirationDate());
        dto.setCredentialId(c.getCredentialId());
        dto.setCredentialUrl(c.getCredentialUrl());
        return dto;
    }

    private ProjectDto toDto(com.workloom.workloom.model.Project p) {
        if (p == null) return null;
        ProjectDto dto = new ProjectDto();
        dto.setId(p.getId());
        dto.setTitle(p.getTitle());
        dto.setDescription(p.getDescription());
        dto.setUrl(p.getUrl());
        dto.setStartDate(p.getStartDate());
        dto.setEndDate(p.getEndDate());
        return dto;
    }

    private LanguageDto toDto(com.workloom.workloom.model.Language lang) {
        if (lang == null) return null;
        LanguageDto dto = new LanguageDto();
        dto.setId(lang.getId());
        dto.setName(lang.getName());
        dto.setProficiency(lang.getProficiency());
        return dto;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User principalUser)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // Fetch fresh user entity from database to ensure Hibernate session is active
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileDto profileDto = new UserProfileDto();
        profileDto.setFullName(user.getFullName());
        profileDto.setHeadline(user.getHeadline());
        profileDto.setSummary(user.getSummary());
        profileDto.setProfilePhotoUrl(user.getProfilePhotoUrl());
        profileDto.setSkills(user.getSkills());
        profileDto.setLinkedinUrl(user.getLinkedinUrl());
        profileDto.setResumeUrl(user.getResumeUrl());
        profileDto.setResumeLink(user.getResumeLink());

        profileDto.setExperiences(user.getExperiences() != null ?
                user.getExperiences().stream().map(this::toDto).toList() :
                Collections.emptyList());

        profileDto.setEducations(user.getEducations() != null ?
                user.getEducations().stream().map(this::toDto).toList() :
                Collections.emptyList());

        profileDto.setCertifications(user.getCertifications() != null ?
                user.getCertifications().stream().map(this::toDto).toList() :
                Collections.emptyList());

        profileDto.setProjects(user.getProjects() != null ?
                user.getProjects().stream().map(this::toDto).toList() :
                Collections.emptyList());

        profileDto.setLanguages(user.getLanguages() != null ?
                user.getLanguages().stream().map(this::toDto).toList() :
                Collections.emptyList());

        return ResponseEntity.ok(profileDto);
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileDto dto, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User principalUser)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        profileService.updateUserProfile(principalUser, dto);

        return ResponseEntity.ok("Profile updated successfully.");
    }
}
