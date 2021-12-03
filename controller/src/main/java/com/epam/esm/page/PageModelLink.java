package com.epam.esm.page;

import com.epam.esm.controller.UserController;
import com.epam.esm.hateoas.PageControllerModel;
import com.epam.esm.hateoas.PageModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PageModelLink {

  public void addLinks(PageControllerModel<?> page) {
    final String NUMBER = "number";
    final String SIZE = "size";

    PageModel pageModel = page.getPage();

    int number = pageModel.getNumber();
    long totalPages = pageModel.getTotalPages();
    String size = String.valueOf(pageModel.getSize());

    String first = String.valueOf(0);
    String prev = String.valueOf(Math.max(number - 1, 0));
    String next = String.valueOf(number + 1 >= totalPages ? totalPages : number + 1);
    String last = String.valueOf(totalPages);

    pageModel.add(linkTo(methodOn(UserController.class).findAll(new HashMap<>() {{
      put(NUMBER, first);
      put(SIZE, size);
    }})).withRel("first"));
    pageModel.add(linkTo(methodOn(UserController.class).findAll(new HashMap<>() {{
      put(NUMBER, prev);
      put(SIZE, size);
    }})).withRel("prev"));
    pageModel.add(linkTo(methodOn(UserController.class).findAll(new HashMap<>() {{
      put(NUMBER, String.valueOf(number));
      put(SIZE, size);
    }})).withSelfRel());
    pageModel.add(linkTo(methodOn(UserController.class).findAll(new HashMap<>() {{
      put(NUMBER, next);
      put(SIZE, size);
    }})).withRel("next"));
    pageModel.add(linkTo(methodOn(UserController.class).findAll(new HashMap<>() {{
      put(NUMBER, last);
      put(SIZE, size);
    }})).withRel("last"));
  }
}
