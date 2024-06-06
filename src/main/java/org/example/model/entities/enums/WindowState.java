package org.example.model.entities.enums;

/**
 * The WindowState enum represents the different states that the game window can be in.
 * The game window can be in one of the following states:
 * MENU: The game is in the main menu.
 * GAME: The game is in progress.
 * GAME_OVER: The game has ended because the player has lost.
 * GAME_WIN: The game has ended because the player has won.
 */
public enum WindowState {
    MENU,
    GAME,
    GAME_OVER,
    GAME_WIN
}