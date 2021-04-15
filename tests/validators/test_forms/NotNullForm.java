package tests.validators.test_forms;

import solution.annotations.Constrained;
import solution.annotations.NotBlank;
import solution.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Constrained
public class NotNullForm {

    @NotNull
    private Integer integer = null;

    @NotNull
    private List list;

    @NotNull
    @NotBlank
    private String nullString = null;

    private List<@NotNull List<Integer>> innerList = getList();

    private ArrayList<List<Integer>> getList() {
        var list = new ArrayList<List<Integer>>();
        list.add(null);
        return list;
    }

}
