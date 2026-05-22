package com.framegenesis.framegenesisai.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisualGenerationService {

    public List<String> generateVisuals(
            List<String> scenes
    ) {

        List<String> visuals =
                new ArrayList<>();

        int count = 1;

        for (String scene : scenes) {

            visuals.add(

                    "https://picsum.photos/seed/scene"
                            + count +
                            "/1200/700"

            );

            count++;
        }

        return visuals;
    }

}
