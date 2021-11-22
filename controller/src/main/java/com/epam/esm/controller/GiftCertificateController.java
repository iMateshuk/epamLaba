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
 *
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
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public GiftCertificateDTO createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
    validator.checkCreationCertificate(giftCertificateDTO);
    return giftCertificateService.createGiftCertificate(giftCertificateDTO);
  }

  /**
   *
   * @param allParameters Map<String, String>
   * @return List of GiftCertificateDTO
   *
   * The method can throw ServiceException extends RuntimeException
   */
  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public List<GiftCertificateDTO> getGiftCertificate(@RequestParam Map<String, String> allParameters) {
    return allParameters.size() > 0
        ? giftCertificateService.searchGiftCertificates(allParameters)
        : giftCertificateService.searchGiftCertificates();
  }

  /**
   *
   * @param id must be positive, match RegExp {^\d+$}
   * @return GiftCertificateDTO
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDTO getGiftCertificate(@PathVariable int id) {
    validator.checkId(id);
    return giftCertificateService.searchGiftCertificate(id);
  }

  /**
   *
   * @param giftCertificateDTO from @RequestBody
   * @return GiftCertificateDTO
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDTO updateGiftCertWithTags(@PathVariable int id, @RequestBody GiftCertificateDTO giftCertificateDTO) {
    validator.checkId(id);
    giftCertificateDTO.setId(id);
    validator.checkUpdateCertificate(giftCertificateDTO);
    return giftCertificateService.patchGiftCertificate(giftCertificateDTO);
  }

  /**
   *
   * @param id must be positive
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteGiftCertificate(@PathVariable int id) {
    validator.checkId(id);
    giftCertificateService.delGiftCertificate(id);
  }
}
