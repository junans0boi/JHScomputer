package com.example.jhscomputer.admin.controller;

import com.example.jhscomputer.announcement.entity.Announcement;
import com.example.jhscomputer.asrequest.entity.ASRequest;
import com.example.jhscomputer.assemblyorder.entity.AssemblyOrder;
import com.example.jhscomputer.admin.service.AdminAnnouncementService;
import com.example.jhscomputer.admin.service.AdminAssemblyOrderManagementService;
import com.example.jhscomputer.asrequest.service.ASRequestService;
import com.example.jhscomputer.users.entity.User;
import com.example.jhscomputer.users.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    @Autowired
    private ASRequestService asRequestService;
    
    @Autowired
    private AdminAssemblyOrderManagementService adminAssemblyOrderManagementService;
    
    @Autowired
    private AdminAnnouncementService announcementService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "admin/dashboard";
    }
    
    @GetMapping("/asrequest/list")
    public String viewAllASRequests(Model model) {
        List<ASRequest> requests = asRequestService.getAllRequests();
        model.addAttribute("requests", requests);
        return "admin/asrequest/asrequest-list";  // 수정: 뷰 이름 변경
    }
    
    @GetMapping("/orders/list")
    public String showOrderList(Model model) {
        List<AssemblyOrder> orders = adminAssemblyOrderManagementService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/assemblyorder/assemblyorder-list";  // 수정: 뷰 이름 변경
    }
    
    @GetMapping("/users/list")
    public String showUserList(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        return "admin/user/user-list";  // 수정: 뷰 이름 변경 (소문자와 대시 사용)
    }
    
    @GetMapping("/announcements/list")
    public String listAnnouncements(Model model) {
        List<Announcement> announcements = announcementService.findAll();
        model.addAttribute("announcements", announcements);
        return "admin/announcement/announcement-list";  // 수정: 뷰 이름 변경
    }
}