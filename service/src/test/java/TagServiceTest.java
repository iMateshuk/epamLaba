import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.service.page.Page;
import com.epam.esm.service.page.PageParam;
import com.epam.esm.service.util.Mapper;
import com.epam.esm.service.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {TagServiceImpl.class})
public class TagServiceTest {
  private final static TagEntity tagEntity01 = new TagEntity();
  private final static TagEntity tagEntity02 = new TagEntity();

  private final static TagDTO tagDTO01 = new TagDTO();
  private final static TagDTO tagDTO02 = new TagDTO();

  private final static PageParam pageParam = PageParam.builder().pageNumber(0).pageSize(20).build();

  @BeforeAll
  public static void setupData() {

    tagEntity01.setId(1);
    tagEntity01.setName("123_ABC");

    tagEntity01.setId(2);
    tagEntity01.setName("123 ABC");


    tagDTO01.setId(1);
    tagDTO01.setName("123_ABC");

    tagDTO01.setId(2);
    tagDTO01.setName("123 ABC");
  }

  @Autowired
  private TagServiceImpl mockTagService;

  @MockBean
  private TagDAO mockTagDAO;

  @MockBean
  private Validator mockValidator;

  @MockBean
  private Mapper mockMapper;

  @Test
  public void insertByNameTest() {
    when(mockTagDAO.isExistByName("123_ABC")).thenReturn(false);
    when(mockTagDAO.insertByName("123_ABC")).thenReturn(tagEntity01);
    when(mockMapper.toTarget(tagEntity01, TagDTO.class)).thenReturn(tagDTO01);
    assertEquals(tagDTO01, mockTagService.insertByName("123_ABC"));
    assertEquals(2, mockTagService.insertByName("123_ABC").getId());

    assertThrows(ValidationException.class, () -> mockTagService.insertByName("123!_*@"));
  }

  @Test
  public void findAllTest() {
    List<TagEntity> tags = new ArrayList<>();
    tags.add(tagEntity01);
    tags.add(tagEntity02);

    List<TagDTO> dtos = new ArrayList<>();
    dtos.add(tagDTO01);
    dtos.add(tagDTO02);

    when(mockMapper.toTarget(tags, TagDTO.class)).thenReturn(dtos);
    when(mockTagDAO.findAll(pageParam.getPageNumber(), pageParam.getPageSize())).thenReturn(tags);
    when(mockTagDAO.count()).thenReturn(2L);

    Page<TagDTO> pageTagDTO = Page.<TagDTO>builder()
        .pageSize(pageParam.getPageSize())
        .pageNumber(pageParam.getPageNumber())
        .totalElements(2L)
        .lastPage(2L / pageParam.getPageSize())
        .list(dtos)
        .build();

    assertEquals(pageTagDTO, mockTagService.findAll(pageParam));
    assertDoesNotThrow(() -> mockTagService.findAll(pageParam));
  }


  @Test
  public void findById() {
    when(mockTagDAO.findById(1)).thenReturn(tagEntity01);
    when(mockMapper.toTarget(tagEntity01, TagDTO.class)).thenReturn(tagDTO01);
    assertEquals(tagDTO01, mockTagService.findById(1));
    assertEquals(2, mockTagService.findById(1).getId());

    when(mockTagDAO.findById(1)).thenReturn(null);
    assertThrows(ServiceException.class, () -> mockTagService.findById(1));
  }

  @Test
  public void deleteTagTest() {
    when(mockTagDAO.isExistById(1000)).thenReturn(true);
    assertDoesNotThrow(() -> mockTagService.deleteById(1000));

    when(mockTagDAO.isExistById(1001)).thenReturn(false);
    assertThrows(ServiceException.class, () -> mockTagService.deleteById(1001));
  }
}
