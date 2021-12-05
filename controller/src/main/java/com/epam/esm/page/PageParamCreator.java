package com.epam.esm.page;

import com.epam.esm.service.dto.PageParamDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class PageParamCreator {
  private static final int MIN_PAGE = 0;
  private static final int MIN_SIZE = 2;
  private static final int MAX_SIZE = 100;

  private static final String SIZE = "size";
  private static final String NUMBER = "number";

  public PageParamDTO buildPageDTOAndRemoveKey(Map<String, String> parameters) {

    int size;
    try {
      size = Integer.parseInt(parameters.get(SIZE));
    } catch (NumberFormatException ignore) {
      size = MIN_SIZE;
    }

    int number;
    try {
      number = Integer.parseInt(parameters.get(NUMBER));
    } catch (NumberFormatException ignore) {
      number = MIN_PAGE;
    }

   parameters.entrySet().removeIf(entry -> entry.getKey().equals(SIZE) || entry.getKey().equals(NUMBER));

    return new PageParamDTO(
        (size < MIN_SIZE || size > MAX_SIZE) ? MIN_SIZE : size,
        Math.max(number, MIN_PAGE)
    );
  }
}
