package com.epam.esm.controller;

import com.epam.esm.service.GCService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
/*@RequestMapping("/gift-certificates")*/
public class Controller {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private GCService gcService;

    @RequestMapping("/list")
    public String list() {

        return gcService.list();
    }

    @GetMapping("/load")
    @ResponseBody
    public String loadGC() throws JsonProcessingException {

        return objectMapper.writeValueAsString(gcService.listGift());
    }

    @GetMapping("/getGiftByTag")
    @ResponseBody
    public String getGC(@RequestParam("theID") int theID) throws JsonProcessingException {

        return objectMapper.writeValueAsString(gcService.getGiftCert(theID));
    }

}
