package com.example.towerdefence;
import javafx.scene.image.Image;

import javafx.geometry.Point2D;

public class Firewall extends Truppa{
    public Firewall(int atk, double atkSpeed, Point2D position) {
        super(atk, atkSpeed,  "Firewall", position);
        try {
            this.sprite = new Image(getClass().getResourceAsStream("/com/example/towerdefence/firewall.png"));
        } catch (Exception e) {
            System.out.println("Sprite per 'Firewall' non trovata");
        }
    };

}
