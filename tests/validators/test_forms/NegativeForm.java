package tests.validators.test_forms;

import solution.annotations.Constrained;
import solution.annotations.Negative;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Constrained
public class NegativeForm {

    private Set<@Negative Integer> negativeSet = Set.of(1, 4, -2);

    @Negative
    private long negativeLong = -3L;

    @Negative
    private Integer nullValue = null;

    @Negative
    private Short negativeShort = 4;

    private List<@Negative Integer> negativeList = List.of(-2, 2, -4);

    private Map<@Negative Integer, Short> negativeMapKeys = Map.of(-1, (short)-2, 3, (short)-4);

    private Map<Integer, @Negative Integer> negativeMapValues = Map.of(-2, -3, -5, -3);

    private List<List<List<@Negative Integer>>> negativeInnerList =
            List.of(List.of(List.of(1, -2, 3)), List.of(List.of(1, 2, -3)));
}
