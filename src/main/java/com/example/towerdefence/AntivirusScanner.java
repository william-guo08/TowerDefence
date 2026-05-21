package com.example.towerdefence;
import javafx.scene.image.Image;

import javafx.geometry.Point2D;

public class AntivirusScanner extends Truppa{
    public AntivirusScanner(int atk, double atkSpeed, Point2D position) {
        super(atk, atkSpeed,  "AntivirusScanner", position);
        try {
            this.sprite = new Image(getClass().getResourceAsStream("/com/example/towerdefence/scanner.png"));
        } catch (Exception e) {
            System.out.println("Sprite per 'AntivirusScanner' non trovata");
        }
    };
};