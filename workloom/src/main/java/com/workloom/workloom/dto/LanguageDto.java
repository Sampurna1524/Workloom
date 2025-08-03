package com.workloom.workloom.dto;

/**
 * Data Transfer Object for Language section in User Profile.
 */
public class LanguageDto {

    private Long id;
    private String name;
    private String proficiency;

    // No-argument constructor
    public LanguageDto() {
    }

    // All-argument constructor (optional, for convenience)
    public LanguageDto(Long id, String name, String proficiency) {
        this.id = id;
        this.name = name;
        this.proficiency = proficiency;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    @Override
    public String toString() {
        return "LanguageDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", proficiency='" + proficiency + '\'' +
                '}';
    }
}
