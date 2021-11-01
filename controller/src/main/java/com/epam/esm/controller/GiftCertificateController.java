package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {

        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping("/creators")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO createGiftCertificate(@RequestBody GiftCertificateDTO gcDTO) {

        return giftCertificateService.createGiftCertificate(gcDTO);
    }


    @GetMapping()
    public List<GiftCertificateDTO> getGiftCertificates() {

        return giftCertificateService.searchGiftCertificates();
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO getGiftCertificate(@PathVariable int id) {

        return giftCertificateService.searchGiftCertificate(id);
    }

    @GetMapping("/{tagName}")
    public List<GiftCertificateDTO> chooseTagName(@PathVariable String tagName) {

        return giftCertificateService.getGiftCertificates(tagName);
    }

    @PutMapping("/updateCert")
    public GiftCertificateDTO updateGiftCertificate(@RequestBody GiftCertificateDTO gcDTO) {

        return giftCertificateService.updateGiftCertificate(gcDTO);
    }

    @PutMapping("/update")
    public GiftCertificateDTO updateGiftCertWithTags(@RequestBody GiftCertificateDTO giftCertificateDTO){

        return giftCertificateService.updateGiftCertWithTags(giftCertificateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable int id) {

        giftCertificateService.delGiftCertificate(id);
    }
}
