
package com.example.jhscomputer.youtube.service;

import com.example.jhscomputer.youtube.dto.YouTubeResponse;
import com.example.jhscomputer.youtube.entity.YouTubeVideo;
import com.example.jhscomputer.youtube.repository.YouTubeVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YouTubeService {

    @Autowired
    private YouTubeVideoRepository videoRepository;

    private final String channelId = "UC4dsn1hGfd6GPQ8QUJwOxDQ";
    private final String apiKey = "AIzaSyDp62j_SzM6snwQurgDhprUDIjzQM6eQXk"; // API 키는 외부 설정으로 관리
    
    private final RestTemplate restTemplate = new RestTemplate();

    // 매일 자정에 업데이트 (필요에 따라 스케줄 조정)
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduledVideoUpdate() {
        updateVideosFromYouTube();
    }

    /**
     * 동영상 목록이 최신 상태인지 확인하고, 오래된 데이터면 업데이트 후 반환
     */
    public List<YouTubeVideo> getLatestVideos() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(24);
        List<YouTubeVideo> storedVideos = videoRepository.findAll();
        if (storedVideos.isEmpty() || storedVideos.stream().anyMatch(video -> video.getLastUpdated().isBefore(threshold))) {
            updateVideosFromYouTube();
        }
        // 최신 영상이 먼저 오도록 publishedAt 기준 내림차순 정렬 (필요에 따라 Repository에 정렬 메서드 추가 가능)
        return storedVideos.stream()
                .sorted((a, b) -> b.getPublishedAt().compareTo(a.getPublishedAt()))
                .collect(Collectors.toList());
    }

    /**
     * YouTube API를 호출하여 동영상 데이터를 가져오고 DB에 저장합니다.
     */
    public void updateVideosFromYouTube() {
        String url = "https://www.googleapis.com/youtube/v3/search?key=" + apiKey +
                     "&channelId=" + channelId +
                     "&part=snippet,id&order=date&maxResults=100";

        YouTubeResponse response = restTemplate.getForObject(url, YouTubeResponse.class);
        if (response != null && response.getItems() != null) {
            LocalDateTime now = LocalDateTime.now();
            List<YouTubeVideo> videos = response.getItems().stream()
                .filter(item -> "youtube#video".equals(item.getId().getKind()))
                .map(item -> {
                    YouTubeVideo video = new YouTubeVideo();
                    video.setVideoId(item.getId().getVideoId());
                    video.setTitle(item.getSnippet().getTitle());
                    video.setThumbnailUrl(item.getSnippet().getThumbnails().getHigh().getUrl());
                    video.setPublishedAt(parsePublishedAt(item.getSnippet().getPublishedAt()));
                    video.setLastUpdated(now);
                    return video;
                })
                .collect(Collectors.toList());

            // 단순 구현: 기존 데이터 삭제 후 전체 삽입
            videoRepository.deleteAll();
            videoRepository.saveAll(videos);
        }
    }

    private LocalDateTime parsePublishedAt(String publishedAtStr) {
        return LocalDateTime.parse(publishedAtStr); // ISO_DATE_TIME 형식이라고 가정
    }
}