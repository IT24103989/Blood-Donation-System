package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.Appointment;
import com.donation.LifeLine.model.UnregisterdDonor;
import com.donation.LifeLine.repository.AppointmentRepository;
import com.donation.LifeLine.repository.UnregisterdDonorRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final UnregisterdDonorRepository donorRepository;

    public AppointmentController(AppointmentRepository appointmentRepository,
                                 UnregisterdDonorRepository donorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.donorRepository = donorRepository;
    }


    @GetMapping("/donor/{id}/dashboard")
    public String donorDashboard(@PathVariable Long id, Model model) {
        UnregisterdDonor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        List<Appointment> appointments = appointmentRepository.findByDonorId(id);

        model.addAttribute("donor", donor);
        model.addAttribute("appointments", appointments);

        return "Donor/donor-dashboard"; // your Thymeleaf template
    }

}
