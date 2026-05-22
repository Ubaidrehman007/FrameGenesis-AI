package com.framegenesis.framegenesisai.repository;

import com.framegenesis.framegenesisai.entity.VideoJob;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VideoJobRepository
        extends JpaRepository<VideoJob, Long> {



    List<VideoJob> findByCreatedBy(String createdBy);
    Optional<VideoJob> findByIdAndCreatedBy(
            Long id,
            String email
    );



}



