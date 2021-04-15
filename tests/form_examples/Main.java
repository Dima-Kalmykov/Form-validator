package tests.form_examples;

import solution.annotations.*;
import solution.validators.ValidationError;
import solution.validators.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Constrained
public class Main {

    public static Set<ValidationError> getErrors(Validator validator) {
        List<GuestForm> guests = List.of(
                new GuestForm(null,  "Def", 21, "my@gmail.com"),
                new GuestForm("", "Kalmykov", 11, "er@edu.hse.ru"),
                new GuestForm("Dima", "Kalmykov", 8, "dadaya@edu.hse.ru"),
                new GuestForm("Polina", "Renova", 88, "furmanov@edu.hse.ru"),
                new GuestForm("Anna", "   ", 60, "perut@edu.hse.ru")
        );
        BookingForm bookingForm = new BookingForm(
                guests,
                List.of("TV", "Piano", "Kola", "room"),
                "House", 3, Map.of(2, 0, 1, 2)
        );

        return validator.validate(bookingForm);
    }
}
