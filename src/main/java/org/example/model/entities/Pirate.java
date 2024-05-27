package org.example.model.entities;

public class Pirate {
    private int lives;
    private Backpack backpack;
    private int max_lives;

    public Pirate(int lives, Backpack backpack, int max_lives) {
        this.lives = lives;
        this.backpack = backpack;
        this.max_lives = max_lives;
    }

    public int getMaxLives() {
        return this.max_lives;
    }

    public int getCurrentLives() {
        return lives;
    }

    public void setCurrentLives(int lives) {
        if(lives > this.max_lives){
            throw new IllegalArgumentException("max lives exception");
        }
        this.lives = lives;
    }

    public void setMaxLives(int max_lives) {
        this.max_lives = max_lives;
    }

    public Backpack getBackpack() {
        return backpack;
    }

}

