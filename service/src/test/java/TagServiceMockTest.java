import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.entity.TagEntity;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


/*@ExtendWith(MockitoExtension.class)*/
public class TagServiceMockTest {

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

    @Test
    public void createTagTest() {

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
    public void searchTagsTest() {

        Mockito.when(mockTagDAO.searchTags()).thenReturn(new ArrayList<>());

        assertThrows(NoSuchElementException.class, () -> mockTagService.searchTags());
    }


    @Test
    public void tagSearchTest() {

        TagEntity tagEntity99 = new TagEntity();
        tagEntity99.setName("");

        Mockito.when(mockTagDAO.searchTag(1)).thenReturn(tagEntity01);
        Mockito.when(mockTagDAO.searchTag(99)).thenReturn(tagEntity99);
        Mockito.when(mockTagDAO.searchTag(100)).thenReturn(new TagEntity());

        Assertions.assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> mockTagService.searchTag(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> mockTagService.searchTag(-1)),
                () -> assertThrows(NoSuchElementException.class, () -> mockTagService.searchTag(100)),
                () -> assertThrows(NoSuchElementException.class, () -> mockTagService.searchTag(99)),

                () -> assertDoesNotThrow(() -> mockTagService.searchTag(1)),
                () -> assertEquals(mockTagService.searchTag(1), tagDTO01)
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
