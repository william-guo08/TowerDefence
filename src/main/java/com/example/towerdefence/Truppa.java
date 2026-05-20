package com.example.towerdefence;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Truppa {
    private int health;
    private int atk;
    private double atkSpeed;   // attacchi al secondo
    private int value;         // costo in RAM
    private String role;

    private Point2D position;
    private double cooldown = 0;


    public Truppa(int health, int atk,
                  double atkSpeed, int value, String role, Point2D position) {
        this.health   = health;
        this.atk      = atk;
        this.atkSpeed = atkSpeed;
        this.value    = value;
        this.role     = role;
        this.position = position;
    }


    //Cerca il nemico più vicino nella lista e lo restituisce (null se vuota).
    public Nemico target(java.util.List<Nemico> enemies) {
        Nemico nearest = null;
        double minDist = Double.MAX_VALUE;
        for (Nemico n : enemies) {
            double dist = position.distance(n.getPosition());
            if (dist < minDist) {
                minDist = dist;
                nearest = n;
            }
        }
        return nearest;
    }


}
