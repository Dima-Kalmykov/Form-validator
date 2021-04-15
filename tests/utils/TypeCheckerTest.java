package tests.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import solution.utils.TypeChecker;
import solution.validators.ObjectValidator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Type checker test")
public class TypeCheckerTest {

    @Test
    @DisplayName("Is byteList test")
    void isList() {
        var list1 = List.of(1, 2, 3);
        Assertions.assertTrue(TypeChecker.isList(list1.getClass().getName()));
        var list2 = new ArrayList<>(List.of("Hello", "world"));
        assertTrue(TypeChecker.isList(list2.getClass().getName()));
        var list3 = new LinkedList<>(List.of(1d, 2d));
        assertTrue(TypeChecker.isList(list3.getClass().getName()));

        var notList1 = Map.of(1, 2);
        assertFalse(TypeChecker.isList(notList1.getClass().getName()));
        var notList2 = new ObjectValidator();
        assertFalse(TypeChecker.isList(notList2.getClass().getName()));

        Collection<Integer> notList3 = Set.of(1, 2, 3);
        assertFalse(TypeChecker.isList(notList3.getClass().getName()));
    }

    @Test
    @DisplayName("Is set test")
    void isSet() {
        var set1 = Set.of(1, 2, 3);
        assertTrue(TypeChecker.isSet(set1.getClass().getName()));
        var set2 = new TreeSet<>(List.of("Hello", "world"));
        assertTrue(TypeChecker.isSet(set2.getClass().getName()));
        var set3 = new HashSet<>(List.of(1d, 2d));
        assertTrue(TypeChecker.isSet(set3.getClass().getName()));

        var notSet1 = Map.of(1, 2);
        assertFalse(TypeChecker.isSet(notSet1.getClass().getName()));
        var notSet2 = new ObjectValidator();
        assertFalse(TypeChecker.isSet(notSet2.getClass().getName()));
        Collection<Integer> notSet3 = List.of(1, 2, 3);
        assertFalse(TypeChecker.isSet(notSet3.getClass().getName()));
    }

    @Test
    @DisplayName("Is collection or map test")
    void isCollectionOrMap() {
        var collection1 = Set.of(1, 2, 3);
        assertTrue(TypeChecker.isCollectionOrMap(collection1.getClass().getName()));
        var collection2 = new TreeMap<>(Map.of(1, 2, 3, 4));
        assertTrue(TypeChecker.isCollectionOrMap(collection2.getClass().getName()));
        var collection3 = new ArrayList<>(List.of(1d, 2d));
        assertTrue(TypeChecker.isCollectionOrMap(collection3.getClass().getName()));

        var notCollectionOrMap1 = Integer.valueOf(3);
        assertFalse(TypeChecker.isCollectionOrMap(
                notCollectionOrMap1.getClass().getName()));
        var notCollectionOrMap2 = new ObjectValidator();
        assertFalse(TypeChecker.isCollectionOrMap(
                notCollectionOrMap2.getClass().getName()));
    }

    @Test
    @DisplayName("Is collection test")
    void isCollection() {
        var collection1 = Set.of(1, 2, 3);
        assertTrue(TypeChecker.isCollection(collection1.getClass().getName()));
        var collection2 = new ArrayList<>(List.of("Hello", "world"));
        assertTrue(TypeChecker.isCollection(collection2.getClass().getName()));
        var collection3 = new HashSet<>(List.of(1d, 2d));
        assertTrue(TypeChecker.isCollection(collection3.getClass().getName()));

        var notCollection1 = Map.of(1, 2);
        assertFalse(TypeChecker.isCollection(notCollection1.getClass().getName()));
        var notCollection2 = new ObjectValidator();
        assertFalse(TypeChecker.isCollection(notCollection2.getClass().getName()));
    }

    @Test
    @DisplayName("Is map test")
    void isMap() {
        var map1 = Map.of(1, 2, 3, 4);
        assertTrue(TypeChecker.isMap(map1.getClass().getName()));
        var map2 = new HashMap<>(Map.of("Hello", "world"));
        assertTrue(TypeChecker.isMap(map2.getClass().getName()));
        var map3 = new TreeMap<>(Map.of(1d, 2d));
        assertTrue(TypeChecker.isMap(map3.getClass().getName()));

        var notMap1 = Set.of(1, 2);
        assertFalse(TypeChecker.isMap(notMap1.getClass().getName()));
        var notMap2 = new ObjectValidator();
        assertFalse(TypeChecker.isMap(notMap2.getClass().getName()));
        var notMap3 = List.of(1, 2, 3);
        assertFalse(TypeChecker.isMap(notMap3.getClass().getName()));
    }

    public ArrayList<List<Integer>> paramList1 = new ArrayList<>();
    public LinkedList<Integer> paramList2 = new LinkedList<>();
    public LinkedList<ArrayList> paramList3 = new LinkedList<>();

    public List unparamList1 = new ArrayList();
    public Set<Integer> unparamList2 = new TreeSet<>();
    public int unparamList3 = 3;

