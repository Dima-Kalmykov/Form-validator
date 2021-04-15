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
 * Handle for "@InRange" annotation.
 */
public class InRangeHandler {

    /**
     * Handle object with given annotation.
     *
     * @param object object
     * @param field field
     * @param errorSet set with {@link ValidationError}
     * @param minValue min possible value
     * @param maxValue max possible value
     * @param container value container. For more information check {@link ValueContainer}
     * @param valueType value type. For more information check {@link ValueType}
     * @param pathTracker path tracker
     */
    public static void handle(Object object, Field field, Set<ValidationError> errorSet,
                              long minValue, long maxValue, ValueContainer container,
                              ValueType valueType, StringBuilder pathTracker) {
        switch (container) {
            case OBJECT:
                handleValue(object, errorSet, minValue,
                        maxValue, valueType, pathTracker);
                return;

            case FIELD:
                try {
                    handleValue(field.get(object), errorSet, minValue,
                            maxValue, valueType, pathTracker);
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                }
                return;
        }

        throw new InvalidParameterException("Invalid type of container");
    }

    /**
     * Check that the value located in range.
     *
     * @param minValue min value
     * @param maxValue max value
     * @param value real value
     * @return true if value in range [min; max], false - otherwise
     */
    private static boolean inRange(long minValue, long maxValue, long value) {
        return (minValue <= value && value <= maxValue);
    }

    /**
     * Handle value.
     *
     * @param object object
     * @param errorSet set with {@link ValidationError}
     * @param minValue min possible value
     * @param maxValue max possible value
     * @param valueType value type. For more information check {@link ValueType}
     * @param pathTracker path tracker
     */
    private static void handleValue(Object object, Set<ValidationError> errorSet,
                                    long minValue, long maxValue,
                                    ValueType valueType, StringBuilder pathTracker) {
        switch (valueType) {
            case BYTE:
                var valueByte = (byte) object;
                handleValue(valueByte, minValue, maxValue, errorSet, pathTracker);
                return;

            case SHORT:
                var valueShort = (short) object;
                handleValue(valueShort, minValue, maxValue, errorSet, pathTracker);
                return;

            case INTEGER:
                var valueInteger = (int) object;
                handleValue(valueInteger, minValue, maxValue, errorSet, pathTracker);
                return;

            case LONG:
                var valueLong = (long) object;
                handleValue(valueLong, minValue, maxValue, errorSet, pathTracker);
                return;
        }

        throw new InvalidParameterException("Invalid type of value");
    }

    /**
     * Handle value.
     *
     * @param errorSet set with {@link ValidationError}
     * @param minValue min possible value
     * @param maxValue max possible value
     * @param pathTracker path tracker
     */
    private static void handleValue(long value, long minValue, long maxValue,
                                    Set<ValidationError> errorSet, StringBuilder pathTracker) {
        if (!inRange(minValue, maxValue, value)) {
            errorSet.add(new ErrorContent(MessageBuilder.getErrorMessage(
                    minValue, maxValue, "value"),
                    pathTracker.toString(), value));
        }
    }
}
