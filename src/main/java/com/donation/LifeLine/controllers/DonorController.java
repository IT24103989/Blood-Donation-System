package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.User;
import com.donation.LifeLine.model.UnregisterdDonor;
import com.donation.LifeLine.repository.UserRepository;
import com.donation.LifeLine.services.DonorProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/donor")
@PreAuthorize("hasRole('DONOR')")
public class DonorController {

    private final UserRepository userRepository;
    private final DonorProfileService donorProfileService;

    public DonorController(UserRepository userRepository,
                           DonorProfileService donorProfileService) {
        this.userRepository = userRepository;
        this.donorProfileService = donorProfileService;
    }

    // ------------------ Dashboard ------------------
    @GetMapping("/dashboard")
    public String donorDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String nic = userDetails.getUsername();

        User donor = userRepository.findByUsername(nic)
                .orElseThrow(() -> new RuntimeException("Donor not found: " + nic));

        model.addAttribute("donor", donor);
        return "Donor/donor-dashboard"; // src/main/resources/templates/Donor/donor-dashboard.html
    }

    // ------------------ Profile ------------------
    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        UnregisterdDonor donor = donorProfileService.getDonorByUsername(principal.getName());
        if (donor == null) {
            throw new RuntimeException("Donor profile not found for username: " + principal.getName());
        }

        model.addAttribute("donor", donor);
        return "Donor/donor-profile"; // src/main/resources/templates/Donor/donor-profile.html
    }


    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("donor") UnregisterdDonor updatedDonor, Principal principal) {
        // Fetch existing donor from DB
        UnregisterdDonor existingDonor = donorProfileService.getDonorByUsername(principal.getName());
        if (existingDonor == null) {
            throw new RuntimeException("Donor profile not found for username: " + principal.getName());
        }

        // Update fields (only those editable in the profile)
        existingDonor.setFullName(updatedDonor.getFullName());
        existingDonor.setAddress(updatedDonor.getAddress());
        existingDonor.setBloodGroup(updatedDonor.getBloodGroup());
        existingDonor.setWeight(updatedDonor.getWeight());
        existingDonor.setDateOfBirth(updatedDonor.getDateOfBirth());
        existingDonor.setTravelHistory(updatedDonor.getTravelHistory());


        // Save existing donor
        donorProfileService.updateDonor(existingDonor);

        return "redirect:/donor/profile?success";
    }

    // ------------------ Change Password ------------------
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String newPassword, Principal principal) {
        donorProfileService.updatePassword(principal.getName(), newPassword);
        return "redirect:/donor/profile?passwordChanged";
    }
}
