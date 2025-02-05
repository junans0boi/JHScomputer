package com.example.jhscomputer.admin.controller;

import java.time.LocalDate;
import java.util.Optional;

import com.example.jhscomputer.asrequest.entity.ASRequest;
import com.example.jhscomputer.asrequest.service.ASRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/asrequest")
public class ASRequestManagementController {

    @Autowired
    private ASRequestService asRequestService;

    @GetMapping("/{requestId}/edit")
    public String editASRequestStatus(@PathVariable Long requestId, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<ASRequest> optionalRequest = asRequestService.getRequestById(requestId);
        if (!optionalRequest.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "AS 요청을 찾을 수 없습니다.");
            return "redirect:/admin/asrequest/list";
        }
        ASRequest request = optionalRequest.get();
        model.addAttribute("request", request);
        model.addAttribute("statuses", com.example.jhscomputer.asrequest.enumpackage.ASRequestStatus.values());
        return "admin/asrequest/asrequest-form"; // 수정: 뷰 이름 변경
    }

    @PostMapping("/{requestId}/update")
    public String updateASRequestStatus(
            @PathVariable Long requestId,
            @RequestParam("status") com.example.jhscomputer.asrequest.enumpackage.ASRequestStatus status,
            @RequestParam(value = "issueDescription", required = false) String issueDescription,
            @RequestParam(value = "manufactureDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate manufactureDate,
            @RequestParam(value = "modelName", required = false) String modelName,
            @RequestParam(value = "purchaseDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate purchaseDate,
            @RequestParam(value = "purchaseSource", required = false) String purchaseSource,
            RedirectAttributes redirectAttributes) {
        try {
            asRequestService.updateASRequest(requestId, status, issueDescription, manufactureDate, modelName,
                    purchaseDate, purchaseSource);
            redirectAttributes.addFlashAttribute("success", "AS 요청 상태가 업데이트되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/asrequest/list";  // 수정: 리다이렉트 URL 변경
    }

    @PostMapping("/{requestId}/delete")
    public String deleteASRequest(
            @PathVariable Long requestId,
            RedirectAttributes redirectAttributes) {
        try {
            asRequestService.deleteRequest(requestId);
            redirectAttributes.addFlashAttribute("success", "AS 요청이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/asrequest/list";  // 수정: 리다이렉트 URL 변경
    }
}