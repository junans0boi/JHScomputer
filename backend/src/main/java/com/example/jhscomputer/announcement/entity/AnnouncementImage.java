package com.example.jhscomputer.announcement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "announcement_images")
@Getter @Setter
@NoArgsConstructor
public class AnnouncementImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    // FK -> announcements.post_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Announcement announcement;

    @Column(name = "image_url", length = 500, nullable = false)
    private String imageUrl;
}