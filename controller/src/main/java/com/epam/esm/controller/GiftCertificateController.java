package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.util.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * RestController Gift-Certificate
 * Support CRUD operation
 *
 *  @author Ivan Matsiashuk
 *  @version 1.0
 */
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
  private final GiftCertificateService giftCertificateService;
  private final Validator validator;

  public GiftCertificateController(GiftCertificateService giftCertificateService, Validator validator) {
    this.giftCertificateService = giftCertificateService;
    this.validator = validator;
  }

  /**
   *
   * @param giftCertificateDTO with Tags
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GiftCertificateDTO insertCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
    validator.checkCreationCertificate(giftCertificateDTO);
    return giftCertificateService.insertCertificate(giftCertificateDTO);
  }

  /**
   *
   * @param allParameters Map of parameters
   * @return List of GiftCertificateDTO
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<GiftCertificateDTO> findAllCertificate(@RequestParam Map<String, String> allParameters) {
    return allParameters.size() > 0
        ? giftCertificateService.findAllCertificates(allParameters)
        : giftCertificateService.findAllCertificates();
  }

  /**
   *
   * @param id must be positive, match RegExp {^\d+$}
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDTO findCertificate(@PathVariable int id) {
    validator.checkId(id);
    return giftCertificateService.findCertificate(id);
  }

  /**
   *
   * @param giftCertificateDTO from @RequestBody
   * @param id positive int
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDTO updateCertificate(@PathVariable int id, @RequestBody GiftCertificateDTO giftCertificateDTO) {
    validator.checkId(id);
    giftCertificateDTO.setId(id);
    validator.checkUpdateCertificate(giftCertificateDTO);
    return giftCertificateService.updateCertificate(giftCertificateDTO);
  }

  /**
   *
   * @param id must be positive
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCertificate(@PathVariable int id) {
    validator.checkId(id);
    giftCertificateService.deleteCertificate(id);
  }
}
