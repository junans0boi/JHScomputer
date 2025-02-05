package com.example.jhscomputer.announcement.repository;

import com.example.jhscomputer.announcement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    // 필요 시 커스텀 메서드
}