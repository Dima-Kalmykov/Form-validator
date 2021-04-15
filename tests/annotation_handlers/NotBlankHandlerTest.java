package tests.annotation_handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import solution.annotation_handlers.NotBlankHandler;
import solution.utils.ValueContainer;
import solution.validators.ValidationError;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("\"@NotBlank\" handler test")
public class NotBlankHandlerTest {

    public String hello = "Hello";
    public String spaces = " ";
    public List<String> list = List.of("    ", "", "3");


    @Test
    @DisplayName("Handle \"@NotBlank\" test (field)")
    void handleField() throws NoSuchFieldException {
        Set<ValidationError> errorSet = new HashSet<>();
        var fieldHello = NotBlankHandlerTest.class.getField("hello");
        var spaces = NotBlankHandlerTest.class.getField("spaces");
        var pathTracker = new StringBuilder();
        NotBlankHandler.handle(this, fieldHello, errorSet,
                ValueContainer.FIELD, pathTracker);
        assertEquals(0, errorSet.size());
        NotBlankHandler.handle(this, spaces, errorSet,
                ValueContainer.FIELD, pathTracker);
        assertEquals(1, errorSet.size());
    }

    @Test
    @DisplayName("Handle \"@NotBlank\" test (object)")
    void handleObject() throws NoSuchFieldException, IllegalAccessException {
        Set<ValidationError> errorSet = new HashSet<>();
        var fieldList = NotBlankHandlerTest.class.getField("list");
        var pathTracker = new StringBuilder();
        var list = (List<?>) fieldList.get(this);

        NotBlankHandler.handle(list.get(0), fieldList, errorSet,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(1, errorSet.size());
        NotBlankHandler.handle(list.get(1), fieldList, errorSet,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(2, errorSet.size());
        NotBlankHandler.handle(list.get(2), fieldList, errorSet,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(2, errorSet.size());
    }
}