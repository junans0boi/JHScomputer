package com.example.jhscomputer.announcement.repository;

import com.example.jhscomputer.announcement.entity.AnnouncementImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementImageRepository extends JpaRepository<AnnouncementImage, Long> {
}