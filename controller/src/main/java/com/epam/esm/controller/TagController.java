package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.util.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  RestController Tag
 *  Support CRD operation
 *
 *  @author Ivan Matsiashuk
 *  @version 1.0
 */

@RestController
@RequestMapping("/tags")
public class TagController {
  private final TagService tagService;
  private final Validator validator;

  public TagController(TagService tagService, Validator validator) {
    this.tagService = tagService;
    this.validator = validator;
  }

  /**
   *
   * @param tagDTO
   * @return TagDTO
   */
  @PostMapping()
  public TagDTO createTag(@RequestBody TagDTO tagDTO) {
    validator.checkTagName(tagDTO.getName());
    return tagService.createTag(tagDTO.getName());
  }

  /**
   *
   * @return List of TagDTO
   */
  @GetMapping()
  public List<TagDTO> getTags() {
    return tagService.searchTags();
  }

  /**
   *
   * @param id must be positive
   * @return TagDTO
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  public TagDTO getTag(@PathVariable int id) {
    validator.checkId(id);
    return tagService.searchTag(id);
  }

  /**
   *
   * @param id must be positive
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTag(@PathVariable int id) {
    validator.checkId(id);
    tagService.deleteTag(id);
  }
}
