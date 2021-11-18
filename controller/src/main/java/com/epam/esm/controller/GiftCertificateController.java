package com.epam.esm.controller;

import com.epam.esm.exception.ControllerException;
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
    private final ControllerValidator controllerValidator;

    public GiftCertificateController(GiftCertificateService giftCertificateService, ControllerValidator controllerValidator) {

        this.giftCertificateService = giftCertificateService;
        this.controllerValidator = controllerValidator;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) throws ControllerException {

        controllerValidator.validateForCreationGiftCertificateDTO(giftCertificateDTO);
        return giftCertificateService.createGiftCertificate(giftCertificateDTO);
    }

    @GetMapping()
    public List<GiftCertificateDTO> getGiftCertificate(@RequestParam Map<String,String> allRequestParams) {

        return allRequestParams.size() > 0 ? giftCertificateService.searchGiftCertificates(allRequestParams) : giftCertificateService.searchGiftCertificates();
    }

    @GetMapping("/{id:^\\d+$}")
    public GiftCertificateDTO getGiftCertificate(@PathVariable int id) throws ControllerException {

        controllerValidator.validateValueOfId(id);
        return giftCertificateService.searchGiftCertificate(id);
    }

    @GetMapping("/{tagName:^\\D+.*$}")
    public List<GiftCertificateDTO> chooseTagName(@PathVariable String tagName) throws ControllerException {

        controllerValidator.validateValueOfTagName(tagName);
        return giftCertificateService.getGiftCertificates(tagName);
    }

    @PutMapping()
    public GiftCertificateDTO updateGiftCertWithTags(@RequestBody GiftCertificateDTO giftCertificateDTO) throws ControllerException {

        controllerValidator.validateForUpdateGiftCertificateDTO(giftCertificateDTO);
        return giftCertificateService.updateGiftCertificateWithTags(giftCertificateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable int id) throws ControllerException {

        controllerValidator.validateValueOfId(id);
        giftCertificateService.delGiftCertificate(id);
    }
}
