package tests.annotation_handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import solution.annotation_handlers.AnyOfHandler;
import solution.utils.ValueContainer;
import solution.validators.ValidationError;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("\"@AnyOf\" handler test")
public class AnyOfHandlerTest {

    public String hello = "Hello";
    public List<String> list = List.of("One", "two", "3");

    @Test
    @DisplayName("Handle \"@AnyOf\" test (field)")
    void handleField() throws NoSuchFieldException {
        Set<ValidationError> errorSet = new HashSet<>();
        var fieldHello = AnyOfHandlerTest.class.getField("hello");
        String[] values1 = {"Hello", "World"};
        String[] values2 = {"hello", "Access"};
        var pathTracker = new StringBuilder();
        AnyOfHandler.handle(this, fieldHello, errorSet, values1,
                ValueContainer.FIELD, pathTracker);
        assertEquals(0, errorSet.size());
        AnyOfHandler.handle(this, fieldHello, errorSet, values2,
                ValueContainer.FIELD, pathTracker);
        assertEquals(1, errorSet.size());
    }

    @Test
    @DisplayName("Handle \"@AnyOf\" test (object)")
    void handleObject() throws NoSuchFieldException, IllegalAccessException {
        Set<ValidationError> errorSet = new HashSet<>();
        var fieldList = AnyOfHandlerTest.class.getField("list");
        String[] values1 = {"One", "Two"};
        String[] values2 = {"three", "33"};
        var pathTracker = new StringBuilder();
        var list = (List<?>) fieldList.get(this);

        AnyOfHandler.handle(list.get(0), fieldList, errorSet, values1,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(0, errorSet.size());
        AnyOfHandler.handle(list.get(1), fieldList, errorSet, values1,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(1, errorSet.size());
        AnyOfHandler.handle(list.get(2), fieldList, errorSet, values2,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(2, errorSet.size());
    }
}