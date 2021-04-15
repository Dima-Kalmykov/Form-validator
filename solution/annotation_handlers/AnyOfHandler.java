package solution.annotation_handlers;

import solution.validators.ErrorContent;
import solution.validators.ValidationError;
import solution.utils.MessageBuilder;
import solution.utils.ValueContainer;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Set;

/**
 * Handler for "@AnyOf" annotation.
 */
public class AnyOfHandler {

    /**
     * Handle object with given annotation.
     *
     * @param object object
     * @param field field
     * @param errorSet set with {@link ValidationError}
     * @param values valid values for given object
     * @param container value container. For more information check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void handle(Object object, Field field, Set<ValidationError> errorSet,
                              String[] values, ValueContainer container,
                              StringBuilder pathTracker) {
        switch (container) {
            case OBJECT:
                var valueInObject = (String) object;
                handleValue(valueInObject, values, errorSet, pathTracker);
                return;

            case FIELD:
                try {
                    var valueInField = (String) field.get(object);
                    handleValue(valueInField, values, errorSet, pathTracker);
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
     * @param values valid values for given object
     * @param pathTracker path tracker
     */
    private static void handleValue(String value, String[] values, Set<ValidationError> errorSet,
                                    StringBuilder pathTracker) {
        if (!Arrays.asList(values).contains(value)) {
            errorSet.add(new ErrorContent(MessageBuilder.getErrorMessage(values),
                    pathTracker.toString(), value));
        }
    }
}
