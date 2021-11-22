import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.impl.GiftCertificateDB;
import com.epam.esm.dao.impl.TagDB;
import com.epam.esm.dao.config.DaoConfig;
import com.epam.esm.dao.util.GiftCertificateTagSQL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = "dev")
@ContextConfiguration(classes = {GiftCertificateDB.class, DaoConfig.class, TagDB.class})
public class GiftCertificateDbTest {

    private static final GiftCertificateEntity giftCertificateEntity = new GiftCertificateEntity();

    private static final String testTagName = "forTestOnly";
    private static final String existTagName = "sky";

    private static final int giftCertificateId = 1;
    private static final int giftCertificateDelId = 2;

    @BeforeAll
    public static void setupData() {

        giftCertificateEntity.setName("Test name");
        giftCertificateEntity.setDescription("Test description");
        giftCertificateEntity.setPrice(99.99f);
        giftCertificateEntity.setDuration(99);
    }

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    @Autowired
    private TagDAO tagDAO;

    @Test
    public void createGiftCertificateTest() {

        assertNotEquals(0, giftCertificateDAO.createGiftCertificate(giftCertificateEntity).getId());
    }

    @Test
    public void getGiftCertificatesTest() {

        Assertions.assertNotNull(giftCertificateDAO.getGiftCertificates());
    }

    @Test
    public void getGiftCertificateTest() {

        Assertions.assertEquals(giftCertificateId, giftCertificateDAO.getGiftCertificate(giftCertificateId).getId());
    }

    @Test
    public void getGiftCertificatesByTagNameTest() {

        Assertions.assertAll(

                () -> assertTrue(giftCertificateDAO.getGiftCertificates(testTagName).isEmpty()),
                () -> assertNotNull(giftCertificateDAO.getGiftCertificates(existTagName)),
                () -> assertFalse(giftCertificateDAO.getGiftCertificates(existTagName).isEmpty())
        );
    }

    @Test
    public void getGiftCertificatesByMapValue() {

        Map<String, String> parameters = new HashMap<>();
        Map<String, String> notExistValues = new HashMap<>();

        parameters.put(GiftCertificateTagSQL.SEARCH_TAG_NAME.toString(), existTagName);
        notExistValues.put(GiftCertificateTagSQL.SEARCH_TAG_NAME.toString(), testTagName);

        Assertions.assertAll(

                () -> assertDoesNotThrow(() -> giftCertificateDAO.getGiftCertificates(parameters)),
                () -> assertNotNull(giftCertificateDAO.getGiftCertificates(parameters)),
                () -> assertFalse(giftCertificateDAO.getGiftCertificates(parameters).isEmpty()),

                () -> assertDoesNotThrow(() -> giftCertificateDAO.getGiftCertificates(notExistValues)),
                () -> assertNotNull(giftCertificateDAO.getGiftCertificates(notExistValues)),
                () -> assertTrue(giftCertificateDAO.getGiftCertificates(notExistValues).isEmpty())
        );
    }

    @Test
    public void updateGiftCertificateTest() {

        giftCertificateEntity.setId(1);

        assertNotEquals(giftCertificateDAO.getGiftCertificate(giftCertificateEntity.getId()),
                        giftCertificateDAO.updateGiftCertificate(giftCertificateEntity));
    }

    @Test
    public void delGiftCertificateAndTagBundleTest() {


        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> tagDAO.createTag(testTagName)),

                () -> assertDoesNotThrow(() -> giftCertificateDAO
                        .delGiftCertificateAndTagBundle(tagDAO.searchTag(testTagName).getId())),

                () -> assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateDAO
                        .getGiftCertificate(tagDAO.searchTag(testTagName).getId())),

                () -> assertDoesNotThrow(() -> tagDAO.deleteTag(tagDAO.searchTag(testTagName).getId()))
        );
    }

    @Test
    public void addGiftCertificateTagTest() {

        Assertions.assertAll(

                () -> assertDoesNotThrow(() -> tagDAO.createTag(testTagName)),
                () -> assertDoesNotThrow(() -> giftCertificateDAO.addGiftCertificateTag(giftCertificateId, testTagName)),

                () -> assertTrue(tagDAO.getListTag(giftCertificateId)
                        .stream().anyMatch((value) -> value.getName().equals(testTagName))),

                () -> assertDoesNotThrow(() -> tagDAO.deleteTag(tagDAO.searchTag(testTagName).getId()))
        );
    }

    @Test
    public void delGiftCertificateTest() {

        Assertions.assertAll(

                () -> assertDoesNotThrow(() -> giftCertificateDAO.delGiftCertificate(giftCertificateDelId)),

                () -> assertThrows(EmptyResultDataAccessException.class,
                        () -> giftCertificateDAO.getGiftCertificate(giftCertificateDelId))
        );
    }
}
