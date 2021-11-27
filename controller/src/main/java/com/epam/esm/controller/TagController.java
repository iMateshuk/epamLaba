package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.util.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController Tag
 * Support CRD operation
 *
 * @author Ivan Matsiashuk
 * @version 1.0
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
   * @param tagDTO TagDTO object from @RequestBody
   * @return TagDTO
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TagDTO insert(@RequestBody TagDTO tagDTO) {
    String tagName = tagDTO.getName();
    validator.checkTagName(tagName);
    return tagService.insertByName(tagName);
  }

  /**
   * @return List of TagDTO
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TagDTO> findAll() {
    return tagService.findAll();
  }

  /**
   * @param id must be positive
   * @return TagDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public TagDTO findById(@PathVariable int id) {
    validator.checkId(id);
    return tagService.findById(id);
  }

  /**
   * @param id must be positive
   *           <p>
   *           The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    validator.checkId(id);
    tagService.deleteById(id);
  }
}
