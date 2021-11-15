import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;


/*@ExtendWith(MockitoExtension.class)*/
public class TagServiceMockTest {

    @BeforeEach
    public void setup() {

        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private TagServiceImpl mockTagService;

    @Mock
    private TagDAO mockTagDAO;

    @Test
    public void createTagTest() {

        TagEntity tagEntity01 = new TagEntity();
        tagEntity01.setId(1);
        tagEntity01.setName("123_ABC");

        TagEntity tagEntity02 = new TagEntity();
        tagEntity01.setId(2);
        tagEntity01.setName("123 ABC");

        TagDTO tagDTO01 = new TagDTO();
        tagDTO01.setId(1);
        tagDTO01.setName("123_ABC");

        TagDTO tagDTO02 = new TagDTO();
        tagDTO01.setId(2);
        tagDTO01.setName("123 ABC");

        Mockito.when(mockTagDAO.createTag("123_ABC")).thenReturn(tagEntity01);
        Mockito.when(mockTagDAO.createTag("123 ABC")).thenReturn(tagEntity02);

        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> mockTagService.createTag("123_ABC")),
                () -> assertEquals(mockTagService.createTag("123_ABC"), tagDTO01),

                () -> assertDoesNotThrow(() -> mockTagService.createTag("123 ABC")),
                () -> assertEquals(mockTagService.createTag("123 ABC"), tagDTO02),

                () -> assertThrows(IllegalArgumentException.class, () -> mockTagService.createTag("123!_*@"))
        );
    }

    @Test
    public void tagSearchTest() {

        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(1);
        tagEntity.setName("123_ABC");

        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(1);
        tagDTO.setName("123_ABC");

        Mockito.when(mockTagDAO.searchTag(1)).thenReturn(tagEntity);

        Assertions.assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> mockTagService.searchTag(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> mockTagService.searchTag(-1)),

                () -> assertDoesNotThrow(() -> mockTagService.searchTag(1)),
                () -> assertEquals(mockTagService.searchTag(1), tagDTO)
        );

    }

    @Test
    public void deleteTagTest() {

        Assertions.assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> mockTagService.deleteTag(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> mockTagService.deleteTag(-1)),

                () -> assertDoesNotThrow(() -> mockTagService.deleteTag(1))
        );

    }

}
