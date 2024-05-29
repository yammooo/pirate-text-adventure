package org.example.view.handlers;

import org.example.commandparser.CommandParser;
import org.example.commandparser.ParsedCommand;
import org.example.view.panels.CommandPanel;
import org.example.command.*;

public class CommandPanelHandler {

    private CommandPanel commandPanel;
    private CommandParser parser = new CommandParser();


    public CommandPanelHandler(CommandPanel commandPanel) {
        this.commandPanel = commandPanel;
    }

    public void handleCommand(String command) {
        try {
            ParsedCommand parsedCommand = parser.parse(command);
            Command cmd = createCommand(parsedCommand);
            cmd.execute(this);
        } catch (IllegalArgumentException e) {
            showMessage("Invalid command: " + e.getMessage());
        }
    }

    private Command createCommand(ParsedCommand parsedCommand) {
        switch (parsedCommand.action) {
            case "move":
                return new MoveCommand(parsedCommand.id);
            case "pick":
                return new PickUpItemCommand(parsedCommand.id);
            case "use":
                return new UseItemCommand(parsedCommand.id);
            case "drop":
                return new DropItemCommand(parsedCommand.id);
            case "start new game":
                return new StartNewGameCommand();
            case "load game":
                return new LoadGameCommand(parsedCommand.id);
            case "exit to menu":
                return new ExitToMenuCommand();
            case "get help":
                return new GetHelpCommand();
            case "get description":
                return new GetDescriptionCommand(parsedCommand.id);
            case "get dialogue":
                return new GetDialogueCommand(parsedCommand.id);
            default:
                throw new IllegalArgumentException("Unknown action: " + parsedCommand.action);
        }
    }

    public void showMessage(String message) {
        commandPanel.showMessage("System\t> " + message + "\n");
    }

}