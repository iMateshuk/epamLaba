package com.epam.esm.hateoas;

import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.util.ServiceConvertor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PageParamAssembler implements RepresentationModelAssembler<PageParamDTO, PageParamModel> {
  private final ServiceConvertor convertor;

  @Override
  public PageParamModel toModel(PageParamDTO pageDTO) {
    return convertor.toTarget(pageDTO, PageParamModel.class);
  }
}
