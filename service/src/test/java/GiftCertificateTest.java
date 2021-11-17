import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.dto.GiftCertificateConverter;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.util.CheckData;
import com.epam.esm.service.util.RequestedParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCertificateTest {

    private static final TagDTO tagDTO01 = new TagDTO();
    private static final TagDTO tagDTO02 = new TagDTO();

    private static final List<TagDTO> tagDTOs = new ArrayList<>();

    private static final TagEntity tagEntity01 = new TagEntity();
    private static final TagEntity tagEntity02 = new TagEntity();

    private static final List<TagEntity> tagEntities = new ArrayList<>();

    private static final GiftCertificateDTO requestGiftCertificateDTO = new GiftCertificateDTO();

    private static final GiftCertificateEntity giftCertificateEntity = new GiftCertificateEntity();

    private static final Timestamp timestamp = new Timestamp(System.currentTimeMillis());


    @BeforeAll
    public static void setupData() {

        tagDTO01.setName("testTag");
        tagDTO02.setName("otherTestTag");

        tagDTOs.add(tagDTO01);
        tagDTOs.add(tagDTO02);

        tagEntity01.setId(1);
        tagEntity01.setName("testTag");

        tagEntity02.setId(2);
        tagEntity02.setName("otherTestTag");

        tagEntities.add(tagEntity01);
        tagEntities.add(tagEntity02);

        requestGiftCertificateDTO.setDescription("GC Description");
        requestGiftCertificateDTO.setName("GC Name");
        requestGiftCertificateDTO.setDuration(5);
        requestGiftCertificateDTO.setPrice(49.99f);
        requestGiftCertificateDTO.setTags(tagDTOs);

        giftCertificateEntity.setId(1);
        giftCertificateEntity.setName(requestGiftCertificateDTO.getName());
        giftCertificateEntity.setDescription(requestGiftCertificateDTO.getDescription());
        giftCertificateEntity.setPrice(requestGiftCertificateDTO.getPrice());
        giftCertificateEntity.setDuration(requestGiftCertificateDTO.getDuration());
        giftCertificateEntity.setCreateDate(timestamp);
        giftCertificateEntity.setLastUpdateDate(timestamp);
    }

    @BeforeEach
    public void setup() {

        MockitoAnnotations.initMocks(this);

        requestGiftCertificateDTO.setId(0);
    }

    @InjectMocks
    private GiftCertificateServiceImpl mockGiftCertificate;

    @Mock
    private TagDAO mockTagDAO;

    @Mock
    private GiftCertificateDAO mockGiftCertificateDAO;

    @Test
    public void createGiftCertificateTest() {

        GiftCertificateEntity requestedGiftCertificateEntity = GiftCertificateConverter.toEntity(requestGiftCertificateDTO);

        Mockito.when(mockGiftCertificateDAO.createGiftCertificate(requestedGiftCertificateEntity))
                .thenReturn(giftCertificateEntity);

        GiftCertificateDTO createdGiftCertificateDTO = GiftCertificateConverter
                .toDto(mockGiftCertificateDAO.createGiftCertificate(requestedGiftCertificateEntity));

        Mockito.when(mockTagDAO.getListTag(createdGiftCertificateDTO.getId()))
                .thenReturn(tagEntities);

        GiftCertificateDTO returnGiftCertificateDTO = mockGiftCertificate.createGiftCertificate(requestGiftCertificateDTO);

        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> mockGiftCertificateDAO.createGiftCertificate(requestedGiftCertificateEntity)),

                () -> assertEquals(returnGiftCertificateDTO.getId(), 1),
                () -> assertEquals(returnGiftCertificateDTO.getName(), requestedGiftCertificateEntity.getName()),
                () -> assertEquals(returnGiftCertificateDTO.getDescription(), requestedGiftCertificateEntity.getDescription()),
                () -> assertEquals(returnGiftCertificateDTO.getTags().size(), requestGiftCertificateDTO.getTags().size()),
                () -> assertEquals(returnGiftCertificateDTO.getTags().get(0).getName(), requestGiftCertificateDTO.getTags().get(0).getName())
        );

    }

    @Test
    public void searchGiftCertificatesTest() {

        Map<String, String> cleanMap = new HashMap<>();

        Map<String, String> noMatchParam = new HashMap<>();

        Map<String, String> allRequestParams = new HashMap<>();

        Map<String, String> noFindParams = new HashMap<>();

        noMatchParam.put(RequestedParameter.SORT_NAME.toString(), RequestedParameter.SORT_NAME.getParameterKey());

        allRequestParams.put(RequestedParameter.SORT_NAME.getParameterKey(), RequestedParameter.SORT_NAME.toString());

        noFindParams.put(RequestedParameter.SEARCH_NAME.getParameterKey(), RequestedParameter.SEARCH_NAME.toString());

        List<GiftCertificateEntity> entities = new ArrayList<>();
        entities.add(giftCertificateEntity);

        Mockito.when(mockGiftCertificateDAO.getGiftCertificates(CheckData.createMapParameter(allRequestParams)))
                .thenReturn(entities);

        Mockito.when(mockGiftCertificateDAO.getGiftCertificates(CheckData.createMapParameter(noFindParams)))
                .thenReturn(new ArrayList<>());

        Assertions.assertAll(

                () -> assertThrows(IllegalArgumentException.class, () -> mockGiftCertificate.searchGiftCertificates(cleanMap)),
                () -> assertThrows(IllegalArgumentException.class, () -> mockGiftCertificate.searchGiftCertificates(noMatchParam)),

                () -> assertDoesNotThrow(() -> mockGiftCertificate.searchGiftCertificates(allRequestParams)),

                () -> assertThrows(NoSuchElementException.class, () -> mockGiftCertificate.searchGiftCertificates(noFindParams))

        );
    }

    @Test
    public void updateGiftCertificateWithTagsTest() {

        requestGiftCertificateDTO.setId(1);

        GiftCertificateDTO existGiftCertificateDTO = new GiftCertificateDTO();

        TagDTO existTagDTO01 = new TagDTO();
        TagDTO existTagDTO02 = new TagDTO();

        existTagDTO01.setId(1);
        existTagDTO01.setName("testTag");

        existTagDTO02.setId(5);
        existTagDTO02.setName("exist testTag");

        List<TagDTO> existTagDTOs = new ArrayList<>();

        existTagDTOs.add(existTagDTO01);
        existTagDTOs.add(existTagDTO02);

        existGiftCertificateDTO.setId(1);
        existGiftCertificateDTO.setDescription("Description");
        existGiftCertificateDTO.setName("Name");
        existGiftCertificateDTO.setDuration(3);
        existGiftCertificateDTO.setPrice(1.99f);
        existGiftCertificateDTO.setTags(existTagDTOs);

        GiftCertificateEntity requestedGiftCertificateEntity = GiftCertificateConverter.toEntity(requestGiftCertificateDTO);

        Mockito.when(mockGiftCertificateDAO.updateGiftCertificate(requestedGiftCertificateEntity))
                .thenReturn(giftCertificateEntity);

        GiftCertificateDTO updatedGiftCertificateDTO = GiftCertificateConverter
                .toDto(mockGiftCertificateDAO.updateGiftCertificate(requestedGiftCertificateEntity));

        Mockito.when(mockTagDAO.getListTag(updatedGiftCertificateDTO.getId()))
                .thenReturn(tagEntities);

        GiftCertificateDTO returnGiftCertificateDTO = mockGiftCertificate.updateGiftCertificateWithTags(requestGiftCertificateDTO);

        Assertions.assertAll(

                () -> assertEquals(returnGiftCertificateDTO.getId(), existGiftCertificateDTO.getId()),
                () -> assertNotEquals(returnGiftCertificateDTO.getName(), existGiftCertificateDTO.getName()),
                () -> assertNotEquals(returnGiftCertificateDTO.getDescription(), existGiftCertificateDTO.getDescription()),
                () -> assertNotEquals(returnGiftCertificateDTO.getDuration(), existGiftCertificateDTO.getDuration()),
                () -> assertNotEquals(returnGiftCertificateDTO.getPrice(), existGiftCertificateDTO.getPrice()),

                () -> assertEquals(existGiftCertificateDTO.getTags().size(), returnGiftCertificateDTO.getTags().size()),
                () -> assertEquals(returnGiftCertificateDTO.getTags().get(0).getName(), existGiftCertificateDTO.getTags().get(0).getName()),
                () -> assertNotEquals(returnGiftCertificateDTO.getTags().get(1).getName(), existGiftCertificateDTO.getTags().get(1).getName())
        );


    }

    @Test
    public void delGiftCertificateTest() {

        Assertions.assertAll(

                () -> assertDoesNotThrow(() -> mockGiftCertificate.delGiftCertificate(1)),

                () -> assertThrows(IllegalArgumentException.class, () -> mockGiftCertificate.delGiftCertificate(-1)),
                () -> assertThrows(IllegalArgumentException.class, () -> mockGiftCertificate.delGiftCertificate(0))
        );

    }
}