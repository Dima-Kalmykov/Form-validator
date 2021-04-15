package solution.validators;

import solution.utils.TypeChecker;
import solution.utils.ValueContainer;
import solution.annotations.*;
import solution.validators.supporting_validators.AnnotationValidator;
import solution.validators.supporting_validators.TypeValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main validator.
 */
public class ObjectValidator implements Validator {

    /**
     * Validate object.
     *
     * @param object object
     * @return set of errors. For more information check {@link ValidationError}
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        return validateWithPathTracker(object, new StringBuilder("/"));
    }

    /**
     * Validate object and track path.
     *
     * @param object      object
     * @param pathTracker path tracker
     * @return set of errors. For more information check {@link ValidationError}
     */
    private Set<ValidationError> validateWithPathTracker(Object object,
                                                         StringBuilder pathTracker) {
        if (object != null) {
            if (hasSingleConstrainedAnnotation(object)) {
                try {
                    handleFields(object, pathTracker);
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        }

        return AnnotationValidator.getErrorSet();
    }

    /**
     * Add content to path tracker.
     *
     * @param pathTracker path tracker
     * @param content     content
     */
    private void addToPathTracker(StringBuilder pathTracker, String content) {
        pathTracker.append(content).append("/");
    }

    /**
     * Remove content from path tracker.
     *
     * @param pathTracker path tracker
     * @param content     content
     */
    private void removeFromPathTracker(StringBuilder pathTracker, String content) {
        var currentPath = pathTracker.substring(0, pathTracker.length() - 1);
        var startIndex = Math.max(0, currentPath.lastIndexOf("/"));
        var endIndex = startIndex + content.length() + 1;

        pathTracker.delete(startIndex, endIndex);
    }

    /**
     * Validate fields of object.
     *
     * @param object      object
     * @param pathTracker path tracker
     * @throws IllegalAccessException is thrown when something is going wrong with reflection access.
     */
    private void handleFields(Object object,
                              StringBuilder pathTracker) throws IllegalAccessException {
        var fields = object.getClass().getDeclaredFields();
        for (var field : fields) {
            TypeValidator.ensureCorrectFieldType(field);

            addToPathTracker(pathTracker, field.getName());

            if (TypeChecker.isCustomClass(field.getAnnotatedType().getType().getTypeName())) {
                validateWithPathTracker(field.get(object), pathTracker);
            }

            handleFieldAnnotations(field, field.getAnnotatedType().getDeclaredAnnotations(),
                    object, pathTracker);

            removeFromPathTracker(pathTracker, field.getName());
        }
    }

    /**
     * Handle annotation.
     *
     * @param object      object
     * @param field       field
     * @param annotation  annotation
     * @param container   value container. For more information check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    private void handleAnnotation(Object object, Field field, Annotation annotation,
                                  ValueContainer container, StringBuilder pathTracker) {
        var annotationType = annotation.annotationType();

        if (annotationType == Positive.class) {
            AnnotationValidator.validatePositive(object, field, container, pathTracker);
        }

        if (annotationType == Negative.class) {
            AnnotationValidator.validateNegative(object, field, container, pathTracker);
        }

        if (annotationType == NotNull.class) {
            AnnotationValidator.validateNotNull(object, field, container, pathTracker);
        }

        if (annotationType == NotBlank.class) {
            AnnotationValidator.validateNotBlank(object, field, container, pathTracker);
        }

        if (annotationType == NotEmpty.class) {
            AnnotationValidator.validateNotEmpty(object, field, container, pathTracker);
        }

        if (annotationType == Size.class) {
            var minSize = ((Size) annotation).min();
            var maxSize = ((Size) annotation).max();
            AnnotationValidator.validateSize(object, field, minSize, maxSize,
                    container, pathTracker);
        }

        if (annotationType == InRange.class) {
            var minValue = ((InRange) annotation).min();
            var maxValue = ((InRange) annotation).max();
            AnnotationValidator.validateInRange(object, field, minValue, maxValue,
                    container, pathTracker);
        }

        if (annotationType == AnyOf.class) {
            var values = ((AnyOf) annotation).value();
            AnnotationValidator.validateAnyOf(object, field, values,
                    container, pathTracker);
        }
    }

    /**
     * Handle Map.
     *
     * @param object      object
     * @param field       field
     * @param pathTracker path tracker
     */
    private void handleMap(Object object, Field field, StringBuilder pathTracker) {
        Map<?, ?> map = null;

        var annotations = ((AnnotatedParameterizedType) field.getAnnotatedType())
                .getAnnotatedActualTypeArguments();

        var keyAnnotations = annotations[0].getDeclaredAnnotations();
        ensureAnnotationsCapability(keyAnnotations, annotations[0], field);

        var valueAnnotations = annotations[1].getDeclaredAnnotations();
        ensureAnnotationsCapability(valueAnnotations, annotations[1], field);

        try {
            map = (Map<?, ?>) field.get(object);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }

        if (map == null) {
            return;
        }

        handleMapContent(map.keySet(), field, keyAnnotations,
                pathTracker, "key");
        handleMapContent(map.values(), field, valueAnnotations,
                pathTracker, "value");

        handleNestedCustomClasses(map.keySet(), pathTracker, "key");
        handleNestedCustomClasses(map.values(), pathTracker, "value");
    }

    /**
     * Handle map content.
     *
     * @param values      map
     * @param field       field
     * @param annotations annotations
     * @param pathTracker path tracker
     * @param contentKind keys or values
     */
    private void handleMapContent(Collection<?> values, Field field,
                                  Annotation[] annotations,
                                  StringBuilder pathTracker, String contentKind) {
        for (var value : values) {
            addToPathTracker(pathTracker, "[some " + contentKind + " index]");
            for (var annotation : annotations) {
                handleAnnotation(value, field, annotation, ValueContainer.OBJECT, pathTracker);
            }
            removeFromPathTracker(pathTracker, "[some " + contentKind + " index]");
        }
    }

    /**
     * Handle nested classes.
     *
     * @param collection  collection for processing
     * @param pathTracker path tracker
     */
    private void handleNestedCustomClasses(Collection<?> collection, StringBuilder pathTracker,
                                           String mapContent) {
        int currentIndex = 0;
        var collectionTypeName = collection.getClass().getName();
        for (var value : collection) {
            if (value == null) {
                continue;
            }
            if (TypeChecker.isCustomClass(value.getClass().getName())) {
                String content = "[some index]";
                if (TypeChecker.isList(collectionTypeName)) {
                    content = "[" + currentIndex + "]";
                }
                else if (TypeChecker.isMap(collectionTypeName)) {
                    content = "[some " + mapContent + " index]";
                }
                addToPathTracker(pathTracker, content);

                validateWithPathTracker(value, pathTracker);
                removeFromPathTracker(pathTracker, content);

            }
            ++currentIndex;
        }
    }

    /**
     * Handle Collection.
     *
     * @param object      object
     * @param field       field
     * @param pathTracker path tracker
     */
    private void handleCollection(Object object, Field field, StringBuilder pathTracker) {
        Collection<?> collection = null;
        var annotations = ((AnnotatedParameterizedType) field.getAnnotatedType())
                .getAnnotatedActualTypeArguments()[0].getDeclaredAnnotations();

        ensureAnnotationsCapability(annotations,
                ((AnnotatedParameterizedType) field.getAnnotatedType())
                .getAnnotatedActualTypeArguments()[0], field);

        try {
            collection = (Collection<?>) field.get(object);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }

        if (collection == null) {
            return;
        }

        for (var value : collection) {
            addToPathTracker(pathTracker, "[some index]");
            for (var annotation : annotations) {
                handleAnnotation(value, field, annotation, ValueContainer.OBJECT, pathTracker);
            }
            removeFromPathTracker(pathTracker, "[some index]");
        }

        handleNestedCustomClasses(collection, pathTracker, "");
    }

    /**
     * Handle List.
     *
     * @param object        object
     * @param field         field
     * @param annotatedType annotated type
     * @param pathTracker   path tracker
     */
    private void handleList(Object object, Field field, AnnotatedType annotatedType,
                            StringBuilder pathTracker) {
        var list = (List<?>) object;
        var annotations = ((AnnotatedParameterizedType) annotatedType)
                .getAnnotatedActualTypeArguments()[0].getDeclaredAnnotations();

        var nestedType = ((AnnotatedParameterizedType) annotatedType)
                .getAnnotatedActualTypeArguments()[0];
        ensureAnnotationsCapability(annotations, nestedType, field);

        if (list == null) {
            return;
        }

        int currentIndex = 0;
        for (var value : list) {
            addToPathTracker(pathTracker, "[" + currentIndex + "]");

            for (var annotation : annotations) {
                handleAnnotation(value, field, annotation,
                        ValueContainer.OBJECT, pathTracker);
            }
            removeFromPathTracker(pathTracker, "[" + currentIndex + "]");
            ++currentIndex;
        }

        handleNestedCustomClasses(list, pathTracker, "");
        handleNestedLists(list, field, annotatedType, pathTracker);
    }

    /**
     * Handle nested lists.
     *
     * @param list          byteList
     * @param field         field
     * @param annotatedType annotated type
     * @param pathTracker   path tracker
     */
    private void handleNestedLists(List<?> list, Field field, AnnotatedType annotatedType,
                                   StringBuilder pathTracker) {
        int currentIndex = 0;
        for (var value : list) {
            addToPathTracker(pathTracker, "[" + currentIndex + "]");

            var inner = ((AnnotatedParameterizedType) annotatedType).
                    getAnnotatedActualTypeArguments()[0].getType().getTypeName();

            if (TypeChecker.isList(inner)) {
                handleList(value, field, ((AnnotatedParameterizedType) annotatedType).
                        getAnnotatedActualTypeArguments()[0], pathTracker);
            } else {
                removeFromPathTracker(pathTracker, "[" + currentIndex + "]");
                return;
            }

            removeFromPathTracker(pathTracker, "[" + currentIndex + "]");

            ++currentIndex;
        }
    }

    /**
     * Handle field annotations.
     *
     * @param field            field
     * @param fieldAnnotations annotations of field
     * @param object           object
     * @param pathTracker      path tracker
     */
    private void handleFieldAnnotations(Field field, Annotation[] fieldAnnotations,
                                        Object object, StringBuilder pathTracker) {
        if (field == null) {
            throw new InvalidParameterException();
        }

        ensureAnnotationsCapability(fieldAnnotations, field.getAnnotatedType(), field);

        for (var annotation : fieldAnnotations) {
            handleAnnotation(object, field, annotation,
                    ValueContainer.FIELD, pathTracker);
        }

        if (TypeChecker.isParameterizedList(field)) {
            try {
                handleList(field.get(object), field, field.getAnnotatedType(),
                        pathTracker);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        } else if (TypeChecker.isParameterizedMap(field)) {
            handleMap(object, field, pathTracker);
        } else if (TypeChecker.isParameterizedCollection(field)) {
            handleCollection(object, field, pathTracker);
        }
    }

    /**
     * Check capability of annotations.
     *
     * @param annotations array of annotations.
     * @param type        filed name (just for error message)
     */
    private static void ensureAnnotationsCapability(Annotation[] annotations,
                                                    AnnotatedType type, Field field) {

        var fieldName = type.getType().getTypeName();
        var annotationNames = Arrays.stream(annotations)
                .map(a -> a.annotationType().getName())
                .collect(Collectors.toList());

        TypeValidator.ensureCorrespondence(type, annotations, field.getName());

        if (annotationNames.contains("solution.annotations.Positive") &&
                annotationNames.contains("solution.annotations.Negative")) {
            throw new InvalidParameterException("Field \"" + fieldName + "\"" +
                    " has \"Positive\" and \"Negative\" annotations at the same time");
        }
    }

    /**
     * Check that the object has negativeInt single annotation - "@Constrained".
     *
     * @param object object
     * @return true if object has negativeInt single "@Constrained" annotation, false - otherwise
     */
    private boolean hasSingleConstrainedAnnotation(Object object) {
        if (object.getClass().isAnnotationPresent(Constrained.class)) {
            if (object.getClass().getDeclaredAnnotations().length == 1) {
                return true;
            }

            throw new InvalidParameterException("Invalid annotation class");
        }

        return false;
    }
}
