package com.example.towerdefence;
import javafx.scene.image.Image;

import javafx.geometry.Point2D;

public class Sandbox extends Truppa{
    public Sandbox(int atk, double atkSpeed, Point2D position) {
        super(atk, atkSpeed,  "Sandbox", position);
        try {
            this.sprite = new Image(getClass().getResourceAsStream("/com/example/towerdefence/firewall.png"));
        } catch (Exception e) {
            System.out.println("Sprite per 'Sandbox' non trovata");
        }
    };

}
