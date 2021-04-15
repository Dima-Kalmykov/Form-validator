package tests.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import solution.utils.MessageBuilder;
import solution.validators.ErrorContent;
import solution.validators.ObjectValidator;
import solution.validators.ValidationError;
import solution.validators.supporting_validators.*;
import tests.form_examples.Main;
import tests.validators.test_forms.MainForm;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Object validator test")
public class ObjectValidatorTest {

    private boolean setContainsError(ValidationError expectedError) {
        for (var value : AnnotationValidator.getErrorSet()) {
            if (value.getPath().equals(expectedError.getPath()) &&
                    value.getFailedValue().toString().equals((
                            expectedError.getFailedValue()).toString()) &&
                    value.getMessage().equals(expectedError.getMessage())) {
                return true;
            }
        }

        return false;
    }

    @Test
    @DisplayName("Validate form test")
    void validateMainForm() {
        ObjectValidator validator = new ObjectValidator();
        var errorSet = validator.validate(new MainForm());
        checkAnyOf();
        checkInRange();
        checkNegative();
        checkNotBlank();
        checkNotEmpty();
        checkNotNull();
        checkPositive();
        checkSize();
        assertEquals(57, errorSet.size());
    }

    @Test
    @DisplayName("Validate example form")
    void validateExample() {
        ObjectValidator validator = new ObjectValidator();
        var errorSet = Main.getErrors(validator);
        var messages = List.of(
                "must be one of \"TV\", \"Kitchen\", \"Toilet\", \"room\"",
                "size must be in range between 1 and 100",
                "value must be in range between 10 and 80",
                "must be not blank",
                "must be not blank",
                "must be not null",
                "value must be in range between 10 and 80",
                "must be one of \"TV\", \"Kitchen\", \"Toilet\", \"room\"",
                "value must be in range between 1 and 3"
        );

        var values = List.of("Piano", "", "8", "   ", "", "null", "88", "Kola", "0");

        var paths = List.of(
                "/amenities/[1]/", "/guests/[1]/firstName/", "/guests/[2]/age/",
                "/guests/[4]/lastName/", "/guests/[1]/firstName/", "/guests/[0]/firstName/",
                "/guests/[3]/age/", "/amenities/[2]/", "/peopleInRoom/[some value index]/"
        );

        for (int i = 0; i < messages.size(); i++) {
            assertTrue(setContainsError(new ErrorContent(
                    messages.get(i), paths.get(i), values.get(i)
            )));
        }

        assertEquals(9, errorSet.size());
    }

    private void checkAnyOf() {
        var messages =
                List.of(getMessage("2", "4"), getMessage("2", "3"),
                        getMessage("2", "3"), getMessage("1", "2"),
                        getMessage("12", "21"), getMessage("1", "2"),
                        getMessage("hello"), getMessage("12", "23"));

        var values = List.of("1", "4", "6", "3", "", "4", "world", "123");
        var paths = List.of(
                "/set/[some index]/", "/mapValues/[some value index]/",
                "/mapValues/[some value index]/", "/list/[1]/", "/emptyString/",
                "/list/[2]/", "/innerList/[0]/[1]/", "/invalidString/");

        paths = paths.parallelStream().map(p -> "/anyOfForm" + p).collect(Collectors.toList());

        for (var i = 0; i < messages.size(); ++i) {
            assertTrue(setContainsError(new ErrorContent(
                    messages.get(i), paths.get(i), values.get(i))));
        }
    }

    private void checkInRange() {
        var messages =
                List.of(getMessageValue(0, 3), getMessageValue(10, 30),
                        getMessageValue(2, 10), getMessageValue(10, 30),
                        getMessageValue(2, 3), getMessageValue(-1, 5),
                        getMessageValue(2, 3), getMessageValue(-1, 5),
                        getMessageValue(1, 3), getMessageValue(1, 3),
                        getMessageValue(2, 10));

        var values = List.of(-3, -1, 1, 3, 1, -4, 4, -2, -2, -3, -2);
        var paths = List.of(
                "/inRangeLong/", "/inRangeMapKeys/[some key index]/",
                "/inRangeSet/[some index]/", "/inRangeMapKeys/[some key index]/",
                "/inRangeCollection/[some index]/", "/inRangeList/[2]/",
                "/inRangeCollection/[some index]/", "/inRangeList/[0]/",
                "/inRangeInnerList/[0]/[0]/[1]/", "/inRangeInnerList/[1]/[0]/[2]/",
                "/inRangeSet/[some index]/");

        paths = paths.parallelStream().map(p -> "/inRangeForm/[0]/[0]" + p)
                .collect(Collectors.toList());

        for (var i = 0; i < messages.size(); i++) {
            assertTrue(setContainsError(new ErrorContent(
                    messages.get(i), paths.get(i), values.get(i)
            )));
        }
    }

