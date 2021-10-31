package com.epam.esm.controller;

import com.epam.esm.service.ServiceCommon;
import com.epam.esm.service.dto.GiftCertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
/*@RequestMapping("/gift-certificates")*/
public class CommonController {

    @Autowired
    private ServiceCommon gcService;

    @GetMapping("/opt-by-tag-name")
    public List<GiftCertDTO> optByTagName(@RequestParam("tagName") String theTagName) {

        return gcService.getGiftCertificate(theTagName);
    }

}
