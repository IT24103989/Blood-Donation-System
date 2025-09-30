package com.donation.LifeLine.services;


import com.donation.LifeLine.repository.AppointmentRepository;
import org.springframework.stereotype.Service;



@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

}
