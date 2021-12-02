package com.epam.esm.controller;

import com.epam.esm.hateoas.TagAssembler;
import com.epam.esm.hateoas.TagModel;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

  /**
   * @param tagDTO TagDTO object from @RequestBody
   * @return TagDTO
   */
  @PostMapping
  public ResponseEntity<TagModel> insert(@RequestBody TagDTO tagDTO) {
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
  public ResponseEntity<CollectionModel<TagModel>> findAll() {
    List<TagModel> tags = tagService.findAll().stream().map(tagAssembler::toModel).collect(Collectors.toList());
    return new ResponseEntity<>(
        CollectionModel.of(tags, linkTo(methodOn(TagController.class).findAll()).withSelfRel()),
        HttpStatus.OK
    );
  }

  /**
   * @param id must be positive
   * @return TagDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  public ResponseEntity<TagModel> findById(@PathVariable int id) {
    validator.checkId(id);
    TagModel tagModel = tagAssembler.toModel(tagService.findById(id));
    return new ResponseEntity<>(tagModel, HttpStatus.OK);
  }

  /**
   * @param id must be positive
   *           <p>
   *           The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteById(@PathVariable int id) {
    validator.checkId(id);
    tagService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
