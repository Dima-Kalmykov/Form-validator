package solution.validators.supporting_validators;

import solution.utils.TypeChecker;
import solution.annotation_handlers.*;
import solution.validators.ValidationError;
import solution.utils.ValueContainer;
import solution.utils.ValueType;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

/**
 * General annotation handler.
 */
public class AnnotationValidator {

    /**
     * Set with errors. for more info check {@link ValidationError}.
     */
    private static final Set<ValidationError> errorSet = new HashSet<>();

    /**
     * Return error set.
     * @return error set
     */
    public static Set<ValidationError> getErrorSet() {
        return errorSet;
    }

    /**
     * Validate value with "@NotNull" annotation.
     *
     * @param object object
     * @param field field
     * @param container value container. For more info check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void validateNotNull(Object object, Field field, ValueContainer container,
                                       StringBuilder pathTracker) {
        NotNullHandler.handle(object, field, errorSet, container, pathTracker);
    }

    /**
     * Validate value with "@Positive" annotation.
     *
     * @param object object
     * @param field field
     * @param container value container. For more info check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void validatePositive(Object object, Field field, ValueContainer container,
                                        StringBuilder pathTracker) {
        if (field == null) {
            throw new InvalidParameterException("Field was null");
        }

        field.setAccessible(true);

        if (isNullValue(object, field, container)) {
            return;
        }

        String typeName = getTypeName(object, field, container);

        switch (typeName) {
            case "java.lang.Byte":
            case "byte":
                PositiveHandler.handle(object, field, errorSet, container,
                        ValueType.BYTE, pathTracker);
                return;

            case "java.lang.Short":
            case "short":
                PositiveHandler.handle(object, field, errorSet, container,
                        ValueType.SHORT, pathTracker);
                return;

            case "java.lang.Integer":
            case "int":
                PositiveHandler.handle(object, field, errorSet, container,
                        ValueType.INTEGER, pathTracker);
                return;

            case "java.lang.Long":
            case "long":
                PositiveHandler.handle(object, field, errorSet, container,
                        ValueType.LONG, pathTracker);
                return;
        }

        throw new InvalidParameterException("Invalid type of field \"" +
                field.getName() + "\" with " +
                "\"@Positive\" annotation  (may be, inner type)");
    }

    /**
     * Validate value with "@Negative" annotation.
     *
     * @param object object
     * @param field field
     * @param container value container. For more info check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void validateNegative(Object object, Field field, ValueContainer container,
                                        StringBuilder pathTracker) {
        if (field == null) {
            throw new InvalidParameterException("Field was null");
        }

        field.setAccessible(true);

        if (isNullValue(object, field, container)) {
            return;
        }

        String typeName = getTypeName(object, field, container);

        switch (typeName) {
            case "java.lang.Byte":
            case "byte":
                NegativeHandler.handle(object, field, errorSet, container,
                        ValueType.BYTE, pathTracker);
                return;

            case "java.lang.Short":
            case "short":
                NegativeHandler.handle(object, field, errorSet, container,
                        ValueType.SHORT, pathTracker);
                return;

            case "java.lang.Integer":
            case "int":
                NegativeHandler.handle(object, field, errorSet, container,
                        ValueType.INTEGER, pathTracker);
                return;

            case "java.lang.Long":
            case "long":
                NegativeHandler.handle(object, field, errorSet, container,
                        ValueType.LONG, pathTracker);
                return;
        }

        throw new InvalidParameterException("Invalid type of field \"" +
                field.getName() + "\" with " +
                "\"@Negative\" annotation (may be, inner type)");
    }

    /**
     * Validate value with "@NotBlank" annotation.
     *
     * @param object object
     * @param field field
     * @param container value container. For more info check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void validateNotBlank(Object object, Field field, ValueContainer container,
                                        StringBuilder pathTracker) {
        if (field == null) {
            throw new InvalidParameterException("Field was null");
        }

        field.setAccessible(true);

        if (isNullValue(object, field, container)) {
            return;
        }
        String typeName = getTypeName(object, field, container);

        if (!(typeName.equals("java.lang.String"))) {
            throw new InvalidParameterException("Field \"" + field.getName() +
                    "\" must has negativeInt type \"String\"" +
                    ", because there is \"@NotBlank\" annotation (may be, inner type)");
        }

        NotBlankHandler.handle(object, field, errorSet, container, pathTracker);
    }

    /**
     * Validate value with "@AnyOf" annotation.
     *
     * @param object object
     * @param field field
     * @param values possible values for given field value
     * @param container value container. For more info check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void validateAnyOf(Object object, Field field, String[] values,
                                     ValueContainer container, StringBuilder pathTracker) {
        if (field == null) {
            throw new InvalidParameterException("Field was null");
        }
        field.setAccessible(true);

        if (isNullValue(object, field, container)) {
            return;
        }

        String typeName = getTypeName(object, field, container);

        if (!(typeName.equals("java.lang.String"))) {
            throw new InvalidParameterException("Field \"" + field.getName() +
                    "\" must be negativeInt \"String\"" +
                    ", because there is \"@AnyOf\" annotation (may be, inner type)");
        }

        AnyOfHandler.handle(object, field, errorSet, values, container, pathTracker);
    }

    /**
     * Validate value with "@NotEmpty" annotation.
     *
     * @param object object
     * @param field field
     * @param container value container. For more info check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void validateNotEmpty(Object object, Field field, ValueContainer container,
                                        StringBuilder pathTracker) {
        if (field == null) {
            throw new InvalidParameterException("Field was null");
        }
        field.setAccessible(true);

        if (isNullValue(object, field, container)) {
            return;
        }

        String typeName = getTypeName(object, field, container);

        if (TypeChecker.isList(typeName)) {
            NotEmptyHandler.handle(object, field, errorSet, container,
                    ValueType.LIST, pathTracker);
            return;
        } else if (TypeChecker.isSet(typeName)) {
            NotEmptyHandler.handle(object, field, errorSet, container,
                    ValueType.SET, pathTracker);
            return;
        } else if (TypeChecker.isMap(typeName)) {
            NotEmptyHandler.handle(object, field, errorSet, container,
                    ValueType.MAP, pathTracker);
            return;
        } else if (typeName.equals("java.lang.String")) {
            NotEmptyHandler.handle(object, field, errorSet, container,
                    ValueType.STRING, pathTracker);
            return;
        }

        throw new InvalidParameterException("Invalid type of field \"" +
                field.getName() + "\" with " +
                "\"@NotEmpty\" annotation (may be, inner type)");
    }

    /**
     * Validate value with "@Size" annotation.
     *
     * @param object object
     * @param field field
     * @param minSize min possible size
     * @param maxSize max possible size
     * @param container value container. For more info check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void validateSize(Object object, Field field, int minSize, int maxSize,
                                    ValueContainer container, StringBuilder pathTracker) {
        if (field == null) {
            throw new InvalidParameterException("Field was null");
        }
        field.setAccessible(true);

        if (isNullValue(object, field, container)) {
            return;
        }

        if (maxSize < minSize) {
            throw new InvalidParameterException("Field \"" + field.getName() +
                    "\" has wrong bounds" +
                    " in annotation \"Size\". max < min: " +
                    maxSize + " < " + minSize + " (may be, inner type)");
        }

        String typeName = getTypeName(object, field, container);

        if (TypeChecker.isList(typeName)) {
            SizeHandler.handle(object, field, errorSet, minSize, maxSize,
                    container, ValueType.LIST, pathTracker);
            return;
        } else if (TypeChecker.isSet(typeName)) {
            SizeHandler.handle(object, field, errorSet, minSize, maxSize,
                    container, ValueType.SET, pathTracker);
            return;
        } else if (TypeChecker.isMap(typeName)) {
            SizeHandler.handle(object, field, errorSet, minSize, maxSize,
                    container, ValueType.MAP, pathTracker);
            return;
        } else if (typeName.equals("java.lang.String")) {
            SizeHandler.handle(object, field, errorSet, minSize, maxSize,
                    container, ValueType.STRING, pathTracker);
            return;
        }

        throw new InvalidParameterException("Invalid type of field \"" +
                field.getName() + "\" with " +
                "\"@Size\" annotation (may be, inner type)");
    }

    /**
     * Validate value with "@InRange" annotation.
     *
     * @param object object
     * @param field field
     * @param minValue min possible value
     * @param maxValue max possible value
     * @param container value container. For more info check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void validateInRange(Object object, Field field, long minValue,
                                       long maxValue, ValueContainer container,
                                       StringBuilder pathTracker) {
        if (field == null) {
            throw new InvalidParameterException("Field was null");
        }
        field.setAccessible(true);

        if (isNullValue(object, field, container)) {
            return;
        }

        if (minValue > maxValue) {
            throw new InvalidParameterException("Field \"" + field.getName()
                    + "\" has wrong bounds" + " in annotation \"InRange\". " +
                    "max < min: " + maxValue + " < " + minValue + " (may be, inner type)");
        }

        String typeName = getTypeName(object, field, container);

        switch (typeName) {
            case "java.lang.Byte":
            case "byte":
                InRangeHandler.handle(object, field, errorSet, minValue, maxValue,
                        container, ValueType.BYTE, pathTracker);
                return;

            case "java.lang.Short":
            case "short":
                InRangeHandler.handle(object, field, errorSet, minValue, maxValue,
                        container, ValueType.SHORT, pathTracker);
                return;

            case "java.lang.Integer":
            case "int":
                InRangeHandler.handle(object, field, errorSet, minValue, maxValue,
                        container, ValueType.INTEGER, pathTracker);
                return;

            case "java.lang.Long":
            case "long":
                InRangeHandler.handle(object, field, errorSet, minValue, maxValue,
                        container, ValueType.LONG, pathTracker);
                return;
        }

        throw new InvalidParameterException("Invalid type of field \"" +
                field.getName() + "\" with " +
                "\"@InRange\" annotation (may be, inner type)");
    }

    /**
     * Get type name depending on value container.
     *
     * @param object object
     * @param field field
     * @param container value container. For more information check {@link ValueContainer}
     * @return type name
     */
    private static String getTypeName(Object object, Field field, ValueContainer container) {
        switch (container) {
            case FIELD:
                return field.getAnnotatedType().getType().getTypeName();

            case OBJECT:
                return object.getClass().getTypeName();
        }

        throw new InvalidParameterException("Invalid type of container");
    }

    /**
     * Check if given field value or object is null.
     *
     * @param object object
     * @param field field
     * @param container value container. For more information check {@link ValueContainer}
     * @return true, if field value or object is null, false - otherwise
     */
    private static boolean isNullValue(Object object, Field field, ValueContainer container) {
        switch (container) {
            case OBJECT:
                return object == null;

            case FIELD:
                try {
                    if (field.get(object) == null) {
                        return true;
                    }
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                }

                return false;
        }

        throw new InvalidParameterException("Invalid type of container");
    }
}
