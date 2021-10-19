import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilsTest {

    @Test
    public void testStringUtilsPositiveNumberNull() {

        assertFalse(StringUtils.isPositiveNumber(null), "testStringUtilsPositiveNumberNull should return 'false'");
    }

    @Test
    public void testStringUtilsPositiveNumberTrue() {

        assertTrue(StringUtils.isPositiveNumber("123"), "testStringUtilsPositiveNumberTrue should return 'true'");
    }

    @Test
    public void testStringUtilsPositiveNumberFalse() {

        assertFalse(StringUtils.isPositiveNumber("12-3"), "testStringUtilsPositiveNumberFalse should return 'false'");
    }

    @Test
    public void testStringUtilsPositiveNumberZero() {

        assertTrue(StringUtils.isPositiveNumber("0"), "testStringUtilsPositiveNumberZero should return 'true'");
    }

    @Test
    public void testStringUtilsPositiveNumbNegative() {

        assertFalse(StringUtils.isPositiveNumber("-1"), "testStringUtilsPositiveNumbNegative should return 'false'");
    }

}
