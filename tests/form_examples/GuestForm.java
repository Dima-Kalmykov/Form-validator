package tests.form_examples;

import solution.annotations.*;

@Constrained
public class GuestForm {

    @NotNull
    @NotBlank
    @Size(min=1, max=100)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min=1, max=100)
    private String lastName;

    @InRange(min=10, max=80)
    private int age;

    @NotEmpty
    @NotNull
    private String email;

    public GuestForm(String firstName, String lastName, int age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }
}
