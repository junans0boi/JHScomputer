package com.example.jhscomputer.asrequest.controller;


import com.example.jhscomputer.asrequest.dto.ASRequestDTO;
import com.example.jhscomputer.asrequest.entity.ASRequest;
import com.example.jhscomputer.users.entity.User;
import com.example.jhscomputer.asrequest.service.ASRequestService;
import com.example.jhscomputer.users.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;


@Controller
@RequestMapping("/api/asrequest")
public class ASRequestController {
    @Autowired
    private ASRequestService asRequestService;

    @Autowired
    private UserManagementService userService;

    /**
     * AS 요청 제출 처리
     */
    @PostMapping("/submit")
    public String submitASRequest(
            @ModelAttribute ASRequestDTO asRequestDTO,
            RedirectAttributes redirectAttributes) {
        // 현재 인증된 사용자 가져오기
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login?error=not_logged_in";
        }

        ASRequest asRequest = new ASRequest();
        asRequest.setUser(currentUser);
        asRequest.setContactPhone(currentUser.getPhoneNum());
        asRequest.setDeviceType(asRequestDTO.getDeviceType());
        asRequest.setIssueDescription(asRequestDTO.getIssueDescription());

        // 추가 필드 설정
        if (asRequestDTO.getDeviceType() == ASRequest.DeviceType.DESKTOP) {
            LocalDate purchaseDate = asRequestDTO.getPurchaseDate();
            if (purchaseDate != null) {
                asRequest.setPurchaseDate(purchaseDate);
            }
            asRequest.setPurchaseSource(asRequestDTO.getPurchaseSource());
        } else if (asRequestDTO.getDeviceType() == ASRequest.DeviceType.LAPTOP) {
            LocalDate manufactureDate = asRequestDTO.getManufactureDate();
            if (manufactureDate != null) {
                asRequest.setManufactureDate(manufactureDate);
            }
            asRequest.setModelName(asRequestDTO.getModelName());
        }

        // 파일 업로드 처리
        MultipartFile quotationAttachment = asRequestDTO.getQuotationAttachment();
        if (quotationAttachment != null && !quotationAttachment.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + quotationAttachment.getOriginalFilename();
            try {
                String uploadDir = "src/main/resources/static/uploads/";
                java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + fileName);
                java.nio.file.Files.createDirectories(path.getParent());
                quotationAttachment.transferTo(path);
                asRequest.setQuotationAttachment("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("registrationError", "파일 업로드 중 오류가 발생했습니다.");
                return "redirect:/asrequest";
            }
        }

        // AS 요청 저장
        asRequestService.saveRequest(asRequest);
        redirectAttributes.addFlashAttribute("registrationSuccess", true);
        return "redirect:/asrequest";
    }

    /**
     * 현재 인증된 사용자 가져오기
     */
    private User getCurrentUser() {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        String userEmail = authentication.getName();
        return userService.findByEmail(userEmail);
    }
}