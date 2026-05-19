package com.example.towerdefence;

import javafx.scene.canvas.GraphicsContext;

public class Nemico {
    private int health;
    private double speed;
    private int atk;
    private int goldDrop;

    Nemico(int health, int atk, double speed, int goldDrop) {
        this.atk = atk;
        this.health = health;
        this.speed = speed;
        this.goldDrop = goldDrop;
    }

    public void walk() {

    }

    public void draw(GraphicsContext gc){

    }

    public void attack() {

    }

    public void defeat() {

    }
}
