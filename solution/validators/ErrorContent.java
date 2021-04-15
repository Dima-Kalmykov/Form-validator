package solution.validators;

/**
 * Error content.
 */
public class ErrorContent implements ValidationError {

    /**
     * Error message.
     */
    private final String message;

    /**
     * Path to field which causes the error.
     */
    private final String path;

    /**
     * Value of field.
     */
    private final Object failedValue;

    /**
     * Constructor.
     *
     * @param message error message
     * @param path path to field
     * @param failedValue failed value
     */
    public ErrorContent(String message, String path, Object failedValue) {
        this.message = message;
        this.path = path;
        this.failedValue = failedValue;
    }

    /**
     * Getter for failed value field.
     *
     * @return failed value
     */
    @Override
    public Object getFailedValue() {
        if (failedValue instanceof String) {
            if (((String) failedValue).isBlank() ||
                    ((String) failedValue).isEmpty()) {
                return "\"" + failedValue + "\"";
            }
        }
        return failedValue;
    }

    /**
     * Getter for message field.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Getter for path field.
     *
     * @return path
     */
    @Override
    public String getPath() {
        return getTransformedPath(path);
    }

    /**
     * Convert path representation.
     *
     * @param path path
     * @return new path representation
     */
    private String getTransformedPath(String path) {
        if (path.length() < 2) {
            return path;
        }

        var resultPath = path.substring(1, path.length() - 1);
        resultPath = resultPath.replace("/", ".");

        for (var i = 0; i < resultPath.length() - 2; ++i) {
            if (resultPath.charAt(i + 1) == '[') {
                resultPath = resultPath.substring(0, i) + resultPath.substring(i + 1);
            }
            if (resultPath.charAt(i) == ']' && resultPath.charAt(i + 2) == '[') {
                resultPath = resultPath.substring(0, i + 1) + resultPath.substring(i + 2);
            }
        }

        return resultPath;
    }
}
