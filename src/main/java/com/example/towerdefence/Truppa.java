package com.example.towerdefence;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class Truppa {
    protected int atk;
    protected double atkSpeed;   // attacchi al secondo
    protected String role;
    protected Point2D position;
    protected double cooldown = 0;
    protected Image sprite;

    public Truppa(int atk, double atkSpeed, String role, Point2D position) {
        this.atk      = atk;
        this.atkSpeed = atkSpeed;
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

    public void attack(Nemico enemy, double delta) {
        if (enemy == null) return;
        cooldown -= delta;
        if (cooldown <= 0) {
            enemy.subisciDanno(atk);
            cooldown = 1.0 / atkSpeed;
        }
    }


    //Disegna la torretta sul canvas.
    public void draw(GraphicsContext gc) {
        if (sprite != null) {
            gc.drawImage(sprite, position.getX() -18, position.getY() -18, 50,40);
        } else {
            gc.setFill(Color.STEELBLUE);
            gc.fillRect(position.getX() - 18, position.getY() - 18, 36, 36);
            gc.setFill(Color.WHITE);
            gc.fillText(role, position.getX() - 16, position.getY() + 4);
        }

    }

    public boolean isAlive() { return true; }

    public void setPosition(Point2D position) { this.position = position; }
    //public void setHealth(int health)         { this.health = health; }
}