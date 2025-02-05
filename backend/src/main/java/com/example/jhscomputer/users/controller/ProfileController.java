package com.example.jhscomputer.users.controller;

import com.example.jhscomputer.assemblyorder.dto.AssemblyOrderDTO;
import com.example.jhscomputer.assemblyorder.entity.AssemblyOrder;
import com.example.jhscomputer.assemblyorder.entity.Purchase;
import com.example.jhscomputer.assemblyorder.entity.Quotation;
import com.example.jhscomputer.users.entity.User;
import com.example.jhscomputer.assemblyorder.repository.QuotationRepository;
import com.example.jhscomputer.assemblyorder.service.AssemblyOrderService;
import com.example.jhscomputer.assemblyorder.service.PurchaseService;
import com.example.jhscomputer.users.service.UserManagementService;
import com.example.jhscomputer.users.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자 프로필 관련 요청을 처리하는 컨트롤러입니다.
 * 예: 회원정보 수정, 주문/견적/구매 조회
 */
@Controller
@RequestMapping("/user/mypage")
public class ProfileController {

    @Autowired
    private UserManagementService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // [추가] 필요한 Service/Repository
    @Autowired
    private AssemblyOrderService assemblyOrderService;

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserRepository userRepository;

    // -------------------------------------------------------------------------
    // 1) 프로필 수정 페이지
    // -------------------------------------------------------------------------
    @GetMapping("/users/info")
    public String showProfileEditPage(Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login?error=user_not_found";
        }
        model.addAttribute("user", currentUser);
        return "profile/mypage-profile-info";
    }

    // -------------------------------------------------------------------------
    // 2) 비밀번호 확인 (AJAX)
    // -------------------------------------------------------------------------
    @PostMapping("/api/user/verify-password")
    @ResponseBody
    public Map<String, Boolean> verifyPassword(@RequestBody Map<String, String> payload) {
        String currentPassword = payload.get("currentPassword");
        User currentUser = getCurrentUser();

        boolean isMatch = false;
        if (currentUser != null) {
            isMatch = passwordEncoder.matches(currentPassword, currentUser.getPassword());
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("verified", isMatch);
        return response;
    }

    // -------------------------------------------------------------------------
    // 3) 프로필 업데이트
    // -------------------------------------------------------------------------
    @PostMapping("/api/users/update-profile")
    public String updateProfile(
            @ModelAttribute User updatedUser,
            @RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
            @RequestParam("addressBase") String addressBase,
            @RequestParam(value = "addressExtra", required = false) String addressExtra,
            @RequestParam(value = "addressDetail", required = false) String addressDetail,
            RedirectAttributes redirectAttributes) {

        User currentUser = getCurrentUser();
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("registrationError", "사용자를 찾을 수 없습니다.");
            return "redirect:/login?error=user_not_found";
        }

        // 비밀번호 변경 로직
        String newPassword = updatedUser.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!newPassword.equals(passwordConfirm)) {
                redirectAttributes.addFlashAttribute("registrationError", "비밀번호가 일치하지 않습니다.");
                return "redirect:/profile";
            }
            currentUser.setPassword(passwordEncoder.encode(newPassword));
        }

        // 이름, 전화번호
        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setPhoneNum(updatedUser.getPhoneNum());

        // 주소
        String combinedAddress = addressBase;
        if (addressExtra != null && !addressExtra.trim().isEmpty()) {
            combinedAddress += " " + addressExtra.trim();
        }
        if (addressDetail != null && !addressDetail.trim().isEmpty()) {
            combinedAddress += " " + addressDetail.trim();
        }
        currentUser.setAddress(combinedAddress);

        // 저장
        userService.save(currentUser);

        redirectAttributes.addFlashAttribute("registrationSuccess", true);
        return "redirect:/profile/mypage-layout";
    }

    // -------------------------------------------------------------------------
    // 4) 내 주문 목록 보기
    // -------------------------------------------------------------------------
    @GetMapping("/users/myorders")
    public String myOrders(Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login?error=not_logged_in";
        }
        // 내 주문 목록
        List<AssemblyOrder> orders = assemblyOrderService.getOrdersByUser(currentUser.getUserId());

        // 각 주문에 대한 견적과 구매 정보 조회
        List<AssemblyOrderDTO> orderDTOs = new ArrayList<>();
        for (AssemblyOrder order : orders) {
            Quotation quotation = quotationRepository.findByOrderOrderId(order.getOrderId());
            Purchase purchase = null;
            if (quotation != null) {
                purchase = purchaseService.getPurchaseByQuotationId(quotation.getQuotationId());
            }
            orderDTOs.add(new AssemblyOrderDTO(order, quotation, purchase));
        }

        model.addAttribute("orderDTOs", orderDTOs);
        return "profile/myorders-list"; 
    }

    // -------------------------------------------------------------------------
    // 5) 내 주문의 견적 보기
    // -------------------------------------------------------------------------
    @GetMapping("users/orders/{orderId}/quotation")
    public String viewMyQuotation(@PathVariable Long orderId, Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login?error=not_logged_in";
        }

        AssemblyOrder order = assemblyOrderService.getOrderById(orderId);
        if (!order.getUser().getUserId().equals(currentUser.getUserId())) {
            return "redirect:/api/profile/orders?error=not_your_order";
        }
        Quotation quote = quotationRepository.findByOrderOrderId(orderId);
        model.addAttribute("quotation", quote);
        return "userQuotationDetail";
    }

    // -------------------------------------------------------------------------
    // 6) 내 구매 목록
    // -------------------------------------------------------------------------
    @GetMapping("/users/purchases")
    public String myPurchases(Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login?error=not_logged_in";
        }

        List<Purchase> myList = purchaseService.getPurchasesByUser(currentUser.getUserId());
        model.addAttribute("purchases", myList);
        return "profile/myPurchases-list";
    }

    // -------------------------------------------------------------------------
    // 7) 구매 상세
    // -------------------------------------------------------------------------
    @GetMapping("/users/purchases/{pid}/detail")
    public String purchaseDetail(@PathVariable Long pid, Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login?error=not_logged_in";
        }

        Purchase purchase = purchaseService.getPurchase(pid);
        if (!purchase.getUser().getUserId().equals(currentUser.getUserId())) {
            return "redirect:/api/profile/purchases?error=not_your_purchase";
        }
        model.addAttribute("purchase", purchase);
        return "profile/myPurchase-detail";
    }

    // -------------------------------------------------------------------------
    // [공용] 현재 로그인 사용자 리턴
    // -------------------------------------------------------------------------
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        return userRepository.findByEmail(authentication.getName());
    }
}