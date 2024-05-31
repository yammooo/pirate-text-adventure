package org.example.view.handlers;

import org.example.commandparser.CommandParser;
import org.example.commandparser.ParsedCommand;
import org.example.model.entities.enums.Action;
import org.example.view.panels.CommandPanel;
import org.example.command.*;

public class CommandPanelHandler {

    private final CommandPanel commandPanel;
    private final CommandParser parser = new CommandParser();


    public CommandPanelHandler(CommandPanel commandPanel) {
        this.commandPanel = commandPanel;
    }

    public void handleCommand(String command) {
        try {
            ParsedCommand parsedCommand = parser.parse(command);
            Command cmd = createCommand(parsedCommand);
            cmd.execute();
        } catch (IllegalArgumentException e) {
            showMessage("Invalid command: " + e.getMessage());
        }
    }

    private Command createCommand(ParsedCommand parsedCommand) {
        return switch (parsedCommand.action) {
            case Action.MOVE -> new MoveCommand(parsedCommand.id);
            case Action.PICK -> new PickUpItemCommand(parsedCommand.id);
            case Action.DROP -> new DropItemCommand(parsedCommand.id);
            case Action.START_NEW_GAME -> new StartNewGameCommand();
            case Action.LOAD_GAME -> new LoadGameCommand(parsedCommand.id);
            case Action.EXIT_TO_MENU -> new ExitToMenuCommand();
            case Action.GET_HELP -> new GetHelpCommand();
            case Action.VIEW_ENTITY -> new ViewEntityCommand(parsedCommand.id);
            case Action.GET_DIALOGUE -> new GetDialogueCommand(parsedCommand.id);
        };
    }

    public void showMessage(String message) {
        commandPanel.showMessage("System\t> " + message + "\n");
    }

}