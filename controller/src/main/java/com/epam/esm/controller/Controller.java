package com.epam.esm.controller;

import com.epam.esm.service.Service;
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
    private Service gcService;

    @GetMapping("/opt-by-tag-name")
    @ResponseBody
    public String optByTagName(@RequestParam("tagName") String theTagName) throws JsonProcessingException {

        return objectMapper.writeValueAsString(gcService.getGiftCertificate(theTagName));
    }

}
