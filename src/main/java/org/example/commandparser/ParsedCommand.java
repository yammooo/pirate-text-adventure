package org.example.commandparser;

public class ParsedCommand {
    public String action;
    public int id;

    public ParsedCommand(String action, int id) {
        this.action = action;
        this.id = id;
    }
}