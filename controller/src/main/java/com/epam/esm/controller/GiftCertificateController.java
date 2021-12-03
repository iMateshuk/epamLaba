package com.epam.esm.controller;

import com.epam.esm.hateoas.GiftCertificateAssembler;
import com.epam.esm.hateoas.GiftCertificateModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * RestController Gift-Certificate
 * Support CRUD operation
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
  private final GiftCertificateService certificateService;
  private final Validator validator;
  private final GiftCertificateAssembler certificateAssembler;

  /**
   * @param giftCertificateDTO with Tags
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @PostMapping
  public ResponseEntity<GiftCertificateModel> insert(@RequestBody GiftCertificateDTO giftCertificateDTO) {
    validator.checkCreationCertificate(giftCertificateDTO);
    GiftCertificateModel certificateModel = certificateAssembler.toModel(certificateService.insert(giftCertificateDTO));
    certificateModel.add(linkTo(methodOn(GiftCertificateController.class).insert(giftCertificateDTO)).withSelfRel());
    return new ResponseEntity<>(certificateModel, HttpStatus.CREATED);
  }

  /**
   * @param parameters Map of parameters
   * @return List of GiftCertificateDTO
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @GetMapping
  public ResponseEntity<CollectionModel<GiftCertificateModel>> findAll(@RequestParam Map<String, String> parameters) {
    List<GiftCertificateDTO> certificates = parameters.size() > 0
        ? certificateService.findAllWithParam(parameters)
        : certificateService.findAll();
    List<GiftCertificateModel> models = certificateAssembler.toModels(certificates);
    return new ResponseEntity<>(
        CollectionModel.of(models, linkTo(methodOn(GiftCertificateController.class).findAll(parameters)).withSelfRel()),
        HttpStatus.OK
    );
  }

  /**
   * @param id must be positive, match RegExp {^\d+$}
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  public ResponseEntity<GiftCertificateModel> findById(@PathVariable int id) {
    validator.checkId(id);
    GiftCertificateModel certificateModel = certificateAssembler.toModel(certificateService.findById(id));
    return new ResponseEntity<>(certificateModel, HttpStatus.OK);
  }

  /**
   * @param giftCertificateDTO from @RequestBody
   * @param id                 positive int
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @PatchMapping("/{id}")
  @ResponseStatus()
  public ResponseEntity<GiftCertificateModel> update(@PathVariable int id,
                                                     @RequestBody GiftCertificateDTO giftCertificateDTO) {
    validator.checkId(id);
    giftCertificateDTO.setId(id);
    validator.checkUpdateCertificate(giftCertificateDTO);
    GiftCertificateModel certificateModel = certificateAssembler.toModel(certificateService.update(giftCertificateDTO));
    certificateModel.add(linkTo(methodOn(GiftCertificateController.class).update(id, giftCertificateDTO)).withSelfRel());
    return new ResponseEntity<>(certificateModel, HttpStatus.OK);
  }

  /**
   * @param id must be positive
   *           <p>
   *           The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
    validator.checkId(id);
    certificateService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
