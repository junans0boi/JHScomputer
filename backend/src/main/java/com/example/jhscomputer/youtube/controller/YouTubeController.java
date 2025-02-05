package com.example.jhscomputer.youtube.controller;

import com.example.jhscomputer.youtube.entity.YouTubeVideo;
import com.example.jhscomputer.youtube.service.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/youtube")
public class YouTubeController {

    @Autowired
    private YouTubeService youTubeService;

    // limit 파라미터를 받아 클라이언트가 원하는 개수만큼 반환 (기본값: 10)
    @GetMapping("/videos")
    public List<YouTubeVideo> getVideos(@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        List<YouTubeVideo> allVideos = youTubeService.getLatestVideos();
        // 요청한 수보다 영상이 많으면 잘라서 반환
        if (allVideos.size() > limit) {
            return allVideos.subList(0, limit);
        }
        return allVideos;
    }
}