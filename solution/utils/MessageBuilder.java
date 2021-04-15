package solution.utils;

import java.security.InvalidParameterException;

/**
 * Helper class for building error messages.
 */
public class MessageBuilder {

    /**
     * Get error message for "@AnyOf" annotation.
     *
     * @param values byteList of values
     * @return error message
     */
    public static String getErrorMessage(String[] values) {
        return "must be one of " + getStringRepresentationOfArray(values);
    }

    /**
     * Get error message for "@Size" and "@InRange" annotations.
     *
     * @param min min value
     * @param max max value
     * @param object "size" or "value"
     * @return error message
     */
    public static String getErrorMessage(long min, long max, String object) {
        return object + " must be in range between " + min + " and " + max;
    }

    /**
     * Get error message for given annotation type.
     *
     * @param annotationType string representation of annotation type
     * @return error message
     */
    public static String getErrorMessage(String annotationType) {
        switch (annotationType) {
            case "Negative":
                return "must be negative";

            case "Positive":
                return "must be positive";

            case "NotBlank":
                return "must be not blank";

            case "NotEmpty":
                return "must be not empty";

            case "NotNull":
                return "must be not null";
        }

        throw new InvalidParameterException("Invalid type of annotation");
    }

    /**
     * Get string representation of array
     *
     * @param values byteList of values
     * @return string representation of array
     */
    private static String getStringRepresentationOfArray(String[] values) {
        StringBuilder stringBuilder = new StringBuilder();

        for (var i = 0; i < values.length - 1; i++) {
            stringBuilder.append("\"").append(values[i]).append("\", ");
        }

        stringBuilder.append("\"").append(values[values.length - 1]).append("\"");

        return stringBuilder.toString();
    }
}
