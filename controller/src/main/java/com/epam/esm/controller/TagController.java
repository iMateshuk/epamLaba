package com.epam.esm.controller;

import com.epam.esm.hateoas.TagAssembler;
import com.epam.esm.hateoas.TagModel;
import com.epam.esm.page.PageModelCreator;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * RestController Tag
 * Support CRD operation
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */

@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {
  private final TagService tagService;
  private final Validator validator;
  private final TagAssembler tagAssembler;
  private final PageModelCreator modelCreator;

  /**
   * @param tagDTO TagDTO object from @RequestBody
   * @return TagDTO
   */
  @PostMapping
  public ResponseEntity<?> insert(@RequestBody TagDTO tagDTO) {
    String tagName = tagDTO.getName();
    validator.checkTagName(tagName);
    TagModel tagModel = tagAssembler.toModel(tagService.insertByName(tagName));
    tagModel.add(linkTo(methodOn(TagController.class).insert(tagDTO)).withSelfRel());
    return new ResponseEntity<>(tagModel, HttpStatus.CREATED);
  }

  /**
   * @return List of TagDTO
   */
  @GetMapping
  public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int number,
                                   @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int size) {
    PageParamDTO pageParamDTO = PageParamDTO.builder().number(number).size(size).build();
    PageDTO<TagDTO> page = tagService.findAll(pageParamDTO);
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
  public ResponseEntity<?> findById(@PathVariable  @Min(1) @Max(Integer.MAX_VALUE) int id,
                                    @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int number,
                                    @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int size) {
    PageParamDTO pageParamDTO = PageParamDTO.builder().number(number).size(size).build();
    PageDTO<TagDTO> page = tagService.findById(id, pageParamDTO);
    return new ResponseEntity<>(
        modelCreator.createModel(page, tagAssembler, linkTo(TagController.class).slash(id)),
        HttpStatus.OK);
  }

  /**
   * @param id must be positive
   *           <p>
   *           The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteById(@PathVariable  @Min(1) @Max(Integer.MAX_VALUE) int id) {
    validator.checkId(id);
    tagService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
