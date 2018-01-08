package com.udacity.gradle.builditbigger;

/**
 * Created by Hrishikesh Kadam on 08/01/2018
 */

public class CustomMessage {

    private boolean isSuccessful;
    private String result;

    public CustomMessage(boolean isSuccessful, String result) {
        this.isSuccessful = isSuccessful;
        this.result = result;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CustomMessage{" +
                "isSuccessful=" + isSuccessful +
                ", result='" + result + '\'' +
                '}';
    }
}
