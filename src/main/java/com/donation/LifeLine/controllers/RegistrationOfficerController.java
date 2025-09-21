package com.donation.LifeLine.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration-officer")
@PreAuthorize("hasRole('REGISTRATION_OFFICER')")
public class RegistrationOfficerController {

    @GetMapping("/dashboard")
    public String registrationOfficerDashboard() {
        return "registration-officer-dashboard";
    }
}