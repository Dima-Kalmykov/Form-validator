package tests.validators.test_forms;

import solution.annotations.Constrained;
import solution.annotations.Positive;

import java.util.*;

@Constrained
public class PositiveForm {

    private Set<@Positive Integer> positiveSet = Set.of(1, 4, -2);

    @Positive
    private Long positiveLong = -3L;

    @Positive
    private Integer nullValue = null;

    @Positive
    private byte positiveByte = 4;

    private List<@Positive Integer> positiveList = List.of(-2, 2, -4);

    private Map<@Positive Integer, Short> positiveMapKeys = Map.of(-1, (short)-2, 3, (short)-4);

    private Map<Integer, @Positive Integer> positiveMapValues = Map.of(-2, -3, -5, -3);

    private List<List<List<@Positive Integer>>> positiveInnerList =
            List.of(List.of(List.of(1, -2, 3)), List.of(List.of(1, 2, -3)));
}
