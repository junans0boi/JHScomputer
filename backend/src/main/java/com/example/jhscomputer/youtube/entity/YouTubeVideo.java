package com.example.jhscomputer.youtube.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "youtube_videos")
@Getter
@Setter
@NoArgsConstructor
public class YouTubeVideo {
    @Id
    private String videoId;  // YouTube 고유 ID

    private String title;
    private String thumbnailUrl;
    private LocalDateTime publishedAt;

    // 마지막 업데이트 시각 (데이터가 언제 갱신되었는지)
    private LocalDateTime lastUpdated;
}