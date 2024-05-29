package org.example.commandparser;

public class CommandParser {
    public ParsedCommand parse(String command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }

        String[] parts = command.split(" ");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid command format");
        }

        String action = parts[0];
        int id = -1;
        if (parts.length > 1 && isNumeric(parts[1])) {
            id = Integer.parseInt(parts[1]);
        } else if (parts.length > 2 && isNumeric(parts[2])) {
            id = Integer.parseInt(parts[2]);
        }

        switch (action.toLowerCase()) {
            case "move", "drop", "pick", "use":
                return new ParsedCommand(action, id);
            case "start":
                if (parts[1].equalsIgnoreCase("new") && parts[2].equalsIgnoreCase("game")) {
                    return new ParsedCommand("start new game", -1);
                }
                break;
            case "load":
                if (parts[1].equalsIgnoreCase("game")) {
                    return new ParsedCommand("load game", id);
                }
                break;
            case "save":
                return new ParsedCommand("save game", -1);
            case "exit":
                if (parts[1].equalsIgnoreCase("to") && parts[2].equalsIgnoreCase("menu")) {
                    return new ParsedCommand("exit to menu", -1);
                }
                break;
            case "get":
                if (parts[1].equalsIgnoreCase("help")) {
                    return new ParsedCommand("get help", -1);
                } else if (parts[1].equalsIgnoreCase("description")) {
                    return new ParsedCommand("get description", id);
                } else if (parts[1].equalsIgnoreCase("dialogue")) {
                    return new ParsedCommand("get dialogue", id);
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