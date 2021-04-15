package tests.annotation_handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import solution.annotation_handlers.NegativeHandler;
import solution.utils.ValueContainer;
import solution.utils.ValueType;
import solution.validators.ValidationError;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("\"@Negative\" handler test")
public class NegativeHandlerTest {

    public List<Integer> invalidList = List.of(1, 2);

    @Test
    @DisplayName("Invalid type test")
    void handleInvalidType() throws NoSuchFieldException, IllegalAccessException {
        Set<ValidationError> errorSet = new HashSet<>();
        var field = NegativeHandlerTest.class.getField("invalidList");
        var pathTracker = new StringBuilder();
        var list = (List<?>) field.get(this);

        assertThrows(InvalidParameterException.class, () -> {
            NegativeHandler.handle(list.get(0), field, errorSet,
                    ValueContainer.OBJECT, ValueType.MAP, pathTracker);
        });

        assertThrows(InvalidParameterException.class, () -> {
            NegativeHandler.handle(list.get(0), field, errorSet,
                    ValueContainer.OBJECT, ValueType.LIST, pathTracker);
        });

        assertThrows(InvalidParameterException.class, () -> {
            NegativeHandler.handle(list.get(0), field, errorSet,
                    ValueContainer.OBJECT, ValueType.SET, pathTracker);
        });

        assertThrows(InvalidParameterException.class, () -> {
            NegativeHandler.handle(list.get(0), field, errorSet,
                    ValueContainer.OBJECT, ValueType.STRING, pathTracker);
        });
    }


    @Nested
    @DisplayName("Handle value test (object)")
    class ObjectTest {

        public List<Byte> byteList = List.of((byte) 1, (byte) -2, (byte) -3);
        public List<Short> shortList = List.of((short) 1, (short) -2, (short) -3);
        public List<Integer> integerList = List.of(1, -2, -3);
        public List<Long> longList = List.of(1L, -2L, -3L);

