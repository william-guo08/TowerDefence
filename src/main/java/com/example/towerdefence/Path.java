package com.example.towerdefence;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private Point2D wayIn;
    private Point2D wayOut;
    private List<Point2D> waypoints;

    public Path(){
        this.waypoints = new ArrayList<>();
    }

    public Path(List<Point2D> waypoints){
        if (waypoints == null || waypoints.size() <2) {
            throw new IllegalArgumentException("Il percorso non ha un inizio o fine");
        }
        this.waypoints = new ArrayList<>(waypoints);
        this.wayIn = waypoints.get(0);
        this.wayOut = waypoints.get(waypoints.size() -1);
    }

    public void aggiungiWaypoint(Point2D punto) {
        waypoints.add(punto);
        if (waypoints.size() == 1) wayIn = punto;
        wayOut = punto;
    }

    public double lunghezzaTot() {
        double length = 0;
        for (int i = 0; i < waypoints.size()-1;i++){
            length += waypoints.get(i).distance(waypoints.get(i+1));
        }
        return length;
    }

    public Point2D getPosizione(double distanzaPercorsa) {
        if (waypoints.isEmpty()) return Point2D.ZERO;
    }
}
