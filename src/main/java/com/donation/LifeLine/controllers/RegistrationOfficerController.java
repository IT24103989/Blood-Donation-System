package com.donation.LifeLine.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
@Controller
@RequestMapping("/registration-officer")
@PreAuthorize("hasRole('REGISTRATION_OFFICER')")
public class RegistrationOfficerController {
    // Registration officer-specific endpoints
}