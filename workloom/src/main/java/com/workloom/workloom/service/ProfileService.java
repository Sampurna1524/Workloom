package com.workloom.workloom.service;

import com.workloom.workloom.dto.*;
import com.workloom.workloom.model.*;
import com.workloom.workloom.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Updates the entire user profile including nested entities.
     *
     * IMPORTANT: Loads a fresh managed User entity from DB using user ID to avoid LazyInitializationException.
     *
     * @param principalUser User instance obtained from JWT (detached)
     * @param dto          Data Transfer Object containing updated profile data
     * @return updated User entity after saving
     */
    public User updateUserProfile(User principalUser, UserProfileDto dto) {

        // Fetch user fresh from the DB to ensure Hibernate session management
        Optional<User> userOpt = userRepository.findById(principalUser.getId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        User user = userOpt.get();

        // Update simple fields
        user.setFullName(dto.getFullName());
        user.setHeadline(dto.getHeadline());
        user.setSummary(dto.getSummary());
        user.setProfilePhotoUrl(dto.getProfilePhotoUrl());
        user.setSkills(dto.getSkills());
        user.setLinkedinUrl(dto.getLinkedinUrl());
        user.setResumeUrl(dto.getResumeUrl());
        user.setResumeLink(dto.getResumeLink());

        // Update nested collections safely

        // Experiences
        if (user.getExperiences() == null) {
            user.setExperiences(new ArrayList<>());
        } else {
            user.getExperiences().clear();
        }
        if (dto.getExperiences() != null) {
            List<Experience> experiences = dto.getExperiences()
                    .stream()
                    .map(this::toExperienceEntity)
                    .collect(Collectors.toList());
            experiences.forEach(e -> e.setUser(user));
            user.getExperiences().addAll(experiences);
        }

        // Educations
        if (user.getEducations() == null) {
            user.setEducations(new ArrayList<>());
        } else {
            user.getEducations().clear();
        }
        if (dto.getEducations() != null) {
            List<Education> educations = dto.getEducations().stream()
                    .map(this::toEducationEntity)
                    .collect(Collectors.toList());
            educations.forEach(e -> e.setUser(user));
            user.getEducations().addAll(educations);
        }

        // Certifications
        if (user.getCertifications() == null) {
            user.setCertifications(new ArrayList<>());
        } else {
            user.getCertifications().clear();
        }
        if (dto.getCertifications() != null) {
            List<Certification> certifications = dto.getCertifications().stream()
                    .map(this::toCertificationEntity)
                    .collect(Collectors.toList());
            certifications.forEach(c -> c.setUser(user));
            user.getCertifications().addAll(certifications);
        }

        // Projects
        if (user.getProjects() == null) {
            user.setProjects(new ArrayList<>());
        } else {
            user.getProjects().clear();
        }
        if (dto.getProjects() != null) {
            List<Project> projects = dto.getProjects().stream()
                    .map(this::toProjectEntity)
                    .collect(Collectors.toList());
            projects.forEach(p -> p.setUser(user));
            user.getProjects().addAll(projects);
        }

        // Languages
        if (user.getLanguages() == null) {
            user.setLanguages(new ArrayList<>());
        } else {
            user.getLanguages().clear();
        }
        if (dto.getLanguages() != null) {
            List<Language> languages = dto.getLanguages().stream()
                    .map(this::toLanguageEntity)
                    .collect(Collectors.toList());
            languages.forEach(l -> l.setUser(user));
            user.getLanguages().addAll(languages);
        }

        // Persist the updates and return managed entity
        return userRepository.save(user);
    }


    // Helper methods: DTO to Entity conversions

    private Experience toExperienceEntity(ExperienceDto dto) {
        if (dto == null) return null;
        Experience e = new Experience();
        e.setId(dto.getId());
        e.setTitle(dto.getTitle());
        e.setCompany(dto.getCompany());
        e.setLocation(dto.getLocation());
        e.setStartDate(dto.getStartDate());
        e.setEndDate(dto.getEndDate());
        e.setDescription(dto.getDescription());
        return e;
    }

    private Education toEducationEntity(EducationDto dto) {
        if (dto == null) return null;
        Education e = new Education();
        e.setId(dto.getId());
        e.setSchool(dto.getSchool());
        e.setDegree(dto.getDegree());
        e.setFieldOfStudy(dto.getFieldOfStudy());
        e.setStartDate(dto.getStartDate());
        e.setEndDate(dto.getEndDate());
        e.setDescription(dto.getDescription());
        return e;
    }

    private Certification toCertificationEntity(CertificationDto dto) {
        if (dto == null) return null;
        Certification c = new Certification();
        c.setId(dto.getId());
        c.setName(dto.getName());
        c.setIssuingOrganization(dto.getIssuingOrganization());
        c.setIssueDate(dto.getIssueDate());
        c.setExpirationDate(dto.getExpirationDate());
        c.setCredentialId(dto.getCredentialId());
        c.setCredentialUrl(dto.getCredentialUrl());
        return c;
    }

    private Project toProjectEntity(ProjectDto dto) {
        if (dto == null) return null;
        Project p = new Project();
        p.setId(dto.getId());
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescription());
        p.setUrl(dto.getUrl());
        p.setStartDate(dto.getStartDate());
        p.setEndDate(dto.getEndDate());
        return p;
    }

    private Language toLanguageEntity(LanguageDto dto) {
        if (dto == null) return null;
        Language l = new Language();
        l.setId(dto.getId());
        l.setName(dto.getName());
        l.setProficiency(dto.getProficiency());
        return l;
    }
}
