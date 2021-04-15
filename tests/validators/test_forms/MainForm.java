package tests.validators.test_forms;

import org.junit.jupiter.api.Nested;
import solution.annotations.Constrained;
import solution.annotations.Negative;
import solution.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Constrained
public class MainForm {
    private PositiveForm positiveForm = new PositiveForm();

    private UnconstrainedForm unconstrainedForm = new UnconstrainedForm();

    private NegativeForm negativeForm = new NegativeForm();

    private List<List<InRangeForm>> inRangeForm = List.of(List.of(new InRangeForm()));

    private Set<NotBlankForm> notBlankForms = Set.of(new NotBlankForm(), new NotBlankForm());

    private AnyOfForm anyOfForm = new AnyOfForm();

    private Map<Integer, NotEmptyForm> notEmptyForm = Map.of(1, new NotEmptyForm());

    private SizeForm sizeForm = new SizeForm();

    private NotNullForm notNullForm = new NotNullForm();
}
