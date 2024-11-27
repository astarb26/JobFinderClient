package com.example.jobfinderclient.model;

public class Job implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    private Long jobId;
    private String city;
    private boolean filled; // Indicates whether the job is already filled

    // Constructor with all fields
    public Job(Long jobId, String title, String description, String city) {
        this.title = title;
        this.jobId = jobId;
        this.description = description;
        this.city = city;
    }

    // Getters and Setters for all fields
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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}