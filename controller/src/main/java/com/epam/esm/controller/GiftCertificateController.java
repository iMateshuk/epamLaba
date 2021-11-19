package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.util.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final Validator validator;

    public GiftCertificateController(GiftCertificateService giftCertificateService, Validator validator) {
        this.giftCertificateService = giftCertificateService;
        this.validator = validator;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        validator.checkCreationCertificate(giftCertificateDTO);
        return giftCertificateService.createGiftCertificate(giftCertificateDTO);
    }

    @GetMapping()
    public List<GiftCertificateDTO> getGiftCertificate(@RequestParam Map<String,String> allRequestParams) {
        return allRequestParams.size() > 0
                ? giftCertificateService.searchGiftCertificates(allRequestParams)
                : giftCertificateService.searchGiftCertificates();
    }

    @GetMapping("/{id:^\\d+$}")
    public GiftCertificateDTO getGiftCertificate(@PathVariable int id) {
        validator.checkId(id);
        return giftCertificateService.searchGiftCertificate(id);
    }

    @GetMapping("/{tagName:^\\D+.*$}")
    public List<GiftCertificateDTO> chooseTagName(@PathVariable String tagName) {
        validator.checkTagName(tagName);
        return giftCertificateService.getGiftCertificates(tagName);
    }

    @PutMapping()
    public GiftCertificateDTO updateGiftCertWithTags(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        validator.checkUpdateCertificate(giftCertificateDTO);
        return giftCertificateService.updateGiftCertificateWithTags(giftCertificateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable int id) {
        validator.checkId(id);
        giftCertificateService.delGiftCertificate(id);
    }
}
