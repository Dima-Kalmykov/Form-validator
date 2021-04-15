package solution.utils;

/**
 * Types of value containers.
 */
public enum ValueContainer {
    FIELD, // You need to unpack object from field: value = field.get(object)
    OBJECT // You need to work with given object: value = object
}
