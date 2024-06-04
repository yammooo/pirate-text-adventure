package org.example.model.entities;

import org.example.exceptions.RunOutOfLivesException;

public class Pirate {
    private int lives;
    private final Backpack backpack;
    private final int maxLives;

    public Pirate(Backpack backpack, int maxLives) {
        this.backpack = backpack;
        this.maxLives = maxLives;
        this.lives = maxLives;
    }

    // Getters

    public int getMaxLives() {
        return this.maxLives;
    }

    public int getCurrentLives() {
        return lives;
    }

    public Backpack getBackpack() {
        return backpack;
    }


    // Setters

    public void setCurrentLives(int lives) throws  RunOutOfLivesException{
        if(lives <= 0){
            throw new RunOutOfLivesException("Pirate run out of lives");
        } else if (lives > maxLives){
            lives = maxLives;
        }
        this.lives = lives;
    }

}