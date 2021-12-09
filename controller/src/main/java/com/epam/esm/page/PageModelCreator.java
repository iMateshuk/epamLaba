package com.epam.esm.page;

import com.epam.esm.hateoas.PageModel;
import com.epam.esm.service.page.Page;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PageModelCreator {
  private final PageModelLink pageLink;

  public <S, T extends RepresentationModel<T>> PageModel<T> createModel(Page<S> page,
                                                                        RepresentationModelAssembler<S, T> assembler,
                                                                        WebMvcLinkBuilder linkTo) {
    PageModel<T> pageModel = createModel(page, assembler);
    pageLink.addLinks(pageModel, linkTo);
    return pageModel;
  }

  public <S, T extends RepresentationModel<T>> PageModel<T> createModel(Page<S> page,
                                                                        RepresentationModelAssembler<S, T> assembler) {
    return PageModel.<T>builder()
        .pageSize(page.getPageSize())
        .pageNumber(page.getPageNumber())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .list(page.getList().stream().map(assembler::toModel).collect(Collectors.toList()))
        .build();
  }
}
