package com.donation.LifeLine.services;

import com.donation.LifeLine.model.UnregisterdDonor;
import com.donation.LifeLine.repository.UnregisterdDonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnregisterdDonorServiceImpl implements UnregisterdDonorService {

    @Autowired
    private UnregisterdDonorRepository unregisterdDonorRepository;

    @Override
    public UnregisterdDonor saveDonor(UnregisterdDonor donor) {
        return unregisterdDonorRepository.save(donor);
    }
}
