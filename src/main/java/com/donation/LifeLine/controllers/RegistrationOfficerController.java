package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.Role;
import com.donation.LifeLine.model.UnregisterdDonor;
import com.donation.LifeLine.model.User;
import com.donation.LifeLine.repository.RoleRepository;
import com.donation.LifeLine.repository.UnregisterdDonorRepository;
import com.donation.LifeLine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/registration-officer")
@PreAuthorize("hasRole('REGISTRATION_OFFICER')")
public class RegistrationOfficerController {

    @Autowired
    private UnregisterdDonorRepository donorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;



    @GetMapping("/dashboard")
    public String registrationOfficerDashboard(Model model) {
        // Get all approved donors
        List<UnregisterdDonor> approvedDonors = donorRepository
                .findByIsApprovedTrueAndIsRejectedFalseAndIsRegisteredFalse();
        List<UnregisterdDonor> registeredDonors = donorRepository
                .findByIsRegisteredTrue();

        model.addAttribute("approvedDonors", approvedDonors);
        model.addAttribute("registeredDonors", registeredDonors);
        return "DonorRegistrationOfficer/registration-officer-dashboard";
    }

    // Register approved donor as system user
    @PostMapping("/register-donor")
    public String registerDonor(@RequestParam("donorId") Long donorId,
                                RedirectAttributes redirectAttrs) {

        UnregisterdDonor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        // Check if already registered
        if (userRepository.existsByUsername(donor.getNic())) {
            redirectAttrs.addFlashAttribute("error", "Donor already registered!");
            return "redirect:/registration-officer/dashboard";
        }

        User user = new User();
        user.setUsername(donor.getNic());
        user.setPassword(donor.getPassword()); // reuse existing password
        Role donorRole = roleRepository.findByName(Role.ERole.ROLE_DONOR)
                .orElseThrow(() -> new RuntimeException("ROLE_DONOR not found"));
        user.setRoles(Set.of(donorRole));
        userRepository.save(user);
        donor.setIsRegistered(true);
        donorRepository.save(donor);


        redirectAttrs.addFlashAttribute("success", "Donor registered successfully!");
        return "redirect:/registration-officer/dashboard";
    }

}