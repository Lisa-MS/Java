package com.chnu.phone.service;

import com.chnu.phone.entity.Bill;
import com.chnu.phone.repository.BillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BillService {
    private BillRepository billRepository;

    public List<Bill> allByPaidStatus(String paid) {
        if (paid == null) {
            return billRepository.findAll();
        }
        if (paid.equals("true")) {
            return billRepository.findByPayedAtIsNotNull();
        }
        return billRepository.findByPayedAtIsNull();
    }
}
