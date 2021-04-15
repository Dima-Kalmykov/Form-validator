package solution.validators;

import java.util.Set;

/**
 * Description of validator.
 */
public interface Validator {

    /**
     * Validate object.
     *
     * @param object object
     * @return set of errors. For more information check {@link ValidationError}
     */
    Set<ValidationError> validate(Object object);
}
