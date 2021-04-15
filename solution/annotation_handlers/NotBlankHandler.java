package solution.annotation_handlers;

import solution.utils.ValueType;
import solution.validators.ErrorContent;
import solution.validators.ValidationError;
import solution.utils.MessageBuilder;
import solution.utils.ValueContainer;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.Set;

/**
 * Handler for "@NotBlank" annotation.
 */
public class NotBlankHandler {

    /**
     * Handle object with given annotation.
     *
     * @param object object
     * @param field field
     * @param errorSet set with {@link ValidationError}
     * @param container value container. For more information check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void handle(Object object, Field field, Set<ValidationError> errorSet,
                              ValueContainer container, StringBuilder pathTracker) {
        switch (container) {
            case OBJECT:
                var valueInObject = (String) object;
                handleValue(valueInObject, errorSet, pathTracker);
                return;

            case FIELD:
                try {
                    var valueInField = (String) field.get(object);
                    handleValue(valueInField, errorSet, pathTracker);
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
     * @param value value
     * @param errorSet set with {@link ValidationError}
     * @param pathTracker path tracker
     */
    private static void handleValue(String value, Set<ValidationError> errorSet,
                                    StringBuilder pathTracker) {
        if (value.isBlank()) {
            errorSet.add(new ErrorContent(
                    MessageBuilder.getErrorMessage("NotBlank"),
                    pathTracker.toString(), value));
        }
    }
}
