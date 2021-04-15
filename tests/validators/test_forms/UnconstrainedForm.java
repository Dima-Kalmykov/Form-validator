package tests.validators.test_forms;


import solution.annotations.Positive;

public class UnconstrainedForm {

    @Positive
    private int nonPositive = -3;
}
