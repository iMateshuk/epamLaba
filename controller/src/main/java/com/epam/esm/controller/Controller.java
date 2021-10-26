package com.epam.esm.controller;

import com.epam.esm.service.GCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/*@RequestMapping("/gift-certificates")*/
public class Controller {

    @Autowired
    private GCService gcService;

    @RequestMapping("/list")
    public String loadGC() {

        return gcService.list();
    }

}