    private void checkNegative() {
        var messages = Collections.nCopies(9, "must be negative");

        var values = List.of(2, 4, 2, 1, 4, 3, 1, 1, 3);

        var paths = List.of(
                "/negativeList/[1]/", "/negativeShort/",
                "/negativeInnerList/[1]/[0]/[1]/", "/negativeInnerList/[0]/[0]/[0]/",
                "/negativeSet/[some index]/", "/negativeMapKeys/[some key index]/",
                "/negativeSet/[some index]/", "/negativeInnerList/[1]/[0]/[0]/",
                "/negativeInnerList/[0]/[0]/[2]/");

        paths = paths.parallelStream().map(p -> "/negativeForm" + p).collect(Collectors.toList());

        for (var i = 0; i < messages.size(); i++) {
            assertTrue(setContainsError(new ErrorContent(
                    messages.get(i), paths.get(i), values.get(i)
            )));
        }

    }

    private void checkNotBlank() {
        var messages = Collections.nCopies(4, "must be not blank");

        var values = List.of("\"\"", "\" \"", "\" \"", "\"     \"");
        var paths = List.of(
                "/emptyString/", "/list/[0]/",
                "/map/[some key index]/", "/set/[some index]/");

        paths = paths.parallelStream().map(p -> "/notBlankForms/[some index]" + p)
                .collect(Collectors.toList());

        for (var i = 0; i < messages.size(); i++) {
            assertTrue(setContainsError(new ErrorContent(
                    messages.get(i), paths.get(i), values.get(i)
            )));
        }
    }

    private void checkNotEmpty() {
        var messages = Collections.nCopies(3, "must be not empty");

        var values = List.of("\"\"", "[]", "[]");
        var paths = List.of("/emptyString/", "/list/", "/set/");

        paths = paths.parallelStream().map(p -> "/notEmptyForm/[some value index]" + p)
                .collect(Collectors.toList());

        for (var i = 0; i < messages.size(); i++) {
            assertTrue(setContainsError(new ErrorContent(
                    messages.get(i), paths.get(i), values.get(i)
            )));
        }
    }

    private void checkPositive() {
        var messages = Collections.nCopies(9, "must be positive");

        var values = List.of(-4, -2, -3, -3, -2, -1, -3, -2, -3);

        var paths = List.of(
                "/positiveList/[2]/", "/positiveList/[0]/",
                "/positiveInnerList/[1]/[0]/[2]/", "/positiveMapValues/[some value index]/",
                "/positiveSet/[some index]/", "/positiveMapKeys/[some key index]/",
                "/positiveLong/", "/positiveInnerList/[0]/[0]/[1]/",
                "/positiveMapValues/[some value index]/");

        paths = paths.parallelStream().map(p -> "/positiveForm" + p).collect(Collectors.toList());

        for (var i = 0; i < messages.size(); i++) {
            assertTrue(setContainsError(new ErrorContent(
                    messages.get(i), paths.get(i), values.get(i)
            )));
        }
    }

    private void checkSize() {
        var messages =
                List.of(getMessageSize(2, 2), getMessageSize(2, 4),
                        getMessageSize(2, 3), getMessageSize(1, 2),
                        getMessageSize(10, 13));

        var values = List.of("\"\"", "2", Set.of(2, 5, 24, "sdf"),
                "[1, 2, 3]", "[[1, 2, 3], [3, 1]]");

        var paths = List.of(
                "/emptyString/", "/collection/[some index]/",
                "/set/", "/list/[0]/", "/list/");

        paths = paths.parallelStream().map(p -> "/sizeForm" + p).collect(Collectors.toList());

        for (var i = 0; i < messages.size(); i++) {
            assertTrue(setContainsError(new ErrorContent(
                    messages.get(i), paths.get(i), values.get(i)
            )));
        }
    }

    private void checkNotNull() {
        var messages = Collections.nCopies(4, "must be not null");
        var values = Collections.nCopies(4, "null");
        var paths = List.of(
                "/list/", "/innerList/[0]/",
                "/nullString/", "/integer/");

        paths = paths.parallelStream().map(p -> "/notNullForm" + p).
                collect(Collectors.toList());

        for (int i = 0; i < messages.size(); i++) {
            assertTrue(setContainsError(new ErrorContent(
                    messages.get(i), paths.get(i), values.get(i)
            )));
        }
    }

    private String getMessage(String... values) {
        return MessageBuilder.getErrorMessage(values);
    }

    private String getMessageValue(long min, long max) {
        return MessageBuilder.getErrorMessage(min, max, "value");
    }

    private String getMessageSize(long min, long max) {
        return MessageBuilder.getErrorMessage(min, max, "size");
    }
}