package org.example.commandparser;

import org.example.model.entities.enums.Action;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParsedCommandTest {

    @Test
    public void parsedCommandStoresCorrectAction() {
        ParsedCommand command = new ParsedCommand(Action.MOVE, 1);
        assertEquals(Action.MOVE, command.action);
    }

    @Test
    public void parsedCommandStoresCorrectId() {
        ParsedCommand command = new ParsedCommand(Action.MOVE, 1);
        assertEquals(1, command.id);
    }

    @Test
    public void parsedCommandStoresCorrectActionAndId() {
        ParsedCommand command = new ParsedCommand(Action.PICK, 2);
        assertEquals(Action.PICK, command.action);
        assertEquals(2, command.id);
    }

    @Test
    public void parsedCommandWithNegativeId() {
        ParsedCommand command = new ParsedCommand(Action.MOVE, -1);
        assertEquals(Action.MOVE, command.action);
        assertEquals(-1, command.id);
    }

    @Test
    public void parsedCommandWithZeroId() {
        ParsedCommand command = new ParsedCommand(Action.PICK, 0);
        assertEquals(Action.PICK, command.action);
        assertEquals(0, command.id);
    }
}