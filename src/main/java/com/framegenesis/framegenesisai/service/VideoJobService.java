package com.framegenesis.framegenesisai.service;

import com.framegenesis.framegenesisai.entity.VideoJob;
import com.framegenesis.framegenesisai.repository.VideoJobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class VideoJobService {

    private final VideoJobRepository videoJobRepository;

    public VideoJobService(VideoJobRepository videoJobRepository) {
        this.videoJobRepository = videoJobRepository;
    }

    public VideoJob createJob(String prompt, String email) {

        VideoJob job = new VideoJob();
        job.setPrompt(prompt);
        job.setStatus("PENDING");
        job.setCreatedBy(email);

        return videoJobRepository.save(job);
    }

    public List<VideoJob> getUserJobs(String email) {

        return videoJobRepository.findByCreatedBy(email);
    }

    public VideoJob getJobById(
            Long id,
            String email
    ) {

        return videoJobRepository
                .findByIdAndCreatedBy(id, email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found")
                );
    }

}
