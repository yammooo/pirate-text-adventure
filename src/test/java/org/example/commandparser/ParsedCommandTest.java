package org.example.commandparser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParsedCommandTest {

    @Test
    public void parsedCommandStoresCorrectAction() {
        ParsedCommand command = new ParsedCommand("move", 1);
        assertEquals("move", command.action);
    }

    @Test
    public void parsedCommandStoresCorrectId() {
        ParsedCommand command = new ParsedCommand("move", 1);
        assertEquals(1, command.id);
    }

    @Test
    public void parsedCommandStoresCorrectActionAndId() {
        ParsedCommand command = new ParsedCommand("pick up", 2);
        assertEquals("pick up", command.action);
        assertEquals(2, command.id);
    }

    @Test
    public void parsedCommandWithNegativeId() {
        ParsedCommand command = new ParsedCommand("move", -1);
        assertEquals("move", command.action);
        assertEquals(-1, command.id);
    }

    @Test
    public void parsedCommandWithZeroId() {
        ParsedCommand command = new ParsedCommand("pick up", 0);
        assertEquals("pick up", command.action);
        assertEquals(0, command.id);
    }
}