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
 * Handler for "@NotEmpty" annotation.
 */
public class NotEmptyHandler {

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
            case LIST:
                var valueList = (List<?>) object;
                if (valueList.isEmpty()) {
                    errorSet.add(new ErrorContent(
                            MessageBuilder.getErrorMessage("NotEmpty"),
                            pathTracker.toString(), valueList));
                }
                return;

            case SET:
                var valueSet = (Set<?>) object;
                if (valueSet.isEmpty()) {
                    errorSet.add(new ErrorContent(
                            MessageBuilder.getErrorMessage("NotEmpty"),
                            pathTracker.toString(), valueSet));
                }
                return;

            case MAP:
                var valueMap = (Map<?, ?>) object;
                if (valueMap.isEmpty()) {
                    errorSet.add(new ErrorContent(
                            MessageBuilder.getErrorMessage("NotEmpty"),
                            pathTracker.toString(), valueMap));
                }
                return;

            case STRING:
                var valueString = (String) object;
                if (valueString.isEmpty()) {
                    errorSet.add(new ErrorContent(
                            MessageBuilder.getErrorMessage("NotEmpty"),
                            pathTracker.toString(), valueString));
                }
                return;
        }

        throw new InvalidParameterException("Invalid type of value");
    }
}
