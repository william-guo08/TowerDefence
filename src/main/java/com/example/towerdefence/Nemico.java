package com.example.towerdefence;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Nemico {
    private int health;
    private double speed;
    private int atk;
    private int goldDrop;
    private String role;
    private double distanzaPercorsa;
    private Point2D posizione;
    private boolean alive = true;

    Nemico(int health, int atk, double speed, int goldDrop, String role) {
        this.atk = atk;
        this.health = health;
        this.speed = speed;
        this.goldDrop = goldDrop;
        this.role = role;
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
