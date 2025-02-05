package com.example.jhscomputer.admin.controller;

import com.example.jhscomputer.announcement.entity.Announcement;
import com.example.jhscomputer.admin.service.AdminAnnouncementService;
import com.example.jhscomputer.users.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/announcements")
public class AnnouncementManagementController {

    private final AdminAnnouncementService announcementService;
    private final UserManagementService userService;

    @Autowired
    public AnnouncementManagementController(AdminAnnouncementService announcementService,
                                              UserManagementService userService) {
        this.announcementService = announcementService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newAnnouncementForm(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "admin/announcement/announcement-form";  // 수정: 파일명 "announcement-form.html"
    }

    @GetMapping("/edit/{postId}")
    public String editAnnouncement(@PathVariable Long postId, Model model) {
        Announcement announcement = announcementService.findById(postId);
        model.addAttribute("announcement", announcement);
        return "admin/announcement/announcement-form";  // 수정: 파일명 "announcement-form.html"
    }

    @PostMapping("/save")
    public String saveAnnouncement(@ModelAttribute Announcement announcement,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        if (announcement.getPostId() == null) {
            String userEmail = authentication.getName();
            announcement.setAuthor(userService.findByEmail(userEmail));
            announcement.setCreatedAt(LocalDateTime.now());
        } else {
            Announcement existing = announcementService.findById(announcement.getPostId());
            existing.setTitle(announcement.getTitle());
            existing.setContent(announcement.getContent());
            existing.setAttachmentImage(announcement.getAttachmentImage());
            existing.setStatus(announcement.getStatus());
            announcement = existing;
        }
        announcementService.save(announcement);
        redirectAttributes.addFlashAttribute("successMessage", "공지사항이 저장되었습니다.");
        return "redirect:/admin/announcements/list";
    }

    @PostMapping("/delete")
    public String deleteAnnouncement(@RequestParam Long postId, RedirectAttributes redirectAttributes) {
        announcementService.delete(postId);
        redirectAttributes.addFlashAttribute("successMessage", "공지사항이 삭제되었습니다.");
        return "redirect:/admin/announcements/list";
    }
}