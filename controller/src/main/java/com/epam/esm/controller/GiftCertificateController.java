package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    @Autowired
    GiftCertificateService gcService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGiftCertificate(@RequestBody GiftCertificateDTO gcDTO) {

        gcService.createGiftCertificate(gcDTO);
    }


    @GetMapping("/search")
    public List<GiftCertificateDTO> getGiftCertificates() {

        return gcService.searchGiftCertificates();
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO getGiftCertificate(@PathVariable int id) {

        return gcService.searchGiftCertificate(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateGiftCertificate(@RequestBody GiftCertificateDTO gcDTO) {

        gcService.updateGiftCertificate(gcDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable int id) {

        gcService.delGiftCertificate(id);
    }
}
