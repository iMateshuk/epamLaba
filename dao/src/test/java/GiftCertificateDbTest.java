import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.config.DaoConfig;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.impl.GiftCertificateDB;
import com.epam.esm.dao.impl.TagDB;
import com.epam.esm.dao.util.GiftCertificateTagSQL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "dev")
@SpringBootTest(classes = {GiftCertificateDB.class, DaoConfig.class, TagDB.class})
public class GiftCertificateDbTest {

  private static final GiftCertificateEntity GIFT_CERTIFICATE = new GiftCertificateEntity();

  private static final String testTagName = "forTestOnly";
  private static final String existTagName = "sky";

  private static final int giftCertificateId = 1;
  private static final int giftCertificateDelId = 2;

  @BeforeAll
  public static void setupData() {

    GIFT_CERTIFICATE.setName("Test name");
    GIFT_CERTIFICATE.setDescription("Test description");
    GIFT_CERTIFICATE.setPrice(99.99f);
    GIFT_CERTIFICATE.setDuration(99);
  }

  @Autowired
  private GiftCertificateDAO giftCertificateDAO;

  @Autowired
  private TagDAO tagDAO;

  @Test
  public void createGiftCertificateTest() {
    assertNotEquals(0, giftCertificateDAO.insertCertificate(GIFT_CERTIFICATE).getId());
  }

  @Test
  public void getGiftCertificatesTest() {
    Assertions.assertNotNull(giftCertificateDAO.findAllCertificates());
  }

  @Test
  public void getGiftCertificateTest() {
    Assertions.assertEquals(giftCertificateId, giftCertificateDAO.findCertificate(giftCertificateId).getId());
  }

  @Test
  public void getGiftCertificatesByMapValue() {
    Map<String, String> parameters = new HashMap<>();
    Map<String, String> notExistValues = new HashMap<>();

    parameters.put(GiftCertificateTagSQL.SEARCH_TAG_NAME.toString(), existTagName);
    notExistValues.put(GiftCertificateTagSQL.SEARCH_TAG_NAME.toString(), testTagName);

    Assertions.assertAll(
        () -> assertDoesNotThrow(() -> giftCertificateDAO.findAllCertificates(parameters)),
        () -> assertNotNull(giftCertificateDAO.findAllCertificates(parameters)),
        () -> assertFalse(giftCertificateDAO.findAllCertificates(parameters).isEmpty()),

        () -> assertDoesNotThrow(() -> giftCertificateDAO.findAllCertificates(notExistValues)),
        () -> assertNotNull(giftCertificateDAO.findAllCertificates(notExistValues)),
        () -> assertTrue(giftCertificateDAO.findAllCertificates(notExistValues).isEmpty())
    );
  }

  @Test
  public void updateGiftCertificateTest() {
    GIFT_CERTIFICATE.setId(1);
    assertNotEquals(giftCertificateDAO.findCertificate(GIFT_CERTIFICATE.getId()),
        giftCertificateDAO.updateCertificate(GIFT_CERTIFICATE));
  }

  @Test
  public void delGiftCertificateAndTagBundleTest() {
    Assertions.assertAll(
        () -> assertDoesNotThrow(() -> tagDAO.insertTag(testTagName)),
        () -> assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateDAO
            .findCertificate(tagDAO.findTag(testTagName).getId())),

        () -> assertDoesNotThrow(() -> tagDAO.deleteTag(tagDAO.findTag(testTagName).getId()))
    );
  }

  @Test
  public void addGiftCertificateTagTest() {
    Assertions.assertAll(
        () -> assertDoesNotThrow(() -> tagDAO.insertTag(testTagName)),

        () -> assertDoesNotThrow(() -> tagDAO.deleteTag(tagDAO.findTag(testTagName).getId()))
    );
  }

  @Test
  public void delGiftCertificateTest() {
    Assertions.assertAll(
        () -> assertDoesNotThrow(() -> giftCertificateDAO.deleteCertificate(giftCertificateDelId)),

        () -> assertThrows(EmptyResultDataAccessException.class,
            () -> giftCertificateDAO.findCertificate(giftCertificateDelId))
    );
  }
}
