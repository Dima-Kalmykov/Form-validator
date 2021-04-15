package tests.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import solution.utils.MessageBuilder;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Message builder test")
public class MessageBuilderTest {

    @Test
    @DisplayName("Any of messages test")
    void getErrorMessageAnyOf() {
        String[] values1 = {"One", "Two", "Three"};
        var actual1 = MessageBuilder.getErrorMessage(values1);
        var expected1 = "must be one of \"One\", \"Two\", \"Three\"";
        assertEquals(expected1, actual1);

        String[] values2 = {""};
        var actual2 = MessageBuilder.getErrorMessage(values2);
        var expected2 = "must be one of \"\"";
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Must be in range test")
    void getErrorMessageRange() {
        var min1 = 3;
        var max1 = 4;
        var actual1 = MessageBuilder.getErrorMessage(min1, max1, "value");
        var expected1 = "value must be in range between " + min1 + " and " + max1;
        assertEquals(actual1, expected1);

        byte min2 = -100;
        byte max2 = 100;
        var actual2 = MessageBuilder.getErrorMessage(min2, max2, "size");
        var expected2 = "size must be in range between " + min2 + " and " + max2;
        assertEquals(actual2, expected2);
    }

    @Test
    @DisplayName("Other annotations test")
    void testGetError() {
        assertAnnotationMessages("Negative", "must be negative");
        assertAnnotationMessages("Positive", "must be positive");
        assertAnnotationMessages("NotBlank", "must be not blank");
        assertAnnotationMessages("NotEmpty", "must be not empty");
        assertAnnotationMessages("NotNull", "must be not null");

        assertThrows(InvalidParameterException.class, () ->
                MessageBuilder.getErrorMessage("negative"));
        assertThrows(InvalidParameterException.class, () ->
                MessageBuilder.getErrorMessage("not null"));
        assertThrows(InvalidParameterException.class, () ->
                MessageBuilder.getErrorMessage("Not Empty"));
        assertThrows(InvalidParameterException.class, () ->
                MessageBuilder.getErrorMessage("notBlank"));
    }

    /**
     * Check expected and real message for annotation.
     *
     * @param annotation annotation
     * @param expected expected answer
     */
    private void assertAnnotationMessages(String annotation, String expected) {
        var actual = MessageBuilder.getErrorMessage(annotation);
        assertEquals(expected, actual);
    }
}