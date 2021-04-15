package solution.validators;

/**
 * Description of error content.
 */
public interface ValidationError {

    /**
     * Return error description.
     *
     * @return string with info
     */
    String getMessage();

    /**
     * Return path to field which causes the error.
     *
     * @return path to field.
     */
    String getPath();

    /**
     * Get failed value.
     *
     * @return failed value
     */
    Object getFailedValue();
}
