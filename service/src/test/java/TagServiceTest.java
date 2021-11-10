import com.epam.esm.dao.impl.TagDB;
import com.epam.esm.dao.jdbc.JdbcConfig;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TagServiceImpl.class, TagDB.class, JdbcConfig.class})
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void createTagNameWrongLengthTest() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> tagService.createTag("0"));
    }

    @Test
    public void createTagWithCorrectNameFirstPositiveTest() {

        Assertions.assertDoesNotThrow(() -> tagService.createTag("123_ABC"));
    }

    @Test
    public void createTagWithCorrectNameSecondPositiveTest() {

        Assertions.assertDoesNotThrow(() -> tagService.createTag("123 ABC"));
    }

    @Test
    public void createTagWithWrongNameNegativeTest() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> tagService.createTag("123!_*@"));
    }

    @Test
    public void tagSearchByIdPositiveTest() {

        TagDTO tagDTO = new TagDTO();

        tagDTO.setId(1);
        tagDTO.setName("spa");

        Assertions.assertEquals(tagService.searchTag(1), tagDTO, "tagSearchByIdPositiveTest should be 'equals'");
    }

    @Test
    public void tagSearchByIdZeroTest() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> tagService.searchTag(0));
    }

    @Test
    public void tagSearchByIdNegativeTest() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> tagService.searchTag(-1));
    }

    @Test
    public void deleteTagByIdZeroTest() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> tagService.deleteTag(0));
    }

    @Test
    public void deleteTagByIdZeroNegative() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> tagService.deleteTag(-1));
    }

}
