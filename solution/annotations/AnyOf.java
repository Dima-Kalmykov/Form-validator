package solution.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check that the value located in array.
 * <p>
 * Apply just to String.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface AnyOf {

    /**
     * Get byteList of valid values.
     * @return byteList of valid values
     */
    String[] value();
}


