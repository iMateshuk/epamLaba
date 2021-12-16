package com.epam.esm.controller;

import com.epam.esm.hateoas.GiftCertificateAssembler;
import com.epam.esm.hateoas.GiftCertificateModel;
import com.epam.esm.hateoas.PageModel;
import com.epam.esm.page.PageModelCreator;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * RestController Gift-Certificate
 * Support CRUD operation
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/certificates")
public class GiftCertificateController {
  private final GiftCertificateService certificateService;
  private final Validator validator;
  private final GiftCertificateAssembler certificateAssembler;
  private final PageModelCreator modelCreator;

  /**
   * @param giftCertificateDTO with Tags
   * @return GiftCertificateModel
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @PostMapping
  public ResponseEntity<GiftCertificateModel> insert(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO) {
    GiftCertificateModel certificateModel = certificateAssembler.toModel(certificateService.insert(giftCertificateDTO));
    return new ResponseEntity<>(certificateModel, HttpStatus.CREATED);
  }

  /**
   * @param parameters Map of parameters
   * @return List of PageModel<GiftCertificateModel>
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @GetMapping
  public ResponseEntity<PageModel<GiftCertificateModel>> findAll(
      @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) int pageNumber,
      @RequestParam(required = false, defaultValue = "20") @Min(2) @Max(50) int pageSize,
      @RequestParam Map<String, String> parameters) {

    PageParam pageParam = PageParam.builder().pageSize(pageSize).pageNumber(pageNumber).build();
    Page<GiftCertificateDTO> page = certificateService.findAll(parameters, pageParam);
    return new ResponseEntity<>(
        modelCreator.createModel(page, certificateAssembler, linkTo(GiftCertificateController.class)),
        HttpStatus.OK);
  }

  /**
   * @param id must be positive
   * @return GiftCertificateModel
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  public ResponseEntity<GiftCertificateModel> findById(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int id) {
    GiftCertificateModel certificateModel = certificateAssembler.toModel(certificateService.findById(id));
    return new ResponseEntity<>(certificateModel, HttpStatus.OK);
  }

  /**
   * @param giftCertificateDTO from @RequestBody
   * @param id                 positive int
   * @return GiftCertificateModel
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @PatchMapping("/{id}")
  @ResponseStatus()
  public ResponseEntity<GiftCertificateModel> update(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int id,
                                                     @RequestBody GiftCertificateDTO giftCertificateDTO) {
    giftCertificateDTO.setId(id);
    validator.validateCertificateForUpdate(giftCertificateDTO);
    certificateService.update(giftCertificateDTO);
    GiftCertificateModel certificateModel = certificateAssembler.toModel(certificateService.findById(id));
    return new ResponseEntity<>(certificateModel, HttpStatus.OK);
  }

  /**
   * @param id must be positive
   *           <p>
   *           The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable @Min(1) @Max(Integer.MAX_VALUE) int id) {
    certificateService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
