package com.workloom.workloom.controller;

import com.workloom.workloom.dto.CompanyProfileDto;
import com.workloom.workloom.model.User;
import com.workloom.workloom.model.UserRole;
import com.workloom.workloom.service.CompanyProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company-profile")
@CrossOrigin(origins = "*")
public class CompanyProfileController {

    private final CompanyProfileService companyProfileService;

    public CompanyProfileController(CompanyProfileService companyProfileService) {
        this.companyProfileService = companyProfileService;
    }

    @GetMapping
    public ResponseEntity<?> getCompanyProfile(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)
            || user.getRole() != UserRole.COMPANY) {
            return ResponseEntity.status(403).body("Only companies can access this endpoint.");
        }

        CompanyProfileDto dto = companyProfileService.getByUser(user);
        if (dto == null) {
            return ResponseEntity.status(404).body("Company profile not found.");
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody CompanyProfileDto dto, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)
            || user.getRole() != UserRole.COMPANY) {
            return ResponseEntity.status(403).body("Only companies can update their profile.");
        }

        CompanyProfileDto saved = companyProfileService.createOrUpdateProfile(dto, user);
        return ResponseEntity.ok(saved);
    }
}
