package org.example.model.entities;

public class Pirate {
    private int lives;
    private final Backpack backpack;
    private final int maxLives;

    public Pirate(int lives, Backpack backpack, int maxLives) {
        this.lives = lives;
        this.backpack = backpack;
        this.maxLives = maxLives;
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

    public void setCurrentLives(int lives) {
        if(lives > this.maxLives){
            throw new IllegalArgumentException("max lives exception");
        }
        this.lives = lives;
    }

}