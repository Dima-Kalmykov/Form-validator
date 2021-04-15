package tests.validators.supporting_validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import solution.utils.ValueContainer;
import solution.validators.ErrorContent;
import solution.validators.ValidationError;
import solution.validators.supporting_validators.AnnotationValidator;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Annotation validator test")
public class AnnotationValidatorTest {

    public List<Integer> list;

    private boolean setContainsError(ValidationError expectedError) {
        for (var value : AnnotationValidator.getErrorSet()) {
            if (value.getPath().equals(expectedError.getPath()) &&
                    value.getFailedValue().toString().equals((
                            expectedError.getFailedValue()).toString()) &&
                    value.getMessage().equals(expectedError.getMessage())) {
                return true;
            }
        }

        return false;
    }

    @Test
    @DisplayName("Validate \"@NotNull\" test")
    void validateNotNull() throws NoSuchFieldException {
        var list = AnnotationValidatorTest.class.getField("list");
        var pathTracker = new StringBuilder("/list/");
        AnnotationValidator.validateNotNull(this, list,
                ValueContainer.FIELD, pathTracker);
        assertTrue(setContainsError(new ErrorContent(
                "must be not null", "/list/", "null")));
    }

    @Nested
    @DisplayName("Validate \"@Positive\" test")
    class Positive {

        public List<Integer> positiveList = List.of(1, 3, 2);
        public List<Integer> nullList = null;

