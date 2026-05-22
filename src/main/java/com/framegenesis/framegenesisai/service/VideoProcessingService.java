package com.framegenesis.framegenesisai.service;

import com.framegenesis.framegenesisai.entity.VideoJob;
import com.framegenesis.framegenesisai.repository.VideoJobRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.framegenesis.framegenesisai.service.ScriptGenerationService;
import java.util.List;
import com.framegenesis.framegenesisai.service.SceneGenerationService;
import com.framegenesis.framegenesisai.service.VoiceGenerationService;
import com.framegenesis.framegenesisai.service.VisualGenerationService;
import com.framegenesis.framegenesisai.service.VideoRenderingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VideoProcessingService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(VideoProcessingService.class);

    private final VideoJobRepository videoJobRepository;
    private final SceneGenerationService sceneGenerationService;
    private final VoiceGenerationService voiceGenerationService;
    private final VisualGenerationService visualGenerationService;
    private final VideoRenderingService videoRenderingService;

    private final ScriptGenerationService scriptGenerationService;

    public VideoProcessingService(
            VideoJobRepository videoJobRepository,
            SceneGenerationService sceneGenerationService,
            VoiceGenerationService voiceGenerationService,
            VisualGenerationService visualGenerationService,
            VideoRenderingService videoRenderingService,
            ScriptGenerationService scriptGenerationService
    ) {
        this.videoJobRepository = videoJobRepository;
        this.sceneGenerationService = sceneGenerationService;
        this.voiceGenerationService = voiceGenerationService;
        this.visualGenerationService = visualGenerationService;
        this.videoRenderingService = videoRenderingService;
        this.scriptGenerationService = scriptGenerationService;
    }

    @Async
    public void processVideo(VideoJob job) {

        try {

            job.setStatus("PROCESSING");
            videoJobRepository.save(job);
            String script =
                    scriptGenerationService.generateScript(job.getPrompt());
            job.setGeneratedScript(script);

            videoJobRepository.save(job);
            LOGGER.info("Generated script for video job {}", job.getId());

            List<String> scenes =
                    sceneGenerationService.generateScenes(script);

            String voiceUrl =
                    voiceGenerationService.generateVoiceover(script);

            LOGGER.info("Generated voiceover for video job {}", job.getId());

            List<String> visuals =
                    visualGenerationService.generateVisuals(scenes);
            job.setGeneratedVisuals(visuals);

            videoJobRepository.save(job);

            String finalVideoUrl =
                    videoRenderingService.renderVideo(
                            visuals,
                            voiceUrl
                    );

            LOGGER.info("Rendered final video for job {}", job.getId());

            job.setStatus("COMPLETED");
            job.setVideoUrl(finalVideoUrl);

            videoJobRepository.save(job);

        } catch (Exception e) {
            LOGGER.error("Video job {} failed", job.getId(), e);
            job.setStatus("FAILED");
            videoJobRepository.save(job);
        }
    }
}
