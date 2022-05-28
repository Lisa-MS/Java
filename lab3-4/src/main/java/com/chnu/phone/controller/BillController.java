package com.chnu.phone.controller;

import com.chnu.phone.repository.BillRepository;
import com.chnu.phone.service.BillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/bill")
public class BillController {

    private BillService billService;

    @GetMapping
    public String all(Model model, @RequestParam(required = false) String paid) {
        model.addAttribute("bills", billService.allByPaidStatus(paid));
        return "bill";
    }
}
