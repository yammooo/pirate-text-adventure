package org.example.commandparser;

import org.example.model.entities.enums.Action;

public class ParsedCommand {
    public Action action;
    public int id;

    public ParsedCommand(Action action, int id) {
        this.action = action;
        this.id = id;
    }
}