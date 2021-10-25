package com.epam.esm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/*@RequestMapping("/gift-certificates")*/
public class Controller {

    @RequestMapping("/list")
    public String loadGC() {

        /*List<News> newses = newsService.getNewses();

        theModel.addAttribute("newses", newses);

        return new ModelAndView("news-list");*/

        return "load GC";
    }

}
