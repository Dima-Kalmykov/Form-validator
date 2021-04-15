package solution.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check that the value located in range [min; max].
 * <p>
 * Apply just to byte, short, int, long and their wrappers.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface InRange {

    /**
     * Get min value.
     * @return min value
     */
    long min();

    /**
     * Get max value.
     * @return max value
     */
    long max();
}
