package com.epam.esm.page;

import com.epam.esm.hateoas.PageModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PageModelLink {

  public <T extends RepresentationModel<T>> void addLinks(PageModel<T> pageModel, WebMvcLinkBuilder linkTo) {
    final String NUMBER = "?pageNumber=";
    final String SIZE = "&pageSize=" + pageModel.getPageSize();

    int number = pageModel.getPageNumber();
    long totalPages = pageModel.getLastPage();

    String first = String.valueOf(0);
    String prev = String.valueOf(Math.max(number - 1, 0));
    String next = String.valueOf(number + 1 >= totalPages ? totalPages : number + 1);
    String last = String.valueOf(totalPages);

    pageModel.add(linkTo.slash(NUMBER + first + SIZE).withRel("first"));
    if (!first.equals(prev)) {
      pageModel.add(linkTo.slash(NUMBER + prev + SIZE).withRel("prev"));
    }
    pageModel.add(linkTo.slash(NUMBER + number + SIZE).withSelfRel());
    if (!last.equals(next)) {
      pageModel.add(linkTo.slash(NUMBER + next + SIZE).withRel("next"));
    }
    pageModel.add(linkTo.slash(NUMBER + last + SIZE).withRel("last"));
  }

  public <T extends RepresentationModel<T>> void addLinks(PageModel<T> page, WebMvcLinkBuilder linkTo,
                                                          Map<String, String> param) {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("?");
    param.forEach((key, value) -> strBuilder.append(key).append("=").append(value).append("&"));
    addLinks(page, linkTo.slash(strBuilder));
  }
}
