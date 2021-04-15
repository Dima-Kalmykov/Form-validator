package solution.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Helper class for checking type of fields.
 */
public class TypeChecker {

    /**
     * Check if given type name represents List.
     *
     * @param typeName name of type
     * @return true if given type name represents List, false - otherwise
     */
    public static boolean isList(String typeName) {
        return typeName.startsWith("java.util.") && typeName.contains("List");
    }

    /**
     * Check if given type name represents Set.
     *
     * @param typeName name of type
     * @return true if given type name represents Set, false - otherwise
     */
    public static boolean isSet(String typeName) {
        return typeName.startsWith("java.util.") && typeName.contains("Set");
    }

    /**
     * Check if given type name represents Collection or Map.
     *
     * @param typeName name of type
     * @return true if given type name represents Collection or Map, false - otherwise
     */
    public static boolean isCollectionOrMap(String typeName) {
        return isCollection(typeName) || isMap(typeName);
    }

    /**
     * Check if given type name represents Collection.
     *
     * @param typeName name of type
     * @return true if given type name represents Collection, false - otherwise
     */
    public static boolean isCollection(String typeName) {
        return (isSet(typeName) || isList(typeName)
                || typeName.startsWith("java.util.") &&
                typeName.contains("Collection")) &&
                !isMap(typeName);
    }

    /**
     * Check if given type name represents Map.
     *
     * @param typeName name of type
     * @return true if given type name represents Map, false - otherwise
     */
    public static boolean isMap(String typeName) {
        return typeName.startsWith("java.util.") && typeName.contains("Map");
    }

    /**
     * Check if given field is parameterized List.
     *
     * @param field field
     * @return true if given field is parameterized List, false - otherwise
     */
    public static boolean isParameterizedList(Field field) {
        return isList(field.getAnnotatedType().getType().getTypeName())
                && field.getAnnotatedType().getType() instanceof ParameterizedType;
    }

    /**
     * Check if given field is parameterized Map.
     *
     * @param field field
     * @return true if given field is parameterized Map, false - otherwise
     */
    public static boolean isParameterizedMap(Field field) {
        return isMap(field.getAnnotatedType().getType().getTypeName())
                && field.getAnnotatedType().getType() instanceof ParameterizedType;
    }

    /**
     * Check if given field is parameterized Collection.
     *
     * @param field field
     * @return true if given field is parameterized Collection, false - otherwise
     */
    public static boolean isParameterizedCollection(Field field) {
        return isCollection(field.getAnnotatedType().getType().getTypeName())
                && field.getAnnotatedType().getType() instanceof ParameterizedType;
    }

    /**
     * Check if given class is custom.
     *
     * @param className class name
     * @return true if class is custom, false - otherwise
     */
    public static boolean isCustomClass(String className) {
        return !className.startsWith("java.lang") &&
                !className.startsWith("java.util") &&
                !isPrimitive(className);
    }

    /**
     * Check if given type is primitive.
     *
     * @param type type
     * @return true if type is primitive, false - otherwise
     */
    public static boolean isPrimitive(String type) {
        return List.of("int", "long", "float",
                "double", "char", "byte", "short", "boolean").contains(type);
    }

    /**
     * Check if byteList has nested lists.
     *
     * @param type current type of byteList content
     * @return true if byteList has nested lists, false - otherwise
     */
    public static boolean isNestedList(Type type) {
        if (type instanceof ParameterizedType) {
            var typeName = type.getTypeName().
                    substring(0, type.getTypeName().indexOf('<'));

            return TypeChecker.isList(typeName);
        } else {
            return false;
        }
    }
}
