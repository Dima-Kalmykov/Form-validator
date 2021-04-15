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
 * Handler for "@NotNull" annotation.
 */
public class NotNullHandler {

    /**
     * Handle object with given annotation.
     *
     * @param object      object
     * @param field       field
     * @param errorSet    set with {@link ValidationError}
     * @param container   value container. For more information check {@link ValueContainer}
     * @param pathTracker path tracker
     */
    public static void handle(Object object, Field field, Set<ValidationError> errorSet,
                              ValueContainer container, StringBuilder pathTracker) {
        switch (container) {
            case FIELD:
                try {
                    if (field == null) {
                        throw new InvalidParameterException("Field \"" + "\" was null");
                    } else if (field.getType().isPrimitive()) {
                        throw new InvalidParameterException("Field \"" + field.getName()
                                + "\" has primitive type." +
                                "\nYou can't apply \"@NotNull\" annotation");
                    } else {
                        handleValue(field.get(object), errorSet, pathTracker);
                    }
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                }
                return;

            case OBJECT:
                handleValue(object, errorSet, pathTracker);
                return;
        }

        throw new InvalidParameterException("Invalid type of container");
    }

    /**
     * Handle value.
     *
     * @param object      object
     * @param errorSet    set with {@link ValidationError}
     * @param pathTracker path tracker
     */
    private static void handleValue(Object object, Set<ValidationError> errorSet,
                                    StringBuilder pathTracker) {
        if (object == null) {
            errorSet.add(new ErrorContent(
                    MessageBuilder.getErrorMessage("NotNull"),
                    pathTracker.toString(), "null"));
        }
    }
}
