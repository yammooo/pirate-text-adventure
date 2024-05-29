package org.example.commandparser;

import org.example.model.entities.enums.Action;

public class CommandParser {
    public ParsedCommand parse(String command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }

        String[] parts = command.split(" ");
        if (parts.length < 2 || parts.length > 3) {
            throw new IllegalArgumentException("Invalid command format");
        }

        String action = parts[0];
        int id = -1;
        if (isNumeric(parts[1])) {
            id = Integer.parseInt(parts[1]);
        } else if (parts.length > 2 && isNumeric(parts[2])) {
            id = Integer.parseInt(parts[2]);
        }

        switch (action.toLowerCase()) {
            case "move":
                return new ParsedCommand(Action.MOVE, id);
            case "pick":
                return new ParsedCommand(Action.PICK, id);
            case "use":
                return new ParsedCommand(Action.USE, id);
            case "drop":
                return new ParsedCommand(Action.DROP, id);
            case "start":
                if (parts[1].equalsIgnoreCase("new") && parts[2].equalsIgnoreCase("game")) {
                    return new ParsedCommand(Action.START_NEW_GAME, -1);
                }
                break;
            case "load":
                if (parts[1].equalsIgnoreCase("game")) {
                    return new ParsedCommand(Action.LOAD_GAME, id);
                }
                break;
            case "exit":
                if (parts[1].equalsIgnoreCase("to") && parts[2].equalsIgnoreCase("menu")) {
                    return new ParsedCommand(Action.EXIT_TO_MENU, -1);
                }
                break;
            case "get":
                if (parts[1].equalsIgnoreCase("help")) {
                    return new ParsedCommand(Action.GET_HELP, -1);
                } else if (parts[1].equalsIgnoreCase("description")) {
                    return new ParsedCommand(Action.GET_DESCRIPTION, id);
                } else if (parts[1].equalsIgnoreCase("dialogue")) {
                    return new ParsedCommand(Action.GET_DIALOGUE, id);
                }
                break;
        }
        throw new IllegalArgumentException("Unknown command");
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}