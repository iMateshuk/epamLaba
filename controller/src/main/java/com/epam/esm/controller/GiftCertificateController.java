package com.epam.esm.controller;

import com.epam.esm.hateoas.GiftCertificateAssembler;
import com.epam.esm.hateoas.GiftCertificateModel;
import com.epam.esm.hateoas.PageModel;
import com.epam.esm.page.PageModelCreator;
import com.epam.esm.page.PageModelLink;
import com.epam.esm.page.PageParamCreator;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.PageParamDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  private final PageParamCreator pageCreator;
  private final PageModelCreator modelCreator;
  private final PageModelLink modelLink;

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
  public ResponseEntity<?> findAll(@RequestParam Map<String, String> parameters) {
    PageParamDTO pageParamDTO = pageCreator.buildPageDTOAndRemoveKey(parameters);

    PageModel<GiftCertificateModel> model;
    if (parameters.size() > 0) {
      PageDTO<GiftCertificateDTO> certificates = certificateService.findAllWithParam(parameters, pageParamDTO);
      model = modelCreator.createModel(certificates, certificateAssembler);
      modelLink.addLinks(model, linkTo(GiftCertificateController.class), parameters);
    } else {
      PageDTO<GiftCertificateDTO> certificates = certificateService.findAll(pageParamDTO);
      model = modelCreator.createModel(certificates, certificateAssembler, linkTo(GiftCertificateController.class));
    }
    return new ResponseEntity<>(model, HttpStatus.OK);
  }

  /**
   * @param id must be positive, match RegExp {^\d+$}
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable int id, @RequestParam Map<String, String> parameters) {
    validator.checkId(id);
    PageParamDTO pageParamDTO = pageCreator.buildPageDTOAndRemoveKey(parameters);
    PageDTO<GiftCertificateDTO> certificates = certificateService.findById(id, pageParamDTO);
    return new ResponseEntity<>(
        modelCreator.createModel(certificates, certificateAssembler, linkTo(GiftCertificateController.class).slash(id)),
        HttpStatus.OK);
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
