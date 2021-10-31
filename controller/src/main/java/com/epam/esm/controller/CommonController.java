package com.epam.esm.controller;

import com.epam.esm.service.ServiceCommon;
import com.epam.esm.service.dto.GiftCertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class CommonController {

    @Autowired
    private ServiceCommon gcService;

    @GetMapping("/{name}")
    public List<GiftCertDTO> optByTagName(@RequestParam("name") String theTagName) {

        return gcService.getGiftCertificate(theTagName);
    }

}
