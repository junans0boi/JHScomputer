package com.example.jhscomputer.youtube.repository;

import com.example.jhscomputer.youtube.entity.YouTubeVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YouTubeVideoRepository extends JpaRepository<YouTubeVideo, String> {
    List<YouTubeVideo> findAllByOrderByPublishedAtDesc();
}