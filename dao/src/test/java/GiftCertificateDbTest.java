import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = "dev")
@SpringBootTest(classes = {GiftCertificateDaoImpl.class, TagDaoImpl.class})
public class GiftCertificateDbTest {
  private static final GiftCertificateEntity GIFT_CERTIFICATE = new GiftCertificateEntity();

  private static final String testTagName = "forTestOnly";

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
  public void insertTest() {
    assertNotEquals(0, giftCertificateDAO.insert(GIFT_CERTIFICATE).getId());
  }

  @Test
  public void findAllTest() {
    Assertions.assertNotNull(giftCertificateDAO.findAll(new HashMap<>(), 0, 20));
  }

  @Test
  public void findByIdTest() {
    Assertions.assertEquals(1, giftCertificateDAO.findById(giftCertificateId).getId());
  }

  @Test
  public void updateTest() {
    GIFT_CERTIFICATE.setId(1);
    assertNotEquals(giftCertificateDAO.findById(GIFT_CERTIFICATE.getId()),
        giftCertificateDAO.update(GIFT_CERTIFICATE));
  }

  @Test
  public void addGiftCertificateTagTest() {
    Assertions.assertAll(
        () -> assertDoesNotThrow(() -> tagDAO.insertByName(testTagName)),
        () -> assertDoesNotThrow(() -> tagDAO.deleteById(tagDAO.findByName(testTagName).getId()))
    );
  }

  @Test
  public void delGiftCertificateTest() {
    Assertions.assertAll(
        () -> assertDoesNotThrow(() -> giftCertificateDAO.deleteById(giftCertificateDelId)),
        () -> assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateDAO.findById(giftCertificateDelId))
    );
  }
}
