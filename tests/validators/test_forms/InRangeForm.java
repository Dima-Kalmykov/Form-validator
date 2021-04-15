package tests.validators.test_forms;

import solution.annotations.Constrained;
import solution.annotations.InRange;
import solution.annotations.Negative;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Constrained
public class InRangeForm {

    private Set<@InRange(min = 2, max = 10) Integer> inRangeSet = Set.of(1, 4, -2);

    @InRange(min = 0, max = 3)
    private Long inRangeLong = -3L;

    @InRange(min = 2, max = 2)
    private Integer nullValue = null;

    @InRange(min = -1, max = 300)
    private short inRangeShort = 4;

    private Collection<@InRange(min = 2, max = 3) Integer> inRangeCollection =
            Set.of(1, 2, 4);

    private List<@InRange(min = -1, max = 5) Integer> inRangeList = List.of(-2, 2, -4);

    private Map<@InRange(min = 10, max = 30) Integer, Short> inRangeMapKeys =
            Map.of(-1, (short) -2, 3, (short) -4);

    private Map<Integer, @InRange(min = -3, max = -3) Integer> inRangeMapValues =
            Map.of(-2, -3, -5, -3);

    private List<List<List<@InRange(min = 1, max = 3) Integer>>> inRangeInnerList =
            List.of(List.of(List.of(1, -2, 3)), List.of(List.of(1, 2, -3)));
}
