package jobhunter.employerservice.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StringValidationTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "      "})
    public void testStringIsEmpty(String str) {
        assertTrue(StringValidation.IsStringEmpty(str));
    }

    @Test
    public void testNullStringIsEmpty() {
        assertTrue(StringValidation.IsStringEmpty(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"124u1u95", "n", "    test ", "aa ", " f ", "this is a string"})
    public void testNotEmptyStrings(String str) {
        assertTrue(StringValidation.IsStringNotEmpty(str));
    }
}