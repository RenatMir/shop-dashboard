package com.shopdashboardservice.validation;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void beforeClass() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validateCorrectJsonTest() {
        TestJson json = new TestJson()
                .setJson("{ \"testField\" : \"value\"}");

        Set<ConstraintViolation<TestJson>> errors = validator.validate(json);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void validateNullJsonFieldTest() {
        TestJson json = new TestJson();
        Set<ConstraintViolation<TestJson>> errors = validator.validate(json);
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    public void validateInvalidJsonTest() {
        TestJson json = new TestJson()
                .setJson(" \"testField\" : \"value\"");

        Set<ConstraintViolation<TestJson>> errors = validator.validate(json);
        assertEquals(1, errors.size());
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    private static class TestJson {
        @JsonValidation
        private String json;
    }
}
