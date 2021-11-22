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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private TagServiceImpl mockTagService;

    @Mock
    private TagDAO mockTagDAO;

    @Mock
    private Validator mockValidator;

    @Test
    public void createTagTest() {
        Mockito.when(mockTagDAO.createTag("123_ABC")).thenReturn(tagEntity01);
        Mockito.when(mockTagDAO.createTag("123 ABC")).thenReturn(tagEntity02);
        Assertions.assertAll(
                () -> assertEquals(mockTagService.createTag("123_ABC"), tagDTO01),
                () -> assertEquals(mockTagService.createTag("123 ABC"), tagDTO02)
        );
        Mockito.doThrow(ValidationException.class).when(mockValidator).matchField("123!_*@");
        assertThrows(ValidationException.class, () -> mockTagService.createTag("123!_*@"));
    }

    @Test
    public void searchTagsTest() {
        Mockito.when(mockTagDAO.searchTags()).thenReturn(new ArrayList<>());
        assertDoesNotThrow(() -> mockTagService.searchTags());
    }


    @Test
    public void tagSearchTest() {
        Mockito.when(mockTagDAO.isTagExist(1)).thenReturn(true);
        Mockito.when(mockTagDAO.searchTag(1)).thenReturn(tagEntity01);
        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> mockTagService.searchTag(1)),
                () -> assertEquals(mockTagService.searchTag(1), tagDTO01)
        );

    }

    @Test
    public void deleteTagTest() {
        Mockito.when(mockTagDAO.isTagExist(1)).thenReturn(true);
        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> mockTagService.deleteTag(1))
        );

    }

}
