    package com.example.jhscomputer.admin.service;

    import com.example.jhscomputer.announcement.entity.Announcement;
    import com.example.jhscomputer.announcement.repository.AnnouncementRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class AdminAnnouncementService {

        @Autowired
        private AnnouncementRepository announcementRepository;
        // ---------------------------------------
        // [회원] 전체 공지사항 조회
        // ---------------------------------------
        public List<Announcement> findAll() {
            return announcementRepository.findAll();
        }

        
        // ---------------------------------------
        // [회원] 특정 ID 조회
        // ---------------------------------------
        public Announcement findById(Long postId) {
            return announcementRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다. ID: " + postId));
        }

        // ---------------------------------------
        // [관리자] 저장
        // ---------------------------------------
        public Announcement save(Announcement announcement) {
            return announcementRepository.save(announcement);
        }

        // ---------------------------------------
        // [관리자]삭제
        // ---------------------------------------
        public void delete(Long postId) {
            announcementRepository.deleteById(postId);
        }
    }