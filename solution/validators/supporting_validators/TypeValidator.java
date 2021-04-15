package solution.validators.supporting_validators;

import solution.utils.TypeChecker;
import solution.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Type validator.
 */
public class TypeValidator {

    /**
     * Special flag for checking nested lists.
     */
    private static boolean isParameterizedType = true;

    /**
     * Check if field type is collection of collections.
     *
     * @param field field
     */
    public static void ensureCorrectFieldType(Field field) {
        field.setAccessible(true);

        var fieldName = field.getType().getName();

        var fieldType = field.getAnnotatedType().getType();

        if (fieldType instanceof ParameterizedType) {
            var innerTypes = ((ParameterizedType) fieldType).getActualTypeArguments();

            if (TypeChecker.isMap(fieldName)) {
                processMap(innerTypes, fieldName);
            } else if (TypeChecker.isList(fieldName)) {
                processList(innerTypes[0], fieldName);
            } else if (TypeChecker.isCollection(fieldName)) {
                processCollection(innerTypes[0], fieldName);
            }
        }
    }

    /**
     * Check that annotations can be applied to field.
     * @param annotatedType annotated type of field
     * @param annotations annotations
     * @param fieldName field name
     */
    public static void ensureCorrespondence(AnnotatedType annotatedType,
                                            Annotation[] annotations, String fieldName) {

        if (TypeChecker.isNestedList(annotatedType.getType())) {
            processNestedList(annotatedType, fieldName);
        }

        var typeName = annotatedType.getType().getTypeName();
        for (var annotation : annotations) {
            if (annotation.annotationType() == Positive.class) {
                checkAnnotationsForNumber(typeName, fieldName, "Positive");
            }
            if (annotation.annotationType() == Negative.class) {
                checkAnnotationsForNumber(typeName, fieldName, "Negative");
            }
            if (annotation.annotationType() == NotBlank.class) {
                checkAnnotationsForString(typeName, fieldName, "NotBlank");
            }
            if (annotation.annotationType() == NotEmpty.class) {
                checkAnnotationsForCollection(typeName, fieldName, "NotEmpty");
            }
            if (annotation.annotationType() == Size.class) {
                checkAnnotationsForCollection(typeName, fieldName, "Size");
                var minSize = ((Size) annotation).min();
                var maxSize = ((Size) annotation).max();
                checkBounds(minSize, maxSize, "Size", fieldName);
            }
            if (annotation.annotationType() == InRange.class) {
                checkAnnotationsForNumber(typeName, fieldName, "InRange");
                var minValue = ((InRange) annotation).min();
                var maxValue = ((InRange) annotation).max();
                checkBounds(minValue, maxValue, "InRange", fieldName);
            }
            if (annotation.annotationType() == AnyOf.class) {
                checkAnnotationsForString(typeName, fieldName, "AnyOf");
            }
        }
    }

    /**
     * Ensure correspondence for nested lists.
     *
     * @param annotatedType annotated type
     * @param fieldName field name
     */
    private static void processNestedList(AnnotatedType annotatedType, String fieldName) {
        var innerType = ((AnnotatedParameterizedType) annotatedType).
                getAnnotatedActualTypeArguments()[0];
        ensureCorrespondence(innerType, innerType.getDeclaredAnnotations(), fieldName);
    }

    /**
     * Check that given annotation can be applied to collection types.
     *
     * @param fieldType field type
     * @param fieldName field name
     * @param annotation annotation
     */
    private static void checkAnnotationsForCollection(String fieldType, String fieldName,
                                                      String annotation) {
        if (!(TypeChecker.isList(fieldType) ||
                TypeChecker.isSet(fieldType) ||
                TypeChecker.isMap(fieldType) ||
                fieldType.equals("java.lang.String"))) {
            throw new InvalidParameterException("Field \"" + fieldName + "\" can't " +
                    "have \"@" + annotation + "\" annotation (may be, inner type)");
        }
    }

    /**
     * Check that min < max.
     *
     * @param min min value
     * @param max max value
     * @param annotation annotation
     * @param fieldName field name
     */
    private static void checkBounds(long min, long max, String annotation, String fieldName) {
        if (max < min) {
            throw new InvalidParameterException("Field \"" + fieldName + "\" has wrong bounds" +
                    " in annotation \" " + annotation + "\". max < min: " + max + " < " + min +
                    " (may be, inner type)");
        }
    }

    /**
     * Check that given annotation can be applied to String type.
     *
     * @param fieldType field type
     * @param fieldName field name
     * @param annotation annotation
     */
    private static void checkAnnotationsForString(String fieldType, String fieldName,
                                                  String annotation) {
        if (!fieldType.equals("java.lang.String")) {
            throw new InvalidParameterException("Field \"" + fieldName + "\" can't " +
                    "have \"@" + annotation + "\" annotation (may be, inner type)");
        }
    }

    /**
     * Check that given annotation can be applied to numeric types.
     *
     * @param fieldType field type
     * @param fieldName field name
     * @param annotation annotation
     */
    private static void checkAnnotationsForNumber(String fieldType, String fieldName,
                                                  String annotation) {
        if (Stream.of("Byte", "Short", "Integer", "Long").
                noneMatch(a -> ("java.lang." + a).equals(fieldType)) &&
                !List.of("byte", "short", "int", "long").contains(fieldType)) {
            throw new InvalidParameterException("Field \"" + fieldName + "\" can't " +
                    "have \"@" + annotation + "\" annotation (may be, inner type)");
        }
    }

    /**
     * Check inner types of Collection.
     *
     * @param innerType inner type
     * @param fieldName name of field (just for error message)
     */
    private static void processCollection(Type innerType, String fieldName) {
        if (TypeChecker.isCollectionOrMap(innerType.getTypeName())) {
            throw new InvalidParameterException("Invalid type of field \""
                    + fieldName + "\".\nYou can't create \"Collection\"/\"Set\"" +
                    " of collections (except \"List\" of lists)");
        }
    }

    /**
     * Check inner types of Map.
     *
     * @param innerTypes inner types
     * @param fieldName  name of field (just for error message)
     */
    private static void processMap(Type[] innerTypes, String fieldName) {
        for (var innerType : innerTypes) {
            if (TypeChecker.isCollectionOrMap(innerType.getTypeName())) {
                throw new InvalidParameterException("Invalid type of field \""
                        + fieldName + "\".\nYou can't create \"Map\" of collections");
            }
        }
    }

    /**
     * Check inner type of List.
     *
     * @param type      current type of byteList content.
     * @param fieldName name of field (just for error message)
     */
    private static void processList(Type type, String fieldName) {
        if (TypeChecker.isNestedList(type)) {
            processList(((ParameterizedType) type).getActualTypeArguments()[0],
                    fieldName);
        }
        var typeName = type.getTypeName();


        if (!isParameterizedType) {
            if (!TypeChecker.isList(typeName) && TypeChecker.isCollectionOrMap(typeName)) {
                throw new InvalidParameterException("Invalid type of field \""
                        + fieldName + "\".\n\"List\" can't contain other collections" +
                        " (except lists)");
            }

            return;
        }

        isParameterizedType = false;

        if (!TypeChecker.isList(typeName) && TypeChecker.isCollectionOrMap(typeName)) {
            throw new InvalidParameterException("Invalid type of field \""
                    + fieldName + "\".\n\"List\" can't contain other collections" +
                    " (except lists)");
        }
    }
}
