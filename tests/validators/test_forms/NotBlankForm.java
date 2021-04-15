package tests.validators.test_forms;

import solution.annotations.Constrained;
import solution.annotations.NotBlank;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Constrained
public class NotBlankForm {

    @NotBlank
    private String emptyString = "";

    @NotBlank
    private String nullValue = null;

    @NotBlank
    private String hello = "Hello, world!";

    private List<@NotBlank String> list = List.of(" ", "123");

    private Set<@NotBlank String> set = Set.of("     ", "312", "world");

    private Map<@NotBlank String, Integer> map = Map.of(" ", 2, "123", 3);
}
