package org.example.model.entities;

import org.example.exceptions.RunOutOfLivesException;

/**
 * The Pirate class represents the player character in the game.
 * The pirate has a certain number of lives and a backpack to carry items.
 * The pirate can lose lives and if it runs out of lives, the game is over.
 * The pirate can also carry items in its backpack up to a certain weight.
 */
public class Pirate {
    private int lives;
    private final Backpack backpack;
    private final int maxLives;

    /**
     * Constructs a new Pirate with the given backpack and maximum number of lives.
     *
     * @param backpack the backpack of the pirate
     * @param maxLives the maximum number of lives the pirate can have
     */
    public Pirate(Backpack backpack, int maxLives) {
        this.backpack = backpack;
        this.maxLives = maxLives;
        this.lives = maxLives;
    }

    // Getters

    /**
     * Returns the maximum number of lives the pirate can have.
     *
     * @return the maximum number of lives the pirate can have
     */
    public int getMaxLives() {
        return this.maxLives;
    }

    /**
     * Returns the current number of lives the pirate has.
     *
     * @return the current number of lives the pirate has
     */
    public int getCurrentLives() {
        return lives;
    }

    /**
     * Returns the backpack of the pirate.
     *
     * @return the backpack of the pirate
     */
    public Backpack getBackpack() {
        return backpack;
    }

    // Setters

    /**
     * Sets the current number of lives the pirate has.
     *
     * @param lives the new number of lives the pirate has
     * @throws RunOutOfLivesException if the new number of lives is less than or equal to zero
     */
    public void setCurrentLives(int lives) throws  RunOutOfLivesException{
        if(lives <= 0){
            throw new RunOutOfLivesException("Pirate run out of lives");
        } else if (lives > maxLives){
            lives = maxLives;
        }
        this.lives = lives;
    }

}