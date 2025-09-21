package com.donation.LifeLine.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medical-adviser")
@PreAuthorize("hasRole('MEDICAL_ADVISER')")
public class MedicalAdviserController {

    @GetMapping("/dashboard")
    public String medicalAdviserDashboard() {
        return "medical-adviser-dashboard";
    }
}