package com.framegenesis.framegenesisai.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoRenderingService {

    public String renderVideo(
            List<String> visuals,
            String voiceUrl
    ) {
        return "https://framegenesis.ai/videos/final-rendered-video.mp4";
    }
}