        @Test
        @DisplayName("Handle byte test")
        void handleByte() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var field = ObjectTest.class.getField("byteList");
            var pathTracker = new StringBuilder();
            var list = (List<?>) field.get(this);
            NegativeHandler.handle(list.get(0), field, errorSet,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
            assertEquals(1, errorSet.size());
            NegativeHandler.handle(list.get(1), field, errorSet,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
            assertEquals(1, errorSet.size());
            NegativeHandler.handle(list.get(2), field, errorSet,
                    ValueContainer.OBJECT, ValueType.BYTE, pathTracker);
            assertEquals(1, errorSet.size());
        }

        @Test
        @DisplayName("Handle short test")
        void handleShort() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var field = ObjectTest.class.getField("shortList");
            var pathTracker = new StringBuilder();
            var list = (List<?>) field.get(this);
            NegativeHandler.handle(list.get(0), field, errorSet,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
            assertEquals(1, errorSet.size());
            NegativeHandler.handle(list.get(1), field, errorSet,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
            assertEquals(1, errorSet.size());
            NegativeHandler.handle(list.get(2), field, errorSet,
                    ValueContainer.OBJECT, ValueType.SHORT, pathTracker);
            assertEquals(1, errorSet.size());
        }

        @Test
        @DisplayName("Handle integer test")
        void handleInteger() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var field = ObjectTest.class.getField("integerList");
            var pathTracker = new StringBuilder();
            var list = (List<?>) field.get(this);
            NegativeHandler.handle(list.get(0), field, errorSet,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
            assertEquals(1, errorSet.size());
            NegativeHandler.handle(list.get(1), field, errorSet,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
            assertEquals(1, errorSet.size());
            NegativeHandler.handle(list.get(2), field, errorSet,
                    ValueContainer.OBJECT, ValueType.INTEGER, pathTracker);
            assertEquals(1, errorSet.size());
        }

        @Test
        @DisplayName("Handle long test")
        void handleLong() throws NoSuchFieldException, IllegalAccessException {
            Set<ValidationError> errorSet = new HashSet<>();
            var field = ObjectTest.class.getField("longList");
            var pathTracker = new StringBuilder();
            var list = (List<?>) field.get(this);
            NegativeHandler.handle(list.get(0), field, errorSet,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
            assertEquals(1, errorSet.size());
            NegativeHandler.handle(list.get(1), field, errorSet,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
            assertEquals(1, errorSet.size());
            NegativeHandler.handle(list.get(2), field, errorSet,
                    ValueContainer.OBJECT, ValueType.LONG, pathTracker);
            assertEquals(1, errorSet.size());
        }
    }

    @Nested
    @DisplayName("Handle value test (field)")
    public class FieldTest {

        @Nested
        @DisplayName("Byte test")
        public class ByteTest {

            public byte byteField = 3;
            public byte negativeByteField = -3;
            public Byte byteFieldWrapper = 3;
            public Byte negativeByteWrapper = -3;

            @Test
            @DisplayName("Handle byte test")
            void handleByte() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var positive = ByteTest.class.getField("byteField");
                var negative = ByteTest.class.getField("negativeByteField");
                var pathTracker = new StringBuilder();
                NegativeHandler.handle(this, positive, errorSet,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(1, errorSet.size());
                NegativeHandler.handle(this, negative, errorSet,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(1, errorSet.size());
            }

            @Test
            @DisplayName("Handle byte wrapper test")
            void handleByteWrapper() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var positive = ByteTest.class.getField("byteFieldWrapper");
                var negative = ByteTest.class.getField("negativeByteWrapper");
                var pathTracker = new StringBuilder();
                NegativeHandler.handle(this, positive, errorSet,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(1, errorSet.size());
                NegativeHandler.handle(this, negative, errorSet,
                        ValueContainer.FIELD, ValueType.BYTE, pathTracker);
                assertEquals(1, errorSet.size());
            }
        }

        @Nested
        @DisplayName("Short test")
        public class ShortTest {

            public short shortField = 3;
            public short negativeShortField = -3;
            public Short shortFieldWrapper = 3;
            public Short negativeShortWrapper = -3;

            @Test
            @DisplayName("Handle short test")
            void handleShort() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var positive = ShortTest.class.getField("shortField");
                var negative = ShortTest.class.getField("negativeShortField");
                var pathTracker = new StringBuilder();
                NegativeHandler.handle(this, positive, errorSet,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(1, errorSet.size());
                NegativeHandler.handle(this, negative, errorSet,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(1, errorSet.size());
            }

            @Test
            @DisplayName("Handle short wrapper test")
            void handleShortWrapper() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var positive = ShortTest.class.getField("shortFieldWrapper");
                var negative = ShortTest.class.getField("negativeShortWrapper");
                var pathTracker = new StringBuilder();
                NegativeHandler.handle(this, positive, errorSet,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(1, errorSet.size());
                NegativeHandler.handle(this, negative, errorSet,
                        ValueContainer.FIELD, ValueType.SHORT, pathTracker);
                assertEquals(1, errorSet.size());
            }
        }

        @Nested
        @DisplayName("Integer test")
        public class IntegerTest {

            public int integerField = 3;
            public int negativeIntegerField = -3;
            public Integer integerFieldWrapper = 3;
            public Integer negativeIntegerWrapper = -3;

            @Test
            @DisplayName("Handle integer test")
            void handleInteger() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var positive = IntegerTest.class.getField("integerField");
                var negative = IntegerTest.class.getField("negativeIntegerField");
                var pathTracker = new StringBuilder();
                NegativeHandler.handle(this, positive, errorSet,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(1, errorSet.size());
                NegativeHandler.handle(this, negative, errorSet,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(1, errorSet.size());
            }

            @Test
            @DisplayName("Handle integer wrapper test")
            void handleIntegerWrapper() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var positive = IntegerTest.class.getField("integerFieldWrapper");
                var negative = IntegerTest.class.getField("negativeIntegerWrapper");
                var pathTracker = new StringBuilder();
                NegativeHandler.handle(this, positive, errorSet,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(1, errorSet.size());
                NegativeHandler.handle(this, negative, errorSet,
                        ValueContainer.FIELD, ValueType.INTEGER, pathTracker);
                assertEquals(1, errorSet.size());
            }
        }

        @Nested
        @DisplayName("Long test")
        public class LongTest {

            public long longField = 3;
            public long negativeLongField = -3;
            public Long longFieldWrapper = 3L;
            public Long negativeLongWrapper = -3L;

            @Test
            @DisplayName("Handle long test")
            void handleLong() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var positive = LongTest.class.getField("longField");
                var negative = LongTest.class.getField("negativeLongField");
                var pathTracker = new StringBuilder();
                NegativeHandler.handle(this, positive, errorSet,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(1, errorSet.size());
                NegativeHandler.handle(this, negative, errorSet,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(1, errorSet.size());
            }

            @Test
            @DisplayName("Handle long wrapper test")
            void handleLongWrapper() throws NoSuchFieldException {
                Set<ValidationError> errorSet = new HashSet<>();
                var positive = LongTest.class.getField("longFieldWrapper");
                var negative = LongTest.class.getField("negativeLongWrapper");
                var pathTracker = new StringBuilder();
                NegativeHandler.handle(this, positive, errorSet,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(1, errorSet.size());
                NegativeHandler.handle(this, negative, errorSet,
                        ValueContainer.FIELD, ValueType.LONG, pathTracker);
                assertEquals(1, errorSet.size());
            }
        }
    }
}