package com.epam.esm.controller;

import com.epam.esm.service.CommonService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/{tagName}")
    public List<GiftCertificateDTO> chooseTagName(@PathVariable String tagName) {

        return commonService.getGiftCertificate(tagName);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateGiftCertWithTags(@RequestBody GiftCertificateDTO gcDTO){

        commonService.updateGiftCertWithTags(gcDTO);
    }

}
