package org.example.model;

/**
 * Represents the result of a user query, including the result message and success status.
 */
public class UserQueryResult {

    private String result;
    private boolean success;

    /**
     * Constructs a UserQueryResult with the specified result message and success status.
     * @param result the result message of the user query
     * @param success the success status of the user query
     */
    public UserQueryResult(String result, boolean success) {
        this.result = result;
        this.success = success;
    }

    /**
     * Returns the result message of the user query.
     * @return the result message
     */
    public String getResult() {
        return result;
    }

    /**
     * Returns the success status of the user query.
     * @return true if the query was successful, false otherwise
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * Sets the result message of the user query.
     * @param result the new result message
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Sets the success status of the user query.
     * @param success the new success status
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
