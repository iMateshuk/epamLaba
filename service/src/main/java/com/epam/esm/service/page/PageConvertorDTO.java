package com.epam.esm.service.page;

import com.epam.esm.dao.page.PageDAO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.util.ServiceConvertor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PageConvertorDTO {
  private final ServiceConvertor convertor;

  public <S, T> PageDTO<T> toDto(PageDAO<S> source, Class<T> target){
    PageDTO<T> pageDTO = new PageDTO<>();
    pageDTO.setList(convertor.toTarget(source.getList(), target));
    pageDTO.setPage(convertor.toTarget(source.getPage(), PageParamDTO.class));
    return pageDTO;
  }
}
