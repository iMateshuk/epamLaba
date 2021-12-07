package com.epam.esm.hateoas;

import com.epam.esm.service.page.PageParam;
import com.epam.esm.service.util.ServiceConvertor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PageParamAssembler implements RepresentationModelAssembler<PageParam, PageParamModel> {
  private final ServiceConvertor convertor;

  @Override
  public PageParamModel toModel(PageParam pageDTO) {
    return convertor.toTarget(pageDTO, PageParamModel.class);
  }
}
