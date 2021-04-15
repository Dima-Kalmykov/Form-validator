package tests.validators.test_forms;

import solution.annotations.Constrained;
import solution.annotations.NotEmpty;

import java.util.List;
import java.util.Set;

@Constrained
public class NotEmptyForm {

    @NotEmpty
    private String emptyString = "";

    @NotEmpty
    private Set set = Set.of();

    @NotEmpty
    private List<@NotEmpty List<Integer>> list = List.of();

    @NotEmpty
    private Set<@NotEmpty String> collection = Set.of("2", "123");
}
