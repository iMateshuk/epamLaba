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
  @GetMapping("/{id:^\\d+$}")
  public GiftCertificateDTO getGiftCertificate(@PathVariable int id) {
    validator.checkId(id);
    return giftCertificateService.searchGiftCertificate(id);
  }

  /**
   *
   * @param tagName match RegExp {^\D+.*$}
   * @return List of GiftCertificateDTO
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{tagName:^\\D+.*$}")
  public List<GiftCertificateDTO> chooseTagName(@PathVariable String tagName) {
    validator.checkTagName(tagName);
    return giftCertificateService.getGiftCertificates(tagName);
  }

  /**
   *
   * @param giftCertificateDTO from @RequestBody
   * @return GiftCertificateDTO
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @PutMapping()
  public GiftCertificateDTO updateGiftCertWithTags(@RequestBody GiftCertificateDTO giftCertificateDTO) {
    validator.checkUpdateCertificate(giftCertificateDTO);
    return giftCertificateService.updateGiftCertificateWithTags(giftCertificateDTO);
  }

  /**
   *
   * @param id must be positive
   *
   * The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteGiftCertificate(@PathVariable int id) {
    validator.checkId(id);
    giftCertificateService.delGiftCertificate(id);
  }
}
