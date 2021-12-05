package com.epam.esm.page;

import com.epam.esm.hateoas.PageModel;
import com.epam.esm.hateoas.PageParamAssembler;
import com.epam.esm.service.dto.PageDTO;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PageModelCreator {
  private final PageParamAssembler pageParamAssembler;
  private final PageModelLink pageLink;

  public <S, T extends RepresentationModel<T>> PageModel<T> createModel(PageDTO<S> pageDTO,
                                                                        RepresentationModelAssembler<S, T> assembler,
                                                                        WebMvcLinkBuilder linkTo){
    PageModel<T> pageModel = createModel(pageDTO, assembler);
    pageLink.addLinks(pageModel, linkTo);
    return pageModel;
  }

  public <S, T extends RepresentationModel<T>> PageModel<T> createModel(PageDTO<S> pageDTO,
                                                                        RepresentationModelAssembler<S, T> assembler){
    PageModel<T> pageModel = new PageModel<>();
    pageModel.setList(pageDTO.getList().stream().map(assembler::toModel).collect(Collectors.toList()));
    pageModel.setPage(pageParamAssembler.toModel(pageDTO.getPage()));
    return pageModel;
  }
}
