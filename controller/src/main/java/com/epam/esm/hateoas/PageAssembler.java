package com.epam.esm.hateoas;

import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.util.ControllerConvertor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PageAssembler implements RepresentationModelAssembler<PageDTO, PageModel> {
  private final ControllerConvertor convertor;

  @Override
  public PageModel toModel(PageDTO pageDTO) {
    return convertor.toTarget(pageDTO, PageModel.class);
  }
}
