package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserQueryResultTest {

    private UserQueryResult userQueryResult;

    @BeforeEach
    void setUp() {
        userQueryResult = new UserQueryResult("Result1", true);
    }


    @Test
    void setResultChangesResult() {
        userQueryResult.setResult("Result2");
        assertEquals("Result2", userQueryResult.getResult());
    }

    @Test
    void setSuccessChangesSuccess() {
        userQueryResult.setSuccess(false);
        assertFalse(userQueryResult.getSuccess());
    }
}