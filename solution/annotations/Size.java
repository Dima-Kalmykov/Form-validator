package solution.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check that the value size located in range [min; max].
 * <p>
 * Apply just to List<T>, Set<T>, Map<K, V>, String.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Size {

    /**
     * Get min value.
     * @return min value
     */
    int min();

    /**
     * Get max value.
     * @return max value
     */
    int max();
}
