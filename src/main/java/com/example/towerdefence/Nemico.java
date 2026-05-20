package com.example.towerdefence;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    public boolean walk(Path path, double delta) {
        distanzaPercorsa += speed * (delta*5);
        posizione = path.getPosizione(distanzaPercorsa);

        if (distanzaPercorsa >= path.lunghezzaTot()) {
            alive = false;
            return true;   // ha raggiunto la fine
        }
        return false;
    }

    public void subisciDanno(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            alive  = false;
        }
    }

    //Chiamato quando il nemico viene eliminato: stampa il drop.
    public void defeat() {
        System.out.println(role + " sconfitto! +" + goldDrop + " RAM.");
    }

    public String getRole() {
        return role;
    };

    public void draw(GraphicsContext gc) {
        if (posizione == null || !alive) return;

        //disegna i nemici come palline arancioni
        gc.setFill(Color.ORANGERED);
        gc.fillOval(posizione.getX() - 12, posizione.getY() - 12, 24, 24);

        //Disegna barra della vita sopra i nemici
        double maxBarW = 28;
        double barW = maxBarW * ((double) health / getMaxHealth());
        gc.setFill(Color.DARKRED);
        gc.fillRect(posizione.getX() - 14, posizione.getY() - 20, maxBarW, 4);
        gc.setFill(Color.LIMEGREEN);
        gc.fillRect(posizione.getX() - 14, posizione.getY() - 20, barW, 4);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(posizione.getX()-14, posizione.getY()-20, maxBarW+1,5);

        gc.setFill(Color.WHITE);
        gc.fillText(role, posizione.getX() - 10, posizione.getY() + 4);
    };
    public boolean isAlive() { return alive; }


    public int getDrop(){ return goldDrop; }
    public Point2D getPosition(){ return posizione; }

    public void setHealth(int health) {
        this.health = health;
        if (this.health <= 0) alive = false;
    }

    // maxHealth memorizzato al primo accesso (semplice aiuto per la barra vita)
    private int maxHealth = -1;
    public int getMaxHealth() {
        if (maxHealth == -1) maxHealth = health;
        return maxHealth;
    }
}
