package com.epam.esm.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
  private final GiftCertificateService giftCertificateService;
  private final Validator validator;

  /**
   * @param giftCertificateDTO with Tags
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GiftCertificateDTO insert(@RequestBody GiftCertificateDTO giftCertificateDTO) {
    validator.checkCreationCertificate(giftCertificateDTO);
    return giftCertificateService.insert(giftCertificateDTO);
  }

  /**
   * @param allParameters Map of parameters
   * @return List of GiftCertificateDTO
   * <p>
   * The method can throw ServiceException extends RuntimeException
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<GiftCertificateDTO> findAll(@RequestParam Map<String, String> allParameters) {
    return allParameters.size() > 0
        ? giftCertificateService.findAllWithParam(allParameters)
        : giftCertificateService.findAll();
  }

  /**
   * @param id must be positive, match RegExp {^\d+$}
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDTO findById(@PathVariable int id) {
    validator.checkId(id);
    return giftCertificateService.findById(id);
  }

  /**
   * @param giftCertificateDTO from @RequestBody
   * @param id                 positive int
   * @return GiftCertificateDTO
   * <p>
   * The method can throw ValidationException extends RuntimeException
   */
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDTO update(@PathVariable int id, @RequestBody GiftCertificateDTO giftCertificateDTO) {
    validator.checkId(id);
    giftCertificateDTO.setId(id);
    validator.checkUpdateCertificate(giftCertificateDTO);
    return giftCertificateService.update(giftCertificateDTO);
  }

  /**
   * @param id must be positive
   *           <p>
   *           The method can throw ValidationException extends RuntimeException
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable int id) {
    validator.checkId(id);
    giftCertificateService.deleteById(id);
  }
}
