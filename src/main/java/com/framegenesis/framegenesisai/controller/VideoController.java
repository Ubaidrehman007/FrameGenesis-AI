package com.framegenesis.framegenesisai.controller;

import com.framegenesis.framegenesisai.dto.VideoRequest;
import com.framegenesis.framegenesisai.entity.VideoJob;
import com.framegenesis.framegenesisai.service.VideoJobService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.framegenesis.framegenesisai.service.VideoProcessingService;
import java.util.List;
import com.framegenesis.framegenesisai.repository.VideoJobRepository;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    private final VideoJobService videoJobService;
    private final VideoProcessingService videoProcessingService;
    private final VideoJobRepository videoJobRepository;

    public VideoController(
            VideoJobService videoJobService,
            VideoProcessingService videoProcessingService,
            VideoJobRepository videoJobRepository
    ) {
        this.videoJobService = videoJobService;
        this.videoProcessingService = videoProcessingService;
        this.videoJobRepository = videoJobRepository;
    }

    @PostMapping("/generate")
    public VideoJob generateVideo(
            @Valid @RequestBody VideoRequest request,
            Authentication authentication
    ) {

        VideoJob job = videoJobService.createJob(
                request.prompt(),
                authentication.getName()
        );

        videoProcessingService.processVideo(job);

        return job;
    }

    @GetMapping("/my-jobs")
    public List<VideoJob> getMyJobs(
            Authentication authentication
    ) {

        return videoJobService.getUserJobs(
                authentication.getName()
        );
    }

    @GetMapping("/{id}")
    public VideoJob getJobStatus(
            @PathVariable Long id,
            Authentication authentication
    ) {

        return videoJobService.getJobById(
                id,
                authentication.getName()
        );
    }

    @GetMapping("/my-videos")
    public List<VideoJob> getMyVideos(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return videoJobRepository
                .findByCreatedBy(email);
    }

}