    @Test
    @DisplayName("Is parameterized list test")
    void isParameterizedList() throws NoSuchFieldException {
        var typeCheckerClass = TypeCheckerTest.class;
        var list1 = typeCheckerClass.getField("paramList1");
        assertTrue(TypeChecker.isParameterizedList(list1));
        var list2 = typeCheckerClass.getField("paramList2");
        assertTrue(TypeChecker.isParameterizedList(list2));
        var list3 = typeCheckerClass.getField("paramList3");
        assertTrue(TypeChecker.isParameterizedList(list3));

        var notList1 = typeCheckerClass.getField("unparamList1");
        assertFalse(TypeChecker.isParameterizedList(notList1));
        var notList2 = typeCheckerClass.getField("unparamList2");
        assertFalse(TypeChecker.isParameterizedList(notList2));
        var notList3 = typeCheckerClass.getField("unparamList3");
        assertFalse(TypeChecker.isParameterizedList(notList3));
    }

    public Map<Integer, Integer> paramMap1 = new TreeMap<>();
    public HashMap<String, String> paramMap2 = new HashMap<>();

    public Collection<Integer> unparamMap1 = new ArrayList<>();
    public Set<Integer> unparamMap2 = new TreeSet<>();
    public Map unparamMap3 = new HashMap();

    @Test
    @DisplayName("Is parameterized map test")
    void isParameterizedMap() throws NoSuchFieldException {
        var typeCheckerClass =TypeCheckerTest.class;
        var map1 = typeCheckerClass.getField("paramMap1");
        assertTrue(TypeChecker.isParameterizedMap(map1));
        var map2 = typeCheckerClass.getField("paramMap2");
        assertTrue(TypeChecker.isParameterizedMap(map2));

        var notMap1 = typeCheckerClass.getField("unparamMap1");
        assertFalse(TypeChecker.isParameterizedMap(notMap1));
        var notMap2 = typeCheckerClass.getField("unparamMap2");
        assertFalse(TypeChecker.isParameterizedMap(notMap2));
        var notMap3 = typeCheckerClass.getField("unparamMap3");
        assertFalse(TypeChecker.isParameterizedMap(notMap3));
    }

    public Collection<Integer> paramCollection1 = new LinkedList<>();
    public Collection<String> paramCollection2 = new HashSet<>();

    public Collection unparamCollection1 = new ArrayList<>();
    public float unparamCollection2 = 3f;
    public Map<String, String> unparamCollection3 = new HashMap<>();

    @Test
    @DisplayName("Is parameterized collection test")
    void isParameterizedCollection() throws NoSuchFieldException {
        var typeCheckerClass = TypeCheckerTest.class;
        var collection1 = typeCheckerClass.getField("paramCollection1");
        assertTrue(TypeChecker.isParameterizedCollection(collection1));
        var collection2 = typeCheckerClass.getField("paramCollection2");
        assertTrue(TypeChecker.isParameterizedCollection(collection2));

        var notCollection1 = typeCheckerClass.getField("unparamCollection1");
        assertFalse(TypeChecker.isParameterizedCollection(notCollection1));
        var notCollection2 = typeCheckerClass.getField("unparamCollection2");
        assertFalse(TypeChecker.isParameterizedCollection(notCollection2));
        var notCollection3 = typeCheckerClass.getField("unparamCollection3");
        assertFalse(TypeChecker.isParameterizedCollection(notCollection3));
    }

    @Test
    @DisplayName("Is custom class test")
    void isCustomClass() {
        var customClasses = List.of(TypeChecker.class.getName(),
                ObjectValidator.class.getName(), "com.company.Main");

        for (var customClass : customClasses) {
            assertTrue(TypeChecker.isCustomClass(customClass));
        }

        var notCustomClasses = List.of("java.lang.Integer",
                "java.util.Map", "int", "byte", String.class.getName());

        for (var notCustomClass : notCustomClasses) {
            assertFalse(TypeChecker.isCustomClass(notCustomClass));
        }
    }

    @Test
    @DisplayName("Is primitive test")
    void isPrimitive() {
        var primitives =
                List.of("int", "byte", "short", "double", "char", "float", "boolean");
        for (var primitive : primitives) {
            assertTrue(TypeChecker.isPrimitive(primitive));
        }

        var notPrimitives = List.of(String.class.getName(),
                Object.class.getName(), TypeChecker.class.getName(), Integer.class.getName());

        for (var notPrimitive : notPrimitives) {
            assertFalse(TypeChecker.isPrimitive(notPrimitive));
        }
    }

    public List<List> nestedList1 = new ArrayList<>();
    public List<LinkedList<Integer>> nestedList2 = new ArrayList<>();
    public ArrayList<LinkedList<String>> nestedList3 = new ArrayList<>();

    public List notNestedList1 = new LinkedList();
    public Map<String, String> notNestedList2 = new HashMap<>();


    @Test
    @DisplayName("Is nested list test")
    void isNestedList() throws NoSuchFieldException {
        var typeCheckerClass = TypeCheckerTest.class;
        var list1 = typeCheckerClass.getField("nestedList1");
        assertTrue(TypeChecker.isNestedList(list1.getAnnotatedType().getType()));
        var list2 = typeCheckerClass.getField("nestedList2");
        assertTrue(TypeChecker.isNestedList(list2.getAnnotatedType().getType()));
        var list3 = typeCheckerClass.getField("nestedList3");
        assertTrue(TypeChecker.isNestedList(list3.getAnnotatedType().getType()));

        var notList1 = typeCheckerClass.getField("notNestedList1");
        assertFalse(TypeChecker.isNestedList(notList1.getAnnotatedType().getType()));
        var notList2 = typeCheckerClass.getField("notNestedList2");
        assertFalse(TypeChecker.isNestedList(notList2.getAnnotatedType().getType()));
    }
}