        @Test
        @DisplayName("Null test")
        void NullTest() throws NoSuchFieldException {
            var positiveList = Positive.class.getField("positiveList");
            var nullList = Positive.class.getField("nullList");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validatePositive(null, positiveList,
                    ValueContainer.OBJECT, pathTracker);
            AnnotationValidator.validatePositive(this, nullList,
                    ValueContainer.FIELD, pathTracker);
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validatePositive(null, null,
                        ValueContainer.FIELD, pathTracker);
            });
        }

        public int negativeInt = -3;
        public Integer positiveInt = 2;

        public long positiveLong = 2;
        public Long negativeLong = -4L;

        public short positiveShort = 5;
        public Short negativeShort = -4;

        public byte negativeByte = -3;
        public Byte positiveByte = 4;

        public double negativeDouble = -2;
        public Double positiveDouble = 2D;

        @Test
        @DisplayName("Byte test")
        void testByte() throws NoSuchFieldException {
            var negativeByte = Positive.class.getField("negativeByte");
            var positiveByte = Positive.class.getField("positiveByte");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validatePositive(this, negativeByte,
                    ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validatePositive(this, positiveByte,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be positive", "", -3)));
            assertFalse(setContainsError(new ErrorContent(
                    "must be positive", "", 4)));
        }

        @Test
        @DisplayName("Long test")
        void testLong() throws NoSuchFieldException {
            var positiveLong = Positive.class.getField("positiveLong");
            var negativeLong = Positive.class.getField("negativeLong");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validatePositive(this, positiveLong,
                    ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validatePositive(this, negativeLong,
                    ValueContainer.FIELD, pathTracker);
            assertFalse(setContainsError(new ErrorContent(
                    "must be positive", "", 2)));
            assertTrue(setContainsError(new ErrorContent(
                    "must be positive", "", -4)));
        }

        @Test
        @DisplayName("Double test")
        void testDouble() throws NoSuchFieldException {
            var negativeDouble = Positive.class.getField("negativeDouble");
            var positiveDouble = Positive.class.getField("positiveDouble");
            var pathTracker = new StringBuilder();
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validatePositive(this, negativeDouble,
                        ValueContainer.FIELD, pathTracker);
            });
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validatePositive(this, positiveDouble,
                        ValueContainer.FIELD, pathTracker);
            });
        }

        @Test
        @DisplayName("Integer test")
        void testInt() throws NoSuchFieldException {
            var negativeInt = Positive.class.getField("negativeInt");
            var positiveInt = Positive.class.getField("positiveInt");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validatePositive(this, negativeInt,
                    ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validatePositive(this, positiveInt,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be positive", "", -3)));
            assertFalse(setContainsError(new ErrorContent(
                    "must be positive", "", 2)));
        }

        @Test
        @DisplayName("Short test")
        void testShort() throws NoSuchFieldException {
            var positiveShort = Positive.class.getField("positiveShort");
            var negativeShort = Positive.class.getField("negativeShort");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validatePositive(this, positiveShort,
                    ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validatePositive(this, negativeShort,
                    ValueContainer.FIELD, pathTracker);
            assertFalse(setContainsError(new ErrorContent(
                    "must be positive", "", 5)));
            assertTrue(setContainsError(new ErrorContent(
                    "must be positive", "", -4)));
        }
    }

    @Nested
    @DisplayName("Validate \"@Negative\" test")
    class Negative {

        public List<Integer> positiveList = List.of(1, 3, 2);
        public List<Integer> nullList = null;

        @Test
        @DisplayName("Null test")
        void NullTest() throws NoSuchFieldException {
            var positiveList = Negative.class.getField("positiveList");
            var nullList = Negative.class.getField("nullList");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNegative(null, positiveList,
                    ValueContainer.OBJECT, pathTracker);
            AnnotationValidator.validateNegative(this, nullList,
                    ValueContainer.FIELD, pathTracker);
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateNegative(null, null,
                        ValueContainer.FIELD, pathTracker);
            });
        }

        public int negativeInt = -3;
        public Integer positiveInt = 2;

        public long positiveLong = 2;
        public Long negativeLong = -4L;

        public short positiveShort = 5;
        public Short negativeShort = -4;

        public byte negativeByte = -3;
        public Byte positiveByte = 4;

        public double negativeDouble = -2;
        public Double positiveDouble = 2D;

        @Test
        @DisplayName("Byte test")
        void testByte() throws NoSuchFieldException {
            var negativeByte = Negative.class.getField("negativeByte");
            var positiveByte = Negative.class.getField("positiveByte");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNegative(this, negativeByte,
                    ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validateNegative(this, positiveByte,
                    ValueContainer.FIELD, pathTracker);
            assertFalse(setContainsError(new ErrorContent(
                    "must be negative", "", -3)));
            assertTrue(setContainsError(new ErrorContent(
                    "must be negative", "", 4)));
        }

        @Test
        @DisplayName("Long test")
        void testLong() throws NoSuchFieldException {
            var positiveLong = Negative.class.getField("positiveLong");
            var negativeLong = Negative.class.getField("negativeLong");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNegative(this, positiveLong,
                    ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validateNegative(this, negativeLong,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be negative", "", 2)));
            assertFalse(setContainsError(new ErrorContent(
                    "must be negative", "", -4)));
        }

        @Test
        @DisplayName("Double test")
        void testDouble() throws NoSuchFieldException {
            var negativeDouble = Negative.class.getField("negativeDouble");
            var positiveDouble = Negative.class.getField("positiveDouble");
            var pathTracker = new StringBuilder();
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateNegative(this, negativeDouble,
                        ValueContainer.FIELD, pathTracker);
            });
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateNegative(this, positiveDouble,
                        ValueContainer.FIELD, pathTracker);
            });
        }

        @Test
        @DisplayName("Integer test")
        void testInt() throws NoSuchFieldException {
            var negativeInt = Negative.class.getField("negativeInt");
            var positiveInt = Negative.class.getField("positiveInt");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNegative(this, negativeInt,
                    ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validateNegative(this, positiveInt,
                    ValueContainer.FIELD, pathTracker);
            assertFalse(setContainsError(new ErrorContent(
                    "must be negative", "", -3)));
            assertTrue(setContainsError(new ErrorContent(
                    "must be negative", "", 2)));
        }

        @Test
        @DisplayName("Short test")
        void testShort() throws NoSuchFieldException {
            var positiveShort = Negative.class.getField("positiveShort");
            var negativeShort = Negative.class.getField("negativeShort");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNegative(this, positiveShort,
                    ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validateNegative(this, negativeShort,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be negative", "", 5)));
            assertFalse(setContainsError(new ErrorContent(
                    "must be negative", "", -4)));
        }
    }

    @Nested
    @DisplayName("Validate \"@NotBlank\" test")
    class NotBlank {

        public List<Integer> positiveList = List.of(1, 3, 2);
        public List<Integer> nullList = null;

        @Test
        @DisplayName("Null test")
        void NullTest() throws NoSuchFieldException {
            var positiveList = NotBlank.class.getField("positiveList");
            var nullList = NotBlank.class.getField("nullList");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNotBlank(null, positiveList,
                    ValueContainer.OBJECT, pathTracker);
            AnnotationValidator.validateNotBlank(this, nullList,
                    ValueContainer.FIELD, pathTracker);
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateNotBlank(null, null,
                        ValueContainer.FIELD, pathTracker);
            });
        }

        public String emptyString = "";
        public Double doubleField = 4d;

        @Test
        @DisplayName("Not blank test")
        void notBlankTest() throws NoSuchFieldException {
            var emptyString = NotBlank.class.getField("emptyString");
            var doubleField = NotBlank.class.getField("doubleField");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNotBlank(this, emptyString,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be not blank", "", "\"\"")));
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateNotBlank(this, doubleField,
                        ValueContainer.FIELD, pathTracker);
            });
        }
    }

    @Nested
    @DisplayName("Validate \"@AnyOf\" test")
    class AnyOf {

        public List<Integer> positiveList = List.of(1, 3, 2);
        public List<Integer> nullList = null;

        @Test
        @DisplayName("Null test")
        void NullTest() throws NoSuchFieldException {
            var positiveList = AnyOf.class.getField("positiveList");
            var nullList = AnyOf.class.getField("nullList");
            var pathTracker = new StringBuilder();
            String[] values = {};
            AnnotationValidator.validateAnyOf(null, positiveList, values,
                    ValueContainer.OBJECT, pathTracker);
            AnnotationValidator.validateAnyOf(this, nullList, values,
                    ValueContainer.FIELD, pathTracker);
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateAnyOf(null, null, values,
                        ValueContainer.FIELD, pathTracker);
            });
        }

        public String emptyString = "";
        public Double doubleField = 4d;
        public String validString = "2";

        @Test
        @DisplayName("Any of test")
        void anyOfTest() throws NoSuchFieldException, IllegalAccessException {
            var emptyString = AnyOf.class.getField("emptyString");
            var doubleField = AnyOf.class.getField("doubleField");
            var validString = AnyOf.class.getField("validString");
            var pathTracker = new StringBuilder();
            String[] values = {"negativeInt", "2"};

            AnnotationValidator.validateAnyOf(this, validString, values,
                    ValueContainer.FIELD, pathTracker);
            assertFalse(setContainsError(new ErrorContent(
                    "must be one of \"negativeInt\", \"2\"", "", "\"\"")));

            AnnotationValidator.validateAnyOf(emptyString.get(this), emptyString, values,
                    ValueContainer.OBJECT, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be one of \"negativeInt\", \"2\"", "", "\"\"")));

            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateAnyOf(this, doubleField, values,
                        ValueContainer.FIELD, pathTracker);
            });
        }
    }

    @Nested
    @DisplayName("Validate \"@NotEmpty\" test")
    class NotEmpty {

        public List<Integer> positiveList = List.of(1, 3, 2);
        public List<Integer> nullList = null;

        @Test
        @DisplayName("Null test")
        void NullTest() throws NoSuchFieldException {
            var positiveList = NotEmpty.class.getField("positiveList");
            var nullList = NotEmpty.class.getField("nullList");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNotEmpty(null, positiveList,
                    ValueContainer.OBJECT, pathTracker);
            AnnotationValidator.validateNotEmpty(this, nullList,
                    ValueContainer.FIELD, pathTracker);
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateNotEmpty(null, null,
                        ValueContainer.FIELD, pathTracker);
            });
        }

        public List<Integer> list = List.of();
        public Set<Integer> set = Set.of();
        public Map<Integer, Integer> map = Map.of();
        public String emptyString = "";
        public int invalid = 3;

        @Test
        @DisplayName("List test")
        void testList() throws NoSuchFieldException {
            var list = NotEmpty.class.getField("list");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNotEmpty(this, list,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be not empty", "", List.of())));
        }

        @Test
        @DisplayName("Set test")
        void testSet() throws NoSuchFieldException {
            var set = NotEmpty.class.getField("set");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNotEmpty(this, set,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be not empty", "", Set.of())));
        }

        @Test
        @DisplayName("Map test")
        void testMap() throws NoSuchFieldException {
            var map = NotEmpty.class.getField("map");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNotEmpty(this, map,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be not empty", "", Map.of())));
        }

        @Test
        @DisplayName("String test")
        void testString() throws NoSuchFieldException {
            var string = NotEmpty.class.getField("emptyString");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateNotEmpty(this, string,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "must be not empty", "", "")));
        }

        @Test
        @DisplayName("Invalid test")
        void invalidTest() throws NoSuchFieldException {
            var invalid = NotEmpty.class.getField("invalid");
            var pathTracker = new StringBuilder();
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateNotEmpty(this, invalid,
                        ValueContainer.FIELD, pathTracker);
            });
        }
    }

    @Nested
    @DisplayName("Validate \"@Size\" test")
    class Size {

        public List<Integer> positiveList = List.of(1, 3, 2);
        public List<Integer> nullList = null;

        @Test
        @DisplayName("Null test")
        void NullTest() throws NoSuchFieldException {
            var positiveList = Size.class.getField("positiveList");
            var nullList = Size.class.getField("nullList");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateSize(null, positiveList,
                    1, 2, ValueContainer.OBJECT, pathTracker);
            AnnotationValidator.validateSize(this, nullList,
                    1, 2, ValueContainer.FIELD, pathTracker);
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateSize(null, null,
                        1, 2, ValueContainer.FIELD, pathTracker);
            });
        }

        public List<Integer> list = List.of();
        public Set<Integer> set = Set.of();
        public Map<Integer, Integer> map = Map.of();
        public String emptyString = "";
        public int invalid = 3;

        @Test
        @DisplayName("List test")
        void testList() throws NoSuchFieldException {
            var list = Size.class.getField("list");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateSize(this, list, 1, 2,
                    ValueContainer.FIELD, pathTracker);

            assertTrue(setContainsError(new ErrorContent(
                    "size must be in range between 1 and 2", "", List.of())));
        }

        @Test
        @DisplayName("Set test")
        void testSet() throws NoSuchFieldException {
            var set = Size.class.getField("set");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateSize(this, set, 1, 2,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "size must be in range between 1 and 2", "", Set.of())));
        }

        @Test
        @DisplayName("Map test")
        void testMap() throws NoSuchFieldException {
            var map = Size.class.getField("map");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateSize(this, map, 1, 2,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "size must be in range between 1 and 2", "", Map.of())));
        }

        @Test
        @DisplayName("String test")
        void testString() throws NoSuchFieldException {
            var emptyString = Size.class.getField("emptyString");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateSize(this, emptyString, 1, 2,
                    ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "size must be in range between 1 and 2", "", "")));
        }

        @Test
        @DisplayName("Invalid test")
        void invalidTest() throws NoSuchFieldException {
            var invalid = Size.class.getField("invalid");
            var pathTracker = new StringBuilder();
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateSize(this, invalid,
                        1, 2, ValueContainer.FIELD, pathTracker);
            });
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateSize(this, invalid,
                        2, 1, ValueContainer.FIELD, pathTracker);
            });
        }
    }

    @Nested
    @DisplayName("Validate \"@InRange\" test")
    class InRange {

        public List<Integer> positiveList = List.of(1, 3, 2);
        public List<Integer> nullList = null;

        @Test
        @DisplayName("Null test")
        void NullTest() throws NoSuchFieldException {
            var positiveList = InRange.class.getField("positiveList");
            var nullList = InRange.class.getField("nullList");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateInRange(null, positiveList,
                    1, 2, ValueContainer.OBJECT, pathTracker);
            AnnotationValidator.validateInRange(this, nullList,
                    1, 2, ValueContainer.FIELD, pathTracker);
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateInRange(null, null,
                        1, 2, ValueContainer.FIELD, pathTracker);
            });
        }

        public int negativeInt = -3;
        public Integer positiveInt = 2;

        public long positiveLong = 2;
        public Long negativeLong = -4L;

        public short positiveShort = 5;
        public Short negativeShort = -4;

        public byte negativeByte = -3;
        public Byte positiveByte = 4;

        public double negativeDouble = -2;
        public Double positiveDouble = 2D;

        public List<Integer> emptyList = List.of();

        @Test
        @DisplayName("Byte test")
        void testByte() throws NoSuchFieldException {
            var negativeByte = InRange.class.getField("negativeByte");
            var positiveByte = InRange.class.getField("positiveByte");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateInRange(this, negativeByte,
                    10, 12, ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validateInRange(this, positiveByte,
                    10, 12, ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "value must be in range between 10 and 12", "", -3)));
            assertTrue(setContainsError(new ErrorContent(
                    "value must be in range between 10 and 12", "", 4)));
        }

        @Test
        @DisplayName("Long test")
        void testLong() throws NoSuchFieldException {
            var positiveLong = InRange.class.getField("positiveLong");
            var negativeLong = InRange.class.getField("negativeLong");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateInRange(this, positiveLong,
                    10, 12, ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validateInRange(this, negativeLong,
                    10, 12, ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "value must be in range between 10 and 12", "", 2)));
            assertTrue(setContainsError(new ErrorContent(
                    "value must be in range between 10 and 12", "", -4)));
        }

        @Test
        @DisplayName("Invalid values")
        void testInvalidValues() throws NoSuchFieldException {
            var negativeDouble = InRange.class.getField("negativeDouble");
            var positiveDouble = InRange.class.getField("positiveDouble");
            var emptyList = InRange.class.getField("emptyList");
            var pathTracker = new StringBuilder();
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateInRange(this, negativeDouble,
                        10, 12, ValueContainer.FIELD, pathTracker);
            });
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateInRange(this, emptyList,
                        10, 12, ValueContainer.FIELD, pathTracker);
            });
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateInRange(this, positiveDouble,
                        10, 12, ValueContainer.FIELD, pathTracker);
            });
            assertThrows(InvalidParameterException.class, () -> {
                AnnotationValidator.validateInRange(this, positiveDouble,
                        12, 10, ValueContainer.FIELD, pathTracker);
            });
        }

        @Test
        @DisplayName("Integer test")
        void testInt() throws NoSuchFieldException {
            var negativeInt = InRange.class.getField("negativeInt");
            var positiveInt = InRange.class.getField("positiveInt");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateInRange(this, negativeInt,
                    10, 12, ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validateInRange(this, positiveInt,
                    10, 12, ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "value must be in range between 10 and 12", "", -3)));
            assertTrue(setContainsError(new ErrorContent(
                    "value must be in range between 10 and 12", "", 2)));
        }

        @Test
        @DisplayName("Short test")
        void testShort() throws NoSuchFieldException {
            var positiveShort = InRange.class.getField("positiveShort");
            var negativeShort = InRange.class.getField("negativeShort");
            var pathTracker = new StringBuilder();
            AnnotationValidator.validateInRange(this, positiveShort,
                    10, 12, ValueContainer.FIELD, pathTracker);
            AnnotationValidator.validateInRange(this, negativeShort,
                    10, 12, ValueContainer.FIELD, pathTracker);
            assertTrue(setContainsError(new ErrorContent(
                    "value must be in range between 10 and 12", "", 5)));
            assertTrue(setContainsError(new ErrorContent(
                    "value must be in range between 10 and 12", "", -4)));
        }
    }
}