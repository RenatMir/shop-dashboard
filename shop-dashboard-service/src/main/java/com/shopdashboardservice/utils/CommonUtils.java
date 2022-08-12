package com.shopdashboardservice.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class CommonUtils {

    public static boolean isJSONValid(String jsonInString) {
        if(jsonInString == null)
            return true;

        boolean isJSONValid = true;

        try{
            new JSONObject(jsonInString);
        }catch (JSONException exception) {
            isJSONValid = false;
        }
        return isJSONValid;
    }
}
