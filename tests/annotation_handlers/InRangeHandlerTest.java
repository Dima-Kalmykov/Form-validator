package tests.annotation_handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import solution.annotation_handlers.InRangeHandler;
import solution.utils.ValueContainer;
import solution.utils.ValueType;
import solution.validators.ValidationError;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("\"@InRange\" handler test")
public class InRangeHandlerTest {

    public List<Integer> invalidList = List.of(1, 2);

    @Test
    @DisplayName("Invalid type test")
    void handleInvalidType() throws NoSuchFieldException, IllegalAccessException {
        Set<ValidationError> errorSet = new HashSet<>();
        var field = InRangeHandlerTest.class.getField("invalidList");
        var pathTracker = new StringBuilder();
        var list = (List<?>) field.get(this);

        assertThrows(InvalidParameterException.class, () -> {
            InRangeHandler.handle(list.get(0), field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.MAP, pathTracker);
        });

        assertThrows(InvalidParameterException.class, () -> {
            InRangeHandler.handle(list.get(0), field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.LIST, pathTracker);
        });

        assertThrows(InvalidParameterException.class, () -> {
            InRangeHandler.handle(list.get(0), field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.SET, pathTracker);
        });

        assertThrows(InvalidParameterException.class, () -> {
            InRangeHandler.handle(list.get(0), field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.STRING, pathTracker);
        });
    }

    @Nested
    @DisplayName("Handle value in object")
    class ObjectTest {

        public List<Byte> byteList = List.of((byte) 1, (byte) 2, (byte) 3);
        public List<Short> shortList = List.of((short) 1, (short) 2, (short) 3);
        public List<Integer> integerList = List.of(1, 2, 3);
        public List<Long> longList = List.of(1L, 2L, 3L);

