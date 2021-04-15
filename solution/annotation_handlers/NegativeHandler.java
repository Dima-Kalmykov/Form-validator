package solution.annotation_handlers;

import solution.validators.ErrorContent;
import solution.validators.ValidationError;
import solution.utils.MessageBuilder;
import solution.utils.ValueContainer;
import solution.utils.ValueType;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.Set;

/**
 * Handler for "@Negative" annotation.
 */
public class NegativeHandler {

    /**
     * Handle object with given annotation.
     *
     * @param object object
     * @param field field
     * @param errorSet set with {@link ValidationError}
     * @param container value container. For more information check {@link ValueContainer}
     * @param valueType value type. For more information check {@link ValueType}
     * @param pathTracker path tracker
     */
    public static void handle(Object object, Field field, Set<ValidationError> errorSet,
                              ValueContainer container, ValueType valueType,
                              StringBuilder pathTracker) {
        switch (container) {
            case OBJECT:
                handleValue(object, errorSet, valueType, pathTracker);
                return;

            case FIELD:
                try {
                    handleValue(field.get(object), errorSet, valueType, pathTracker);
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                }
                return;
        }

        throw new InvalidParameterException("Invalid type of container");
    }

    /**
     * Handle value.
     *
     * @param object object
     * @param errorSet set with {@link ValidationError}
     * @param valueType value type. For more information check {@link ValueType}
     * @param pathTracker path tracker
     */
    private static void handleValue(Object object, Set<ValidationError> errorSet,
                                    ValueType valueType, StringBuilder pathTracker) {
        switch (valueType) {
            case BYTE:
                var valueByte = (byte) object;
                handleValue(valueByte, errorSet, pathTracker);
                return;

            case SHORT:
                var valueShort = (short) object;
                handleValue(valueShort, errorSet, pathTracker);
                return;

            case INTEGER:
                var valueInteger = (int) object;
                handleValue(valueInteger, errorSet, pathTracker);
                return;

            case LONG:
                var valueLong = (long) object;
                handleValue(valueLong, errorSet, pathTracker);
                return;
        }

        throw new InvalidParameterException("Invalid type of value");
    }

    /**
     * Handle value.
     *
     * @param value value
     * @param errorSet set with {@link ValidationError}
     * @param pathTracker path tracker
     */
    private static void handleValue(long value, Set<ValidationError> errorSet,
                                    StringBuilder pathTracker) {
        if (value >= 0) {
            errorSet.add(new ErrorContent(
                    MessageBuilder.getErrorMessage("Negative"),
                    pathTracker.toString(), value));
        }
    }
}
