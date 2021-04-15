package tests.validators.test_forms;

import solution.annotations.Constrained;
import solution.annotations.Size;

import java.util.List;
import java.util.Set;

@Constrained
public class SizeForm {

    @Size(min = 2, max = 2)
    private String emptyString = "";

    @Size(min = 2, max = 4)
    private Set nullValue = null;

    @Size(min = 2, max = 3)
    private Set set = Set.of(2, 5, "sdf", 24);

    @Size(min = 10, max = 13)
    private List<@Size(min = 1, max = 2) List<Integer>> list = List.of(List.of(1, 2, 3), List.of(3, 1));

    @Size(min = 1, max = 3)
    private Set<@Size(min = 2, max = 4) String> collection = Set.of("2", "123");
}
