package com.example.birdsGameEx2.Models;

public class Game {
    private int speed = 0;
    private int mode = 0;

    public Game() {

    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public int getSpeed() {
        return speed;
    }

}
