package com.example.jhscomputer.announcement.entity;

import com.example.jhscomputer.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "announcements")
@Getter @Setter
@NoArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId; 

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, length = 5000)
    private String content;

    // 단일 이미지라면 아래처럼
    @Column(name = "attachment_image", length = 500)
    private String attachmentImage; // 이미지 경로/URL

    // 작성자
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id") 
    private User author; // FK -> users 테이블

    // enum: active/inactive
    @Column(nullable = false)
    private String status = "active";

    // 여러 장 이미지를 별도 테이블로
    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL)
    private List<AnnouncementImage> images = new ArrayList<>();
}