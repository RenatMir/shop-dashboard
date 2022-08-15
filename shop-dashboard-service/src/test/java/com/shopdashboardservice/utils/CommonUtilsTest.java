package com.shopdashboardservice.utils;

import com.shopdashboardservice.AbstractTest;
import org.junit.jupiter.api.Test;

import static com.shopdashboardservice.utils.CommonUtils.isJSONValid;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonUtilsTest extends AbstractTest {

    @Test
    public void isJSONValidTest() {
        String jsonInString = "{\"test\":\"value\"}";

        boolean jsonValidBool = isJSONValid(jsonInString);

        assertTrue(jsonValidBool);
    }

    @Test
    public void isJSONValidErrorTest() {
        String jsonInString = "\"test\":\"value\"";

        boolean jsonValidBool = isJSONValid(jsonInString);

        assertFalse(jsonValidBool);
    }
}
