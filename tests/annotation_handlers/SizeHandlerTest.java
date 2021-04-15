package tests.annotation_handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import solution.annotation_handlers.SizeHandler;
import solution.utils.ValueContainer;
import solution.utils.ValueType;
import solution.validators.ValidationError;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("\"@Size\" handler test")
public class SizeHandlerTest {

    public List<Integer> invalidList = List.of(1, 2);

    @Test
    @DisplayName("Invalid type test")
    void handleInvalidType() throws NoSuchFieldException, IllegalAccessException {
        Set<ValidationError> errorSet = new HashSet<>();
        var field = SizeHandlerTest.class.getField("invalidList");
        var pathTracker = new StringBuilder();
        var list = (List<?>) field.get(this);
        assertThrows(InvalidParameterException.class, () -> {
            SizeHandler.handle(list, field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
        });

        assertThrows(InvalidParameterException.class, () -> {
            SizeHandler.handle(list, field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
        });

        assertThrows(InvalidParameterException.class, () -> {
            SizeHandler.handle(list, field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
        });

        assertThrows(InvalidParameterException.class, () -> {
            SizeHandler.handle(list, field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
        });
    }

    @Nested
    @DisplayName("Handle value test (object)")
    public class ObjectTest {

        public List<List<Integer>> list = List.of(
                List.of(), List.of(1, 2), List.of(4, 5, 2));
        public Set<String> set = Set.of("123");
        public Set<String> emptySet = Set.of("");

        @Test
        @DisplayName("List test")
        void handleList() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var list = ObjectTest.class.getField("list");
            var pathTracker = new StringBuilder();
            var values = (List<?>) list.get(this);

            SizeHandler.handle(values.get(0), list, errorSet, 2, 4,
                    ValueContainer.OBJECT, ValueType.LIST, pathTracker);
            assertEquals(1, errorSet.size());
            SizeHandler.handle(values.get(1), list, errorSet, 1, 2,
                    ValueContainer.OBJECT, ValueType.LIST, pathTracker);
            assertEquals(1, errorSet.size());
            SizeHandler.handle(values.get(2), list, errorSet, 3, 5,
                    ValueContainer.OBJECT, ValueType.LIST, pathTracker);
            assertEquals(1, errorSet.size());
        }

        @Test
        @DisplayName("Set test")
        void handleSet() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var set = ObjectTest.class.getField("set");
            var emptySet = ObjectTest.class.getField("emptySet");
            var pathTracker = new StringBuilder();
            var values = (Set<?>) set.get(this);
            var emptyValues = (Set<?>) emptySet.get(this);
            var iterator = values.iterator();
            var emptyIterator = emptyValues.iterator();
            SizeHandler.handle(iterator.next(), set, errorSet, 4, 5,
                    ValueContainer.OBJECT, ValueType.STRING, pathTracker);
            assertEquals(1, errorSet.size());
            SizeHandler.handle(emptyIterator.next(), emptySet, errorSet,
                    0, 2,
                    ValueContainer.OBJECT, ValueType.STRING, pathTracker);
            assertEquals(1, errorSet.size());
        }
    }

    @Nested
    @DisplayName("Handle value in field")
    public class FieldTest {

        public List<Integer> list = List.of(1, 2, -3);
        public List<Integer> emptyList = List.of();
        public Set<Integer> set = Set.of(1, 2);
        public Set<Integer> emptySet = Set.of();
        public Map<Integer, Integer> map = Map.of(1, 2);
        public Map<Integer, Integer> emptyMap = Map.of();
        public String string = "hello, world";
        public String emptyString = "";

        @Test
        @DisplayName("List test")
        void handleList() throws NoSuchFieldException {
            Set<ValidationError> errorSet = new HashSet<>();
            var list = FieldTest.class.getField("list");
            var emptyList = FieldTest.class.getField("emptyList");
            var pathTracker = new StringBuilder();
            SizeHandler.handle(this, list, errorSet, 1, 4,
                    ValueContainer.FIELD, ValueType.LIST, pathTracker);
            assertEquals(0, errorSet.size());
            SizeHandler.handle(this, emptyList, errorSet, 1, 2,
                    ValueContainer.FIELD, ValueType.LIST, pathTracker);
            assertEquals(1, errorSet.size());
        }

        @Test
        @DisplayName("Set test")
        void handleSet() throws NoSuchFieldException {
            Set<ValidationError> errorSet = new HashSet<>();
            var set = FieldTest.class.getField("set");
            var emptySet = FieldTest.class.getField("emptySet");
            var pathTracker = new StringBuilder();
            SizeHandler.handle(this, set, errorSet, 0, 2,
                    ValueContainer.FIELD, ValueType.SET, pathTracker);
            assertEquals(0, errorSet.size());
            SizeHandler.handle(this, emptySet, errorSet, 1, 5,
                    ValueContainer.FIELD, ValueType.SET, pathTracker);
            assertEquals(1, errorSet.size());
        }

        @Test
        @DisplayName("Map test")
        void handleMap() throws NoSuchFieldException {
            Set<ValidationError> errorSet = new HashSet<>();
            var map = FieldTest.class.getField("map");
            var emptyMap = FieldTest.class.getField("emptyMap");
            var pathTracker = new StringBuilder();
            SizeHandler.handle(this, map, errorSet, 1, 1,
                    ValueContainer.FIELD, ValueType.MAP, pathTracker);
            assertEquals(0, errorSet.size());
            SizeHandler.handle(this, emptyMap, errorSet, 3, 4,
                    ValueContainer.FIELD, ValueType.MAP, pathTracker);
            assertEquals(1, errorSet.size());
        }

        @Test
        @DisplayName("String test")
        void handleString() throws NoSuchFieldException {
            Set<ValidationError> errorSet = new HashSet<>();
            var string = FieldTest.class.getField("string");
            var emptyString = FieldTest.class.getField("emptyString");
            var pathTracker = new StringBuilder();
            SizeHandler.handle(this, string, errorSet, 2, 40,
                    ValueContainer.FIELD, ValueType.STRING, pathTracker);
            assertEquals(0, errorSet.size());
            SizeHandler.handle(this, emptyString, errorSet, 2, 3,
                    ValueContainer.FIELD, ValueType.STRING, pathTracker);
            assertEquals(1, errorSet.size());
        }
    }
}