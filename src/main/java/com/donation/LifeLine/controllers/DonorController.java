package com.donation.LifeLine.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donor")
@PreAuthorize("hasRole('DONOR')")
public class DonorController {

    @GetMapping("/dashboard")
    public String donorDashboard() {
        return "Donor/donor-dashboard";
    }
}
