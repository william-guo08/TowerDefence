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

    /**
     * Attacca il nemico bersaglio se il cooldown è esaurito.
     * @param enemy  nemico da colpire
     * @param delta  secondi trascorsi dall'ultimo frame
     */
    public void attack(Nemico enemy, double delta) {
        if (enemy == null) return;
        cooldown -= delta;
        if (cooldown <= 0) {
            enemy.takeDamage(atk);
            cooldown = 1.0 / atkSpeed;
        }
    }

    /** Rimuove la torretta (rimborso/vendita). */
    public void sold() {
        health = 0;
        System.out.println(role + " venduta per " + (value / 2) + " RAM.");
    }

    /** Disegna la torretta sul canvas. */
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.STEELBLUE);
        gc.fillRect(position.getX() - 18, position.getY() - 18, 36, 36);
        gc.setFill(Color.WHITE);
        gc.fillText(role, position.getX() - 16, position.getY() + 4);
    }




}
