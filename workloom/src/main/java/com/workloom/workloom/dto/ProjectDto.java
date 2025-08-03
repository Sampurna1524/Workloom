package com.workloom.workloom.dto;

/**
 * Data Transfer Object for Project section in User Profile.
 */
public class ProjectDto {

    private Long id;
    private String title;
    private String description;
    private String url;
    private String startDate;
    private String endDate;

    // No-arg constructor
    public ProjectDto() {
    }

    // All-args constructor
    public ProjectDto(Long id, String title, String description, String url, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
