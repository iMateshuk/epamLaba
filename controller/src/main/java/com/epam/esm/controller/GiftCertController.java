package com.epam.esm.controller;

import com.epam.esm.service.GiftCertService;
import com.epam.esm.service.dto.GiftCertDTO;
import com.epam.esm.service.dto.GiftCertRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giftcerts")
public class GiftCertController {

    @Autowired
    GiftCertService gcService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGiftCert(@RequestBody GiftCertRequestDTO gcRequestDTO){

        gcService.createGiftCert(gcRequestDTO);
    }

    @GetMapping("/search")
    public List<GiftCertDTO> getGiftCerts() {


        return gcService.searchGiftCerts();
    }

    @GetMapping("/{id}")
    public GiftCertDTO getGiftCert(@PathVariable int id) {

        return gcService.searchGiftCert(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateGiftCert(@RequestBody GiftCertRequestDTO gcRequestDTO){

        gcService.updateGiftCert(gcRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCert(@PathVariable int id) {

        gcService.delGiftCert(id);
    }
}
