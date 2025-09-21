package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.UnregisterdDonor;
import com.donation.LifeLine.model.User;
import com.donation.LifeLine.services.UnregisterdDonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.donation.LifeLine.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/medical-adviser")
@PreAuthorize("hasRole('MEDICAL_ADVISER')")
public class MedicalAdviserController {

    @Autowired
    private UnregisterdDonorService unregisterdDonorService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String medicalAdviserDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<UnregisterdDonor> donors = unregisterdDonorService.getAllDonors();
        model.addAttribute("donors", donors);

        // Get username of logged-in advisor
        String username = userDetails.getUsername();

        // Fetch full User entity from DB
        User advisor = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Advisor not found"));


        model.addAttribute("advisor", advisor);               // Full advisor object
        model.addAttribute("advisorName", advisor.getUsername()); // For nav dropdown
        model.addAttribute("advisorId", advisor.getId());     // For dashboard ID
        model.addAttribute("lastLogin", "2025-09-20 07:00 AM"); // You can fetch real last login if stored
        model.addAttribute("pendingApprovals", donors.size());

        return "MedicalAdviser/medical-adviser-dashboard";
    }
}
