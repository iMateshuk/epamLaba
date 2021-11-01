package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {

        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO createGiftCertificate(@RequestBody GiftCertificateDTO gcDTO) {

        return giftCertificateService.createGiftCertificate(gcDTO);
    }

    @GetMapping()
    public List<GiftCertificateDTO> getGiftCertificate(@RequestParam Map<String,String> allRequestParams) {

        return allRequestParams.size() > 0 ? giftCertificateService.searchGiftCertificates(allRequestParams) : giftCertificateService.searchGiftCertificates();
    }

    @GetMapping("/{id:^\\d+$}")
    public GiftCertificateDTO getGiftCertificate(@PathVariable int id) {

        return giftCertificateService.searchGiftCertificate(id);
    }

    @GetMapping("/{tagName:^\\D+.*$}")
    public List<GiftCertificateDTO> chooseTagName(@PathVariable String tagName) {

        return giftCertificateService.getGiftCertificates(tagName);
    }

    @PutMapping("/update")
    public GiftCertificateDTO updateGiftCertificate(@RequestBody GiftCertificateDTO gcDTO) {

        return giftCertificateService.updateGiftCertificate(gcDTO);
    }

    @PutMapping()
    public GiftCertificateDTO updateGiftCertWithTags(@RequestBody GiftCertificateDTO giftCertificateDTO) {

        return giftCertificateService.updateGiftCertWithTags(giftCertificateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable int id) {

        giftCertificateService.delGiftCertificate(id);
    }
}
