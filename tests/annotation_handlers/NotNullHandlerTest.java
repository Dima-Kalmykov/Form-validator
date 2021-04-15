package tests.annotation_handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import solution.annotation_handlers.NotNullHandler;
import solution.utils.ValueContainer;
import solution.validators.ValidationError;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("\"@NotNull\" handler test")
public class NotNullHandlerTest {

    public int intField = 3;
    public short shortField = 4;
    public byte byteField = 5;
    public long longField = 7;
    public boolean boolField = false;
    public double doubleField = 8;
    public char charField = '4';

    public List<Integer> list;
    public Integer wrapperInt = 3;

    public List<List<Integer>> strings = List.of(List.of(), List.of(1, 2), List.of());

    @Test
    @DisplayName("Invalid type test")
    void handleInvalidType() throws NoSuchFieldException {
        var list = new ArrayList<Field>();
        list.add(NotNullHandlerTest.class.getField("intField"));
        list.add(NotNullHandlerTest.class.getField("shortField"));
        list.add(NotNullHandlerTest.class.getField("byteField"));
        list.add(NotNullHandlerTest.class.getField("longField"));
        list.add(NotNullHandlerTest.class.getField("doubleField"));
        list.add(NotNullHandlerTest.class.getField("charField"));
        list.add(NotNullHandlerTest.class.getField("boolField"));

        Set<ValidationError> errorSet = new HashSet<>();
        var pathTracker = new StringBuilder();

        for (var field : list) {
            assertThrows(InvalidParameterException.class, () -> {
                NotNullHandler.handle(this, field, errorSet,
                        ValueContainer.FIELD, pathTracker);
            });
        }
    }

    @Test
    @DisplayName("Handle field test")
    void handleField() throws NoSuchFieldException {
        var list = NotNullHandlerTest.class.getField("list");
        var wrapperInt = NotNullHandlerTest.class.getField("wrapperInt");

        Set<ValidationError> errorSet = new HashSet<>();
        var pathTracker = new StringBuilder();

        NotNullHandler.handle(this, list, errorSet,
                ValueContainer.FIELD, pathTracker);
        assertEquals(1, errorSet.size());
        NotNullHandler.handle(this, wrapperInt, errorSet,
                ValueContainer.FIELD, pathTracker);
        assertEquals(1, errorSet.size());

        assertThrows(InvalidParameterException.class, () -> {
            NotNullHandler.handle(this, null, errorSet,
                    ValueContainer.FIELD, pathTracker);
        });
    }

    @Test
    @DisplayName("Handle object test")
    void handleObject() throws NoSuchFieldException, IllegalAccessException {
        var strings = NotNullHandlerTest.class.getField("strings");
        Set<ValidationError> errorSet = new HashSet<>();
        var pathTracker = new StringBuilder();
        var list = (List<?>)strings.get(this);
        NotNullHandler.handle(list.get(0), strings, errorSet,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(0, errorSet.size());
        NotNullHandler.handle(list.get(1), strings, errorSet,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(0, errorSet.size());
        NotNullHandler.handle(list.get(2), strings, errorSet,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(0, errorSet.size());
        NotNullHandler.handle(null, strings, errorSet,
                ValueContainer.OBJECT, pathTracker);
        assertEquals(1, errorSet.size());
    }
}