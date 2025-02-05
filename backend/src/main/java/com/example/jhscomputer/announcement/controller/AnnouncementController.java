package com.example.jhscomputer.announcement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.jhscomputer.announcement.entity.Announcement;
import com.example.jhscomputer.announcement.service.AnnouncementService;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    // 사용자용 목록
    @GetMapping("/list")
    public String listForUser(Model model) {
        List<Announcement> announcements = announcementService.findAll();
        model.addAttribute("announcements", announcements);
        return "user/announcement/announcement-list";  // 수정: 사용자용 경로로 변경
    }

    // 사용자용 상세
    @GetMapping("/{postId}")
    public String detailForUser(@PathVariable Long postId, Model model) {
        Announcement ann = announcementService.findById(postId);
        model.addAttribute("announcement", ann);
        return "user/announcement/announcement-detail";  // 수정: 사용자용 경로로 변경
    }
}