package com.epam.esm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UiController {

  @RequestMapping
  public String index() {
    return "index";
  }
}
