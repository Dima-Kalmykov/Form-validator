package solution.annotation_handlers;

import solution.validators.ErrorContent;
import solution.validators.ValidationError;
import solution.utils.MessageBuilder;
import solution.utils.ValueContainer;
import solution.utils.ValueType;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Handler for "@Size" annotation.
 */
public class SizeHandler {

    /**
     * Handle object with given annotation.
     *
     * @param object      object
     * @param field       field
     * @param errorSet    set with ValidationError.
     * @param minSize     min possible size
     * @param maxSize     max possible size
     * @param container   value container. For more information check {@link ValueContainer}
     * @param valueType   value type. For more information check {@link ValueType}
     * @param pathTracker path tracker
     */
    public static void handle(Object object, Field field, Set<ValidationError> errorSet,
                              int minSize, int maxSize, ValueContainer container,
                              ValueType valueType, StringBuilder pathTracker) {
        switch (container) {
            case OBJECT:
                handleValue(object, errorSet, minSize,
                        maxSize, valueType, pathTracker);
                return;

            case FIELD:
                try {
                    handleValue(field.get(object), errorSet, minSize,
                            maxSize, valueType, pathTracker);
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                }
                return;
        }

        throw new InvalidParameterException("Invalid type of container");
    }

    /**
     * Check that the value located in range [min; max].
     *
     * @param min   min value
     * @param max   max value
     * @param value real value
     * @return true if value in range [min; max], false - otherwise
     */
    private static boolean inRange(int min, int max, int value) {
        return (min <= value && value <= max);
    }

    /**
     * Handle value.
     *
     * @param object      object
     * @param errorSet    set with {@link ValidationError}
     * @param minSize     min possible size
     * @param maxSize     max possible size
     * @param valueType   value type. For more information check {@link ValueType}
     * @param pathTracker path tracker
     */
    private static void handleValue(Object object, Set<ValidationError> errorSet,
                                    int minSize, int maxSize, ValueType valueType,
                                    StringBuilder pathTracker) {
        switch (valueType) {
            case LIST:
                var valueList = (List<?>) object;
                if (!inRange(minSize, maxSize, valueList.size())) {
                    errorSet.add(new ErrorContent(
                            MessageBuilder.getErrorMessage(minSize, maxSize, "size"),
                            pathTracker.toString(), valueList));
                }
                return;

            case SET:
                var valueSet = (Set<?>) object;
                if (!inRange(minSize, maxSize, valueSet.size())) {
                    errorSet.add(new ErrorContent(
                            MessageBuilder.getErrorMessage(minSize, maxSize, "size"),
                            pathTracker.toString(), valueSet));
                }
                return;

            case MAP:
                var valueMap = (Map<?, ?>) object;
                if (!inRange(minSize, maxSize, valueMap.size())) {
                    errorSet.add(new ErrorContent(
                            MessageBuilder.getErrorMessage(minSize, maxSize, "size"),
                            pathTracker.toString(), valueMap));
                }
                return;

            case STRING:
                var valueString = (String) object;
                if (!inRange(minSize, maxSize, valueString.length())) {
                    errorSet.add(new ErrorContent(
                            MessageBuilder.getErrorMessage(minSize, maxSize, "size"),
                            pathTracker.toString(), valueString));
                }
                return;
        }

        throw new InvalidParameterException("Invalid type of value");
    }
}
