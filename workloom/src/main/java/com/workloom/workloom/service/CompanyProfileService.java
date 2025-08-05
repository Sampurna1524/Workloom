package com.workloom.workloom.service;

import com.workloom.workloom.dto.CompanyProfileDto;
import com.workloom.workloom.model.CompanyProfile;
import com.workloom.workloom.model.User;
import com.workloom.workloom.repository.CompanyProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyProfileService {

    private final CompanyProfileRepository companyProfileRepository;

    public CompanyProfileService(CompanyProfileRepository companyProfileRepository) {
        this.companyProfileRepository = companyProfileRepository;
    }

    public CompanyProfileDto getByUser(User user) {
        return companyProfileRepository.findByUserId(user.getId())
            .map(this::toDto)
            .orElse(null);
    }

    public CompanyProfileDto createOrUpdateProfile(CompanyProfileDto dto, User user) {
        CompanyProfile profile = companyProfileRepository.findByUserId(user.getId())
            .orElseGet(() -> {
                CompanyProfile newProfile = new CompanyProfile();
                newProfile.setUser(user);
                user.setCompanyProfile(newProfile); // keep bidirectional link
                return newProfile;
            });

        profile.setCompanyName(dto.getCompanyName());
        profile.setIndustry(dto.getIndustry());
        profile.setWebsite(dto.getWebsite());
        profile.setDescription(dto.getDescription());
        profile.setLogoUrl(dto.getLogoUrl());

        // This will persist profile and maintain relationship
        profile = companyProfileRepository.save(profile);

        return toDto(profile);
    }

    private CompanyProfileDto toDto(CompanyProfile profile) {
        CompanyProfileDto dto = new CompanyProfileDto();
        dto.setId(profile.getId());
        dto.setCompanyName(profile.getCompanyName());
        dto.setIndustry(profile.getIndustry());
        dto.setWebsite(profile.getWebsite());
        dto.setDescription(profile.getDescription());
        dto.setLogoUrl(profile.getLogoUrl());
        dto.setUserId(profile.getUser().getId());
        return dto;
    }
}
