package com.donation.LifeLine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    // Enum for roles
    public enum ERole {
        ROLE_DONOR,
        ROLE_REGISTRATION_OFFICER,
        ROLE_MEDICAL_ADVISER,
        ROLE_ADMIN,
        ROLE_COORDINATOR

    }


}
