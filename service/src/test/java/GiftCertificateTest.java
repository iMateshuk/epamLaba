import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import com.epam.esm.service.util.RequestedParameter;
import com.epam.esm.service.util.Mapper;
import com.epam.esm.service.util.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {GiftCertificateServiceImpl.class})
public class GiftCertificateTest {

  private static final TagDTO tagDTO01 = new TagDTO();
  private static final TagDTO tagDTO02 = new TagDTO();

  private static final List<TagDTO> tagDTOs = new ArrayList<>();

  private static final TagEntity tagEntity01 = new TagEntity();
  private static final TagEntity tagEntity02 = new TagEntity();

  private static final GiftCertificateDTO certificateDTO = new GiftCertificateDTO();
  private static final GiftCertificateEntity certificateEntity = new GiftCertificateEntity();
  private static final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

  private final static PageParam pageParam = PageParam.builder().pageNumber(0).pageSize(20).build();

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

    certificateDTO.setDescription("GC Description");
    certificateDTO.setName("GC Name");
    certificateDTO.setDuration(5);
    certificateDTO.setPrice(49.99f);
    certificateDTO.setTags(tagDTOs);

    certificateEntity.setId(1);
    certificateEntity.setName(certificateDTO.getName());
    certificateEntity.setDescription(certificateDTO.getDescription());
    certificateEntity.setPrice(certificateDTO.getPrice());
    certificateEntity.setDuration(certificateDTO.getDuration());
    certificateEntity.setCreatedDate(timestamp);
    certificateEntity.setModifiedDate(timestamp);
  }

  @Autowired
  private GiftCertificateServiceImpl mockCertificateService;

  @MockBean
  private GiftCertificateDAO mockCertificateDAO;

  @MockBean
  private Validator mockValidator;

  @MockBean
  private Mapper mockMapper;

  @Test
  public void insetTest() {
    when(mockCertificateDAO.isExistByName(certificateDTO.getName())).thenReturn(false);
    when(mockMapper.toTarget(certificateDTO, GiftCertificateEntity.class)).thenReturn(certificateEntity);
    when(mockCertificateDAO.insert(certificateEntity)).thenReturn(certificateEntity);
    certificateDTO.setId(1);
    when(mockMapper.toTarget(certificateEntity, GiftCertificateDTO.class)).thenReturn(certificateDTO);

    assertEquals(certificateDTO, mockCertificateService.insert(certificateDTO));
    assertEquals(1, mockCertificateService.insert(certificateDTO).getId());
  }

  @Test
  public void findByIdTest() {
    when(mockCertificateDAO.isExistById(1)).thenReturn(true);
    when(mockCertificateDAO.findById(1)).thenReturn(certificateEntity);
    certificateDTO.setId(1);
    when(mockMapper.toTarget(certificateEntity, GiftCertificateDTO.class)).thenReturn(certificateDTO);

    assertEquals(1, mockCertificateService.findById(1).getId());

    when(mockCertificateDAO.isExistById(1)).thenReturn(false);
    assertThrows(ServiceException.class, () -> mockCertificateService.findById(1));
  }

  @Test
  public void searchGiftCertificatesTest() {
    Map<String, String> noMatchParam = new HashMap<>();
    Map<String, String> parameters = new HashMap<>();

    List<GiftCertificateEntity> certificateEntities = new ArrayList<>();
    certificateEntities.add(certificateEntity);

    List<GiftCertificateDTO> certificateDTOs = new ArrayList<>();
    certificateDTOs.add(certificateDTO);

    noMatchParam.put(RequestedParameter.SORT_CERT_NAME.toString(), RequestedParameter.SORT_CERT_NAME.getParameterKey());
    parameters.put(RequestedParameter.SORT_CERT_NAME.toString(), RequestedParameter.SORT_CERT_NAME.toString());

    when(mockCertificateDAO.findAll(noMatchParam, pageParam.getPageNumber(), pageParam.getPageSize())).thenReturn(new ArrayList<>());
    when(mockCertificateDAO.findAll(parameters, pageParam.getPageNumber(), pageParam.getPageSize())).thenReturn(certificateEntities);

    when(mockCertificateDAO.count(noMatchParam)).thenReturn(1L);
    when(mockCertificateDAO.count(parameters)).thenReturn(1L);

    when(mockMapper.toTarget(certificateEntities, GiftCertificateDTO.class)).thenReturn(certificateDTOs);

    Page<GiftCertificateDTO> cleanCertificateDTO = Page.<GiftCertificateDTO>builder()
        .pageSize(pageParam.getPageSize())
        .pageNumber(pageParam.getPageNumber())
        .totalElements(1L)
        .lastPage(1L / pageParam.getPageSize())
        .list(new ArrayList<>())
        .build();

    Page<GiftCertificateDTO> machCertificateDTO = Page.<GiftCertificateDTO>builder()
        .pageSize(pageParam.getPageSize())
        .pageNumber(pageParam.getPageNumber())
        .totalElements(1L)
        .lastPage(1L / pageParam.getPageSize())
        .list(certificateDTOs)
        .build();

    assertEquals(0, cleanCertificateDTO.getList().size());
    assertEquals(certificateDTOs, machCertificateDTO.getList());
  }

  @Test
  public void updateTest() {
    certificateDTO.setId(1);
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

    when(mockCertificateDAO.isExistById(certificateDTO.getId())).thenReturn(true);
    when(mockMapper.toTarget(certificateDTO, GiftCertificateEntity.class)).thenReturn(certificateEntity);
    when(mockCertificateDAO.update(certificateEntity)).thenReturn(certificateEntity);
    when(mockMapper.toTarget(certificateEntity, GiftCertificateDTO.class)).thenReturn(certificateDTO);

    GiftCertificateDTO returnGiftCertificateDTO = mockCertificateService.update(certificateDTO);
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
  public void deleteById() {
    when(mockCertificateDAO.isExistById(1000)).thenReturn(true);
    assertDoesNotThrow(() -> mockCertificateService.deleteById(1000));

    when(mockCertificateDAO.isExistById(1001)).thenReturn(false);
    assertThrows(ServiceException.class, () -> mockCertificateService.deleteById(1001));
  }
}
