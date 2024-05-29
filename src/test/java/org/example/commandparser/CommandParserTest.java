package org.example.commandparser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {

    private CommandParser commandParser;

    @BeforeEach
    public void setUp() {
        commandParser = new CommandParser();
    }

    @Test
    public void parseMoveCommand() {
        ParsedCommand parsedCommand = commandParser.parse("move 1");
        assertEquals("move", parsedCommand.action);
        assertEquals(1, parsedCommand.id);
    }

    @Test
    public void parsePickUpCommand() {
        ParsedCommand parsedCommand = commandParser.parse("pick 1");
        assertEquals("pick", parsedCommand.action);
        assertEquals(1, parsedCommand.id);
    }

    @Test
    public void parseUseCommand() {
        ParsedCommand parsedCommand = commandParser.parse("use 1");
        assertEquals("use", parsedCommand.action);
        assertEquals(1, parsedCommand.id);
    }

    @Test
    public void parseDropCommand() {
        ParsedCommand parsedCommand = commandParser.parse("drop 1");
        assertEquals("drop", parsedCommand.action);
        assertEquals(1, parsedCommand.id);
    }

    @Test
    public void parseStartNewGameCommand() {
        ParsedCommand parsedCommand = commandParser.parse("start new game");
        assertEquals("start new game", parsedCommand.action);
        assertEquals(-1, parsedCommand.id);
    }

    @Test
    public void parseLoadGameCommand() {
        ParsedCommand parsedCommand = commandParser.parse("load game 1");
        assertEquals("load game", parsedCommand.action);
        assertEquals(1, parsedCommand.id);
    }

    @Test
    public void parseInvalidCommand() {
        assertThrows(IllegalArgumentException.class, () -> commandParser.parse("invalid command"));
    }

    @Test
    public void parseCommandWithInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> commandParser.parse("move"));
    }

    @Test
    public void parseCommandWithNullString() {
        assertThrows(IllegalArgumentException.class, () -> commandParser.parse(null));
    }

    @Test
    public void parseCommandWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> commandParser.parse(""));
    }
}