        @Test
        @DisplayName("Handle byte test")
        void handleByte() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var field = ObjectTest.class.getField("byteList");
            var pathTracker = new StringBuilder();
            var list = (List<?>) field.get(this);
            InRangeHandler.handle(list.get(0), field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
            assertEquals(1, errorSet.size());
            InRangeHandler.handle(list.get(0), field, errorSet, 1, 1,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
            assertEquals(1, errorSet.size());
            InRangeHandler.handle(list.get(1), field, errorSet, 3, 4,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
            assertEquals(2, errorSet.size());
            InRangeHandler.handle(list.get(1), field, errorSet, 1, 4,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
            assertEquals(2, errorSet.size());
            InRangeHandler.handle(list.get(2), field, errorSet, 4, 120,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
            assertEquals(3, errorSet.size());
            InRangeHandler.handle(list.get(2), field, errorSet, 1, 10,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
            assertEquals(3, errorSet.size());
        }

        @Test
        @DisplayName("Handle short test")
        void handleShort() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var field = ObjectTest.class.getField("shortList");
            var pathTracker = new StringBuilder();
            var list = (List<?>) field.get(this);
            InRangeHandler.handle(list.get(0), field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
            assertEquals(1, errorSet.size());
            InRangeHandler.handle(list.get(0), field, errorSet, 1, 1,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
            assertEquals(1, errorSet.size());
            InRangeHandler.handle(list.get(1), field, errorSet, 3, 4,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
            assertEquals(2, errorSet.size());
            InRangeHandler.handle(list.get(1), field, errorSet, 1, 4,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
            assertEquals(2, errorSet.size());
            InRangeHandler.handle(list.get(2), field, errorSet, 4, 120,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
            assertEquals(3, errorSet.size());
            InRangeHandler.handle(list.get(2), field, errorSet, 1, 10,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
            assertEquals(3, errorSet.size());
        }

        @Test
        @DisplayName("Handle integer test")
        void handleInteger() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var field = ObjectTest.class.getField("integerList");
            var pathTracker = new StringBuilder();
            var list = (List<?>) field.get(this);
            InRangeHandler.handle(list.get(0), field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
            assertEquals(1, errorSet.size());
            InRangeHandler.handle(list.get(0), field, errorSet, 1, 1,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
            assertEquals(1, errorSet.size());
            InRangeHandler.handle(list.get(1), field, errorSet, 3, 4,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
            assertEquals(2, errorSet.size());
            InRangeHandler.handle(list.get(1), field, errorSet, 1, 4,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
            assertEquals(2, errorSet.size());
            InRangeHandler.handle(list.get(2), field, errorSet, 4, 120,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
            assertEquals(3, errorSet.size());
            InRangeHandler.handle(list.get(2), field, errorSet, 1, 10,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
            assertEquals(3, errorSet.size());
        }

        @Test
        @DisplayName("Handle long test")
        void handleLong() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var field = ObjectTest.class.getField("longList");
            var pathTracker = new StringBuilder();
            var list = (List<?>) field.get(this);
            InRangeHandler.handle(list.get(0), field, errorSet, 2, 3,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
            assertEquals(1, errorSet.size());
            InRangeHandler.handle(list.get(0), field, errorSet, 1, 1,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
            assertEquals(1, errorSet.size());
            InRangeHandler.handle(list.get(1), field, errorSet, 3, 4,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
            assertEquals(2, errorSet.size());
            InRangeHandler.handle(list.get(1), field, errorSet, 1, 4,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
            assertEquals(2, errorSet.size());
            InRangeHandler.handle(list.get(2), field, errorSet, 4, 120,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
            assertEquals(3, errorSet.size());
            InRangeHandler.handle(list.get(2), field, errorSet, 1, 10,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
            assertEquals(3, errorSet.size());
        }
    }

    @Nested
    @DisplayName("Handle value test (field)")
    public class FieldTest {

        @Nested
        @DisplayName("Byte test")
        public class ByteTest {

            public byte byteField = 3;
            public Byte byteFieldWrapper = 3;

            @Test
            @DisplayName("Handle byte test")
            void handleByte() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var field = ByteTest.class.getField("byteField");
                var pathTracker = new StringBuilder();
                InRangeHandler.handle(this, field, errorSet, 2, 3,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 3,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 4,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 1, 2,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(1, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 4, 120,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(2, errorSet.size());
            }

            @Test
            @DisplayName("Handle byte wrapper test")
            void handleByteWrapper() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var field = ByteTest.class.getField("byteFieldWrapper");
                var pathTracker = new StringBuilder();
                InRangeHandler.handle(this, field, errorSet, 2, 3,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 3,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 4,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 1, 2,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(1, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 4, 120,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(2, errorSet.size());
            }
        }

        @Nested
        @DisplayName("Short test")
        public class ShortTest {

            public short shortField = 3;
            public Short shortFieldWrapper = 3;

            @Test
            @DisplayName("Handle short test")
            void handleShort() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var field = ShortTest.class.getField("shortField");
                var pathTracker = new StringBuilder();
                InRangeHandler.handle(this, field, errorSet, 2, 3,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 3,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 4,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 1, 2,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(1, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 4, 120,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(2, errorSet.size());
            }

            @Test
            @DisplayName("Handle short wrapper test")
            void handleShortWrapper() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var field = ShortTest.class.getField("shortFieldWrapper");
                var pathTracker = new StringBuilder();
                InRangeHandler.handle(this, field, errorSet, 2, 3,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 3,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 4,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 1, 2,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(1, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 4, 120,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(2, errorSet.size());
            }
        }

        @Nested
        @DisplayName("Integer test")
        public class IntegerTest {

            public int integerField = 3;
            public Integer IntegerFieldWrapper = 3;

            @Test
            @DisplayName("Handle integer test")
            void handleInteger() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var field = IntegerTest.class.getField("integerField");
                var pathTracker = new StringBuilder();
                InRangeHandler.handle(this, field, errorSet, 2, 3,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 3,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 4,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 1, 2,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(1, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 4, 120,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(2, errorSet.size());
            }

            @Test
            @DisplayName("Handle integer wrapper test")
            void handleIntegerWrapper() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var field = IntegerTest.class.getField("IntegerFieldWrapper");
                var pathTracker = new StringBuilder();
                InRangeHandler.handle(this, field, errorSet, 2, 3,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 3,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 4,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 1, 2,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(1, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 4, 120,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(2, errorSet.size());
            }
        }

        @Nested
        @DisplayName("Long test")
        public class LongTest {

            public long longField = 3;
            public Long longFieldWrapper = 3L;

            @Test
            @DisplayName("Handle long test")
            void handleLong() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var field = LongTest.class.getField("longField");
                var pathTracker = new StringBuilder();
                InRangeHandler.handle(this, field, errorSet, 2, 3,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 3,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 4,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 1, 2,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(1, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 4, 120,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(2, errorSet.size());
            }

            @Test
            @DisplayName("Handle long wrapper test")
            void handleLongWrapper() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var field = LongTest.class.getField("longFieldWrapper");
                var pathTracker = new StringBuilder();
                InRangeHandler.handle(this, field, errorSet, 2, 3,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 3,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 3, 4,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(0, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 1, 2,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(1, errorSet.size());
                InRangeHandler.handle(this, field, errorSet, 4, 120,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(2, errorSet.size());
            }
        }
    }
}