package com.donation.LifeLine.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Donor {
    private String fullName;
    private String nic;
    private String address;
    private String bloodGroup;
    private String password;
    private int weight;
    private String travelHistory;
    private String dateOfBirth;



    private Boolean hasRecentFeverOrFlu;
    private Boolean isTakingMedications;
    private Boolean hadRecentSurgeryOrTattoo;
    private Boolean hasChronicConditions;


    private String medicationDetails;
    private String chronicConditionDetails;
}
