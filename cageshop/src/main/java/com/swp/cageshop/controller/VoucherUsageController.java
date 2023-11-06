package com.swp.cageshop.controller;

import com.swp.cageshop.DTO.VoucherUsageDTO;
import com.swp.cageshop.service.voucherUsageService.IVoucherUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cageshop/api/voucher-usage")
public class VoucherUsageController {

    @Autowired
    private IVoucherUsageService voucherUsageService;

    @PostMapping("/add")
    public ResponseEntity<VoucherUsageDTO> createVoucherUsage(@RequestBody VoucherUsageDTO voucherUsageDTO) {
        VoucherUsageDTO createdVoucherUsage = voucherUsageService.createVoucherUsage(voucherUsageDTO);
        return new ResponseEntity<>(createdVoucherUsage, HttpStatus.CREATED);
    }


    @PostMapping("/add-by-voucher")
    public ResponseEntity<VoucherUsageDTO> createVoucherUsageByVoucherCode(@RequestBody VoucherUsageDTO voucherUsageDTO) {
        try {
            VoucherUsageDTO createdVoucherUsage = voucherUsageService.createVoucherUsageByVoucherCode(voucherUsageDTO);
            return new ResponseEntity<>(createdVoucherUsage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<VoucherUsageDTO>> getAllVoucherUsages() {
        List<VoucherUsageDTO> voucherUsages = voucherUsageService.getAllVoucherUsageDTO();
        return new ResponseEntity<>(voucherUsages, HttpStatus.OK);
    }

    @GetMapping("/get-all-by/{orderId}")
    public ResponseEntity<List<String>> listCodeVouchersByOrderId(@PathVariable Long orderId) {
        List<String> codeVouchers = voucherUsageService.findCodeVouchersByOrderId(orderId);
        return new ResponseEntity<>(codeVouchers, HttpStatus.OK);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<VoucherUsageDTO> getVoucherUsageById(@PathVariable Long id) {
        VoucherUsageDTO voucherUsage = voucherUsageService.getVoucherUsageById(id);
        if (voucherUsage != null) {
            return new ResponseEntity<>(voucherUsage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<VoucherUsageDTO> updateVoucherUsage(@RequestBody VoucherUsageDTO voucherUsageDTO) {
        VoucherUsageDTO updatedVoucherUsage = voucherUsageService.updateVoucherUsage(voucherUsageDTO);
        return new ResponseEntity<>(updatedVoucherUsage, HttpStatus.OK);
    }

    @DeleteMapping("/delete-by/{id}")
    public ResponseEntity<Void> deleteVoucherUsage(@PathVariable Long id) {
        voucherUsageService.deleteVoucherUsage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}