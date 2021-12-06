import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.service.util.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/*@SpringBootTest(classes= {TagServiceImpl.class})*/
public class TagServiceTest {
    private static final TagEntity tagEntity01 = new TagEntity();
    private static final TagEntity tagEntity02 = new TagEntity();

    private static final TagDTO tagDTO01 = new TagDTO();
    private final static TagDTO tagDTO02 = new TagDTO();

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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private TagServiceImpl mockTagService;

    @Mock
    private TagDAO mockTagDAO;

    @Mock
    private Validator mockValidator;

    @Test
    public void createTagTest() {
        Mockito.when(mockTagDAO.insertByName("123_ABC")).thenReturn(tagEntity01);
        Mockito.when(mockTagDAO.insertByName("123 ABC")).thenReturn(tagEntity02);
        Assertions.assertAll(
                () -> assertEquals(mockTagService.insertByName("123_ABC"), tagDTO01),
                () -> assertEquals(mockTagService.insertByName("123 ABC"), tagDTO02)
        );
        Mockito.doThrow(ValidationException.class).when(mockValidator).matchField("123!_*@");
        assertThrows(ValidationException.class, () -> mockTagService.insertByName("123!_*@"));
    }

    /*@Test
    public void searchTagsTest() {
        Mockito.when(mockTagDAO.findAll()).thenReturn(new ArrayList<>());
        assertDoesNotThrow(() -> mockTagService.findAll());
    }*/


    @Test
    public void tagSearchTest() {
        /*Mockito.when(mockTagDAO.isExistById(1)).thenReturn(true);
        Mockito.when(mockTagDAO.findById(1)).thenReturn(tagEntity01);
        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> mockTagService.findById(1)),
                () -> assertEquals(mockTagService.findById(1), tagDTO01)
        );*/
    }

    @Test
    public void deleteTagTest() {
        Mockito.when(mockTagDAO.isExistById(1)).thenReturn(true);
        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> mockTagService.deleteById(1))
        );
    }
}
