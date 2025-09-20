package com.donation.LifeLine.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
@Controller
@RequestMapping("/medical-adviser")
@PreAuthorize("hasRole('MEDICAL_ADVISER')")
public class MedicalAdviserController {
}
