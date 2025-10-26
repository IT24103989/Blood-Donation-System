package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.DTO.DonorFrequencyDTO;
import com.donation.LifeLine.model.DTO.DonationHistoryDTO;
import com.donation.LifeLine.model.DonationHistory;
import com.donation.LifeLine.services.DonationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DonationHistoryController {

    @Autowired
    private DonationHistoryService donationHistoryService;

    // ========================
    // CRUD OPERATIONS
    // ========================

    @PostMapping("/donation-history")
    public ResponseEntity<DonationHistory> create(@RequestBody DonationHistoryDTO dto) {
        DonationHistory dh = donationHistoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dh);
    }

    @GetMapping("/donation-history/{id}")
    public ResponseEntity<DonationHistory> getById(@PathVariable Long id) {
        return donationHistoryService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // View all or filter by NIC (Admin use)
    @GetMapping("/donation-history")
    public ResponseEntity<List<DonationHistory>> getAll(
            @RequestParam(required = false) String donorNIC) {
        return ResponseEntity.ok(donationHistoryService.getByDonorNIC(donorNIC));
    }

    // Donor dashboard - view by NIC
    @GetMapping("/donation-history/nic/{nic}")
    public ResponseEntity<List<DonationHistory>> getByDonorNIC(@PathVariable String nic) {
        List<DonationHistory> list = donationHistoryService.getByDonorNIC(nic);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/donation-history/{id}")
    public ResponseEntity<DonationHistory> update(
            @PathVariable Long id, @RequestBody DonationHistoryDTO dto) {
        return donationHistoryService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/donation-history/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = donationHistoryService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ========================
    // REPORTS
    // ========================

    @GetMapping("/reports/donor-frequency")
    public ResponseEntity<List<DonorFrequencyDTO>> donorFrequency(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) Long minDonations) {

        return ResponseEntity.ok(donationHistoryService.donorFrequencyReport(from, to, minDonations));
    }

    @GetMapping("/reports/donor-frequency/export")
    public ResponseEntity<byte[]> donorFrequencyExportCsv(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) Long minDonations) {

        List<DonorFrequencyDTO> report = donationHistoryService.donorFrequencyReport(from, to, minDonations);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(baos, true);

        pw.println("Donor NIC,Donor Name,Donation Count");

        for (DonorFrequencyDTO d : report) {
            String nic = (d.getDonorNIC() != null ? d.getDonorNIC().replace(",", " ") : "N/A");
            String name = (d.getDonorName() != null ? d.getDonorName().replace(",", " ") : "N/A");
            long count = (d.getDonationCount() != null ? d.getDonationCount() : 0L);

            pw.printf("%s,%s,%d%n", nic, name, count);
        }

        pw.flush();
        byte[] csv = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("donor_frequency_report.csv")
                .build());

        return new ResponseEntity<>(csv, headers, HttpStatus.OK);
    }
}