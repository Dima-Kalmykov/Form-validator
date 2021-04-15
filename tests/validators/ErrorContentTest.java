package tests.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import solution.validators.ErrorContent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validation error test")
public class ErrorContentTest {

    @Test
    @DisplayName("Get failed value test")
    void getFailedValue() {
        var simpleError = new ErrorContent("message", "path", 3);
        assertEquals(3, simpleError.getFailedValue());
        var simpleErrorString = new ErrorContent("message", "path", "   ");
        assertEquals("\"   \"", simpleErrorString.getFailedValue());
        var nullError = new ErrorContent("message", "path", null);
        assertNull(nullError.getFailedValue());
        var emptyError = new ErrorContent("message", "path", "");
        assertEquals("\"\"", emptyError.getFailedValue());
    }

    @Test
    @DisplayName("Get message test")
    void getMessage() {
        var simpleError = new ErrorContent("Simple error", "path", "failed value");
        assertEquals("Simple error", simpleError.getMessage());
        var nullError = new ErrorContent(null, "", "");
        assertNull(nullError.getMessage());
        var emptyError = new ErrorContent("", "", "");
        assertEquals("", emptyError.getMessage());
    }

    @Test
    @DisplayName("Get path test")
    void getPath() {
        var error1 = new ErrorContent("message", "/age/[1]/", "");
        assertEquals("age[1]", error1.getPath());
        var error2 = new ErrorContent("message", "/name/[0]/[1]/", "");
        assertEquals("name[0][1]", error2.getPath());
        var error3 = new ErrorContent("message", "/name/[0]/age/", "");
        assertEquals("name[0].age", error3.getPath());
    }
}