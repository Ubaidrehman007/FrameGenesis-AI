package com.framegenesis.framegenesisai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "video_jobs")
public class VideoJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prompt;

    private String status;

    private String videoUrl;

    @Column(columnDefinition = "TEXT")
    private String generatedScript;

    private String createdBy;

    @ElementCollection
    private List<String> generatedVisuals;

    public VideoJob() {
    }

    public Long getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getGeneratedScript() {
        return generatedScript;
    }

    public void setGeneratedScript(String generatedScript) {
        this.generatedScript = generatedScript;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<String> getGeneratedVisuals() {
        return generatedVisuals;
    }

    public void setGeneratedVisuals(List<String> generatedVisuals) {
        this.generatedVisuals = generatedVisuals;
    }
}
