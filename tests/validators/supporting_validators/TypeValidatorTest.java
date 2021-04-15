package tests.validators.supporting_validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import solution.annotations.*;
import solution.validators.supporting_validators.TypeValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.security.InvalidParameterException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Type validator test")
public class TypeValidatorTest {

    @Nested
    @DisplayName("Correct field type test")
    public class CorrectFieldType {

        public Set<List> invalidSet1;
        public Set<Map> invalidSet2;
        public Set<List<Integer>> invalidSet3;
        public Set<Set<Long>> invalidSet4;
        public Set<LinkedList<String>> invalidSet5;

        @Test
        @DisplayName("Set test")
        void handleSet() {
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidSet1"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidSet2"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidSet3"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidSet4"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidSet5"));
            });
        }

        public Map<Map, Integer> invalidMap1;
        public Map<Integer, Map<String, Integer>> invalidMap2;
        public Map<List<Integer>, Long> invalidMap3;
        public Map<Map<String, String>, Long> invalidMap4;
        public Map<List, Byte> invalidMap5;
        public Map<Set, Integer> invalidMap6;

        @Test
        @DisplayName("Map test")
        void handleMap() {
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidMap1"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidMap2"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidMap3"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidMap4"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidMap5"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidMap6"));
            });
        }

        public Collection<Set> invalidCollection1;
        public Collection<List<String>> invalidCollection2;
        public Collection<Set<Integer>> invalidCollection3;
        public Collection<Map<String, String>> invalidCollection4;

        @Test
        @DisplayName("Collection test")
        void handleCollection() {
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidCollection1"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidCollection2"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidCollection3"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidCollection4"));
            });
        }

        public List<Set> invalidList1;
        public List<Map<Integer, Integer>> invalidList2;
        public List<Collection<Integer>> invalidList3;
        public List<List<Set<Integer>>> invalidList4;
        public List<List<List<Map<Integer, String>>>> invalidList5;
        public List<Collection> invalidList6;
        public List<Map> invalidList7;

        @Test
        @DisplayName("List test")
        void handleList() {
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidList1"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidList2"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidList3"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidList4"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidList5"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidList6"));
            });
            assertThrows(InvalidParameterException.class, () -> {
                TypeValidator.ensureCorrectFieldType(
                        CorrectFieldType.class.getField("invalidList7"));
            });
        }
    }

    @Nested
    @DisplayName("Type with annotations test")
    public class CorrectAnnotations {

        public @NotBlank Integer invalidInteger1;
        public @NotEmpty Integer invalidInteger2;
        public @AnyOf(value = {"123", "123"}) Integer invalidInteger3;
        public @Size(min=2, max=3) Integer invalidInteger4;

        @Test
        @DisplayName("Invalid annotation Integer test")
        void handleInvalidInteger() {
            assertThrows(InvalidParameterException.class, () -> {
                var field1 = CorrectAnnotations.class.getField("invalidInteger1");
                TypeValidator.ensureCorrespondence(field1.getAnnotatedType(),
                        new Annotation[]{field1.getAnnotatedType().getDeclaredAnnotations()[0]},
                        "");
            });
            assertThrows(InvalidParameterException.class, () -> {
                var field2 = CorrectAnnotations.class.getField("invalidInteger2");
                TypeValidator.ensureCorrespondence(field2.getAnnotatedType(),
                        new Annotation[]{field2.getAnnotatedType().getDeclaredAnnotations()[0]},
                        "");
            });
            assertThrows(InvalidParameterException.class, () -> {
                var field3 = CorrectAnnotations.class.getField("invalidInteger3");
                TypeValidator.ensureCorrespondence(field3.getAnnotatedType(),
                        new Annotation[]{field3.getAnnotatedType().getDeclaredAnnotations()[0]},
                        "");
            });
            assertThrows(InvalidParameterException.class, () -> {
                var field4 = CorrectAnnotations.class.getField("invalidInteger4");
                TypeValidator.ensureCorrespondence(field4.getAnnotatedType(),
                        new Annotation[]{field4.getAnnotatedType().getDeclaredAnnotations()[0]},
                        "");
            });
        }

        public @Positive List<Integer> invalidOuterList1;
        public @InRange(min=2, max=9) List<Integer> invalidOuterList2;
        public @NotBlank List<Integer> invalidOuterList3;
        public @Negative List<Integer> invalidOuterList4;

        @Test
        @DisplayName("Invalid annotation Collection test")
        void handleInvalidCollection() {
            assertThrows(InvalidParameterException.class, () -> {
                var field1 = CorrectAnnotations.class.getField("invalidOuterList1");
                TypeValidator.ensureCorrespondence(field1.getAnnotatedType(),
                        new Annotation[]{field1.getAnnotatedType().getDeclaredAnnotations()[0]},
                        "");
            });
            assertThrows(InvalidParameterException.class, () -> {
                var field2 = CorrectAnnotations.class.getField("invalidOuterList2");
                TypeValidator.ensureCorrespondence(field2.getAnnotatedType(),
                        new Annotation[]{field2.getAnnotatedType().getDeclaredAnnotations()[0]},
                        "");
            });
            assertThrows(InvalidParameterException.class, () -> {
                var field3 = CorrectAnnotations.class.getField("invalidOuterList3");
                TypeValidator.ensureCorrespondence(field3.getAnnotatedType(),
                        new Annotation[]{field3.getAnnotatedType().getDeclaredAnnotations()[0]},
                        "");
            });
            assertThrows(InvalidParameterException.class, () -> {
                var field4 = CorrectAnnotations.class.getField("invalidOuterList4");
                TypeValidator.ensureCorrespondence(field4.getAnnotatedType(),
                        new Annotation[]{field4.getAnnotatedType().getDeclaredAnnotations()[0]},
                        "");
            });
        }

        public List<List<@NotBlank Integer>> invalidInnerList1;
        public List<@Size(min=2, max=3) Long> invalidInnerList2;
        public List<List<@Positive String>> invalidInnerList3;

        @Test
        @DisplayName("Invalid inner annotations test")
        void handleInnerAnnotation() {
            assertThrows(InvalidParameterException.class, () -> {
                var field1 = CorrectAnnotations.class.getField("invalidInnerList1");
                TypeValidator.ensureCorrespondence(field1.getAnnotatedType(),
                        new Annotation[]{}, "");
            });
            assertThrows(InvalidParameterException.class, () -> {
                var field2 = CorrectAnnotations.class.getField("invalidInnerList2");
                TypeValidator.ensureCorrespondence(field2.getAnnotatedType(),
                        new Annotation[]{}, "");

            });
            assertThrows(InvalidParameterException.class, () -> {
                var field3 = CorrectAnnotations.class.getField("invalidInnerList3");
                TypeValidator.ensureCorrespondence(field3.getAnnotatedType(),
                        new Annotation[]{}, "");
            });
        }

        public List<@Size(min=22, max = 3) List<?>> invalidInnerList4;
        public List<@InRange(min=22, max=5) Integer> invalidInnerList5;

        @Test
        @DisplayName("Invalid bounds test")
        void handleBounds() {
            assertThrows(InvalidParameterException.class, () -> {
                var field1 = CorrectAnnotations.class.getField("invalidInnerList4");
                TypeValidator.ensureCorrespondence(field1.getAnnotatedType(),
                        new Annotation[]{}, "");
            });
            assertThrows(InvalidParameterException.class, () -> {
                var field2 = CorrectAnnotations.class.getField("invalidInnerList5");
                var annotation = ((AnnotatedParameterizedType) field2.getAnnotatedType()).
                        getAnnotatedActualTypeArguments()[0].getDeclaredAnnotations()[0];
                TypeValidator.ensureCorrespondence(field2.getAnnotatedType(),
                        new Annotation[]{annotation}, "");
            });
        }
    }
}