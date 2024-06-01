package org.example.model;

public class UserQueryResult {

    private String result;
    private boolean success;

    public UserQueryResult(String result, boolean success) {
        this.result = result;
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
