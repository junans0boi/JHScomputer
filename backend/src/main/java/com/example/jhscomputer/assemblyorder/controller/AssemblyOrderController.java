package com.example.jhscomputer.assemblyorder.controller;

import com.example.jhscomputer.admin.repository.AdminAssemblyOrderRepository;
import com.example.jhscomputer.users.entity.User;
import com.example.jhscomputer.users.repository.UserRepository;
import com.example.jhscomputer.assemblyorder.entity.AssemblyOrder;
import com.example.jhscomputer.assemblyorder.entity.Quotation;
import com.example.jhscomputer.assemblyorder.entity.Purchase;



import com.example.jhscomputer.assemblyorder.repository.PurchaseRepository;
import com.example.jhscomputer.assemblyorder.repository.QuotationRepository;
import com.example.jhscomputer.assemblyorder.service.AssemblyOrderService;
import com.example.jhscomputer.assemblyorder.service.PurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 조립주문(AssemblyOrder) 관련 요청을 처리하는 컨트롤러입니다.
 * 예: 주문 등록/저장, 구매하기
 */
@Controller
public class AssemblyOrderController {

    private static final Logger logger = LoggerFactory.getLogger(AssemblyOrderController.class);

    @Autowired
    private AdminAssemblyOrderRepository assemblyOrderRepository;

    @Autowired
    private UserRepository userRepository;

    // [추가] 필요한 Service/Repository
    @Autowired
    private AssemblyOrderService assemblyOrderService;

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseService purchaseService;

    // -------------------------------------------------------------------------
    // [회원] 주문 등록
    // -------------------------------------------------------------------------
    @PostMapping("/api/orders/submit")
    public String submitOrder(
            @ModelAttribute AssemblyOrder formOrder,
            @RequestParam(required = false) String customConceptColor,
            @RequestParam(required = false) String customCoolerType) {

        logger.debug("Received conceptColors: {}", formOrder.getConceptColors());
        logger.debug("Received coolerType: {}", formOrder.getCoolerType());
        logger.debug("Received customConceptColor: {}", customConceptColor);
        logger.debug("Received customCoolerType: {}", customCoolerType);

        // 현재 로그인 사용자 가져오기
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            logger.error("사용자 정보가 없습니다: 로그인되지 않음");
            return "redirect:/login?error=not_logged_in";
        }

        // 주문 정보와 사용자 매핑
        formOrder.setUser(currentUser);

        // 커스텀 값 처리
        if ("custom".equals(formOrder.getConceptColors()) && customConceptColor != null
                && !customConceptColor.trim().isEmpty()) {
            formOrder.setConceptColors(customConceptColor.trim());
        }
        if ("custom".equals(formOrder.getCoolerType()) && customCoolerType != null
                && !customCoolerType.trim().isEmpty()) {
            formOrder.setCoolerType(customCoolerType.trim());
        }

        // DB 저장
        try {
            assemblyOrderRepository.save(formOrder);
        } catch (Exception e) {
            logger.error("주문 저장 중 오류 발생: {}", e.getMessage());
            return "redirect:/error";
        }

        // 성공 후 /order 페이지로 이동
        return "redirect:/assemblyorder";
    }

    // -------------------------------------------------------------------------
    // [회원] 견적 구매하기
    // -------------------------------------------------------------------------
    @PostMapping("/api/orders/{orderId}/buy")
    public String buyQuotation(@PathVariable Long orderId, RedirectAttributes ra) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login?error=not_logged_in";
        }

        // 주문 & 견적 확인
        AssemblyOrder order = assemblyOrderService.getOrderById(orderId);
        if (!order.getUser().getUserId().equals(currentUser.getUserId())) {
            ra.addFlashAttribute("error", "잘못된 접근입니다.");
            return "redirect:/api/profile/orders";
        }
        Quotation quote = quotationRepository.findByOrderOrderId(orderId);
        if (quote == null) {
            ra.addFlashAttribute("error", "견적이 존재하지 않습니다.");
            return "redirect:/api/profile/orders";
        }

        // 이미 구매했는지 확인
        Purchase existingPurchase = purchaseRepository.findByQuotationQuotationId(quote.getQuotationId());
        if (existingPurchase != null) {
            ra.addFlashAttribute("error", "이미 구매하셨습니다.");
            return "redirect:/api/profile/orders";
        }

        // 결제금액(총합) 확인
        int totalPrice = (quote.getTotalPrice() != null) ? quote.getTotalPrice() : 0;

        // Purchase 생성
        try {
            purchaseService.createPurchase(currentUser, quote, totalPrice);
            ra.addFlashAttribute("success", "구매가 완료되었습니다!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "구매 중 오류: " + e.getMessage());
        }
        return "redirect:/api/profile/orders";
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
        String userEmail = authentication.getName();
        return userRepository.findByEmail(userEmail);
    }
}