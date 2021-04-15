package tests.validators.test_forms;

import solution.annotations.AnyOf;
import solution.annotations.Constrained;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Constrained
public class AnyOfForm {

    @AnyOf({"12", "23"})
    private String invalidString = "123";

    @AnyOf("123")
    private String nullValue = null;

    @AnyOf({"12", "21"})
    private String emptyString = "";

    private List<@AnyOf({"1", "2"}) String> list = List.of("1", "3", "4", "2");

    private Set<@AnyOf({"2", "4"}) String> set = Set.of("1", "2");

    private List<List<@AnyOf("hello") String>> innerList = List.of(List.of("hello", "world"));

    private Map<Integer, @AnyOf({"2", "3"}) String> mapValues =
            Map.of(1, "2", 3, "4", 5, "6");
}
