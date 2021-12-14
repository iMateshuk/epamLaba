package com.epam.esm.controller;

import com.epam.esm.hateoas.TagAssembler;
import com.epam.esm.hateoas.TagModel;
import com.epam.esm.page.PageModelCreator;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * RestController Tag
 * Support CRD operation
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/tags")
public class TagController {
  private final TagService tagService;
  private final TagAssembler tagAssembler;
  private final PageModelCreator modelCreator;

  /**
   * @param tagDTO TagDTO object from @RequestBody
   * @return TagDTO
   */
  @PostMapping
  public ResponseEntity<?> insert(@Valid @RequestBody TagDTO tagDTO) {
    TagModel tagModel = tagAssembler.toModel(tagService.insertByName(tagDTO.getName()));
    return new ResponseEntity<>(tagModel, HttpStatus.CREATED);
  }

  /**
   * @return List of TagDTO
   */
  @GetMapping
  public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int pageNumber,
                                   @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int pageSize) {
    PageParam pageParam = PageParam.builder().pageNumber(pageNumber).pageSize(pageSize).build();
    Page<TagDTO> page = tagService.findAll(pageParam);
    return new ResponseEntity<>(
        modelCreator.createModel(page, tagAssembler, linkTo(TagController.class)),
        HttpStatus.OK);
  }

  /**
   * @param id must be positive
   * @return TagDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int id) {
    TagModel tagModel = tagAssembler.toModel(tagService.findById(id));
    return new ResponseEntity<>(tagModel, HttpStatus.OK);
  }

  /**
   * @param id must be positive
   *           <p>
   *           The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteById(@PathVariable  @Min(1) @Max(Integer.MAX_VALUE) int id) {
    tagService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
