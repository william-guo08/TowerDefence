package com.example.towerdefence;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
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

        double rimanente = distanzaPercorsa;

        for (int i = 0; i < waypoints.size()-1; i++){
            Point2D a = waypoints.get(i);
            Point2D b = waypoints.get(i + 1);
            double segLen = a.distance(b);

            if (rimanente <= segLen) {
                double t = rimanente / segLen;
                return a.interpolate(b, t);
            }
            rimanente -= segLen;
        }

        return wayOut;
    }

    public Point2D getDirezione(double distanceTravelled) {
        if (waypoints.size() < 2) return new Point2D(1, 0);

        double remaining = distanceTravelled;

        for (int i = 0; i < waypoints.size() - 1; i++) {
            Point2D a = waypoints.get(i);
            Point2D b = waypoints.get(i + 1);
            double segLen = a.distance(b);

            if (remaining <= segLen) {
                double dx = b.getX() - a.getX();
                double dy = b.getY() - a.getY();
                return new Point2D(dx / segLen, dy / segLen);
            }
            remaining -= segLen;
        }

        Point2D a = waypoints.get(waypoints.size() - 2);
        Point2D b = waypoints.get(waypoints.size() - 1);
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        double len = a.distance(b);
        return new Point2D(dx / len, dy / len);
    }

    public void draw(GraphicsContext gc) {
        if (waypoints.size() < 2) return;

        // Track background
        gc.setStroke(Color.rgb(60, 60, 80));
        gc.setLineWidth(36);
        drawPolyline(gc);

        // Track border
        gc.setStroke(Color.rgb(90, 90, 120));
        gc.setLineWidth(40);
        drawPolyline(gc);

        // Track surface
        gc.setStroke(Color.rgb(50, 50, 70));
        gc.setLineWidth(34);
        drawPolyline(gc);

        // Waypoint markers
        gc.setFill(Color.rgb(100, 100, 140, 0.6));
        for (Point2D p : waypoints) {
            gc.fillOval(p.getX() - 6, p.getY() - 6, 12, 12);
        }

        // Entry / exit markers
        if (wayIn != null) {
            gc.setFill(Color.LIMEGREEN);
            gc.fillOval(wayIn.getX() - 8, wayIn.getY() - 8, 16, 16);
        }
        if (wayOut != null) {
            gc.setFill(Color.RED);
            gc.fillOval(wayOut.getX() - 8, wayOut.getY() - 8, 16, 16);
        }
    }

    public Point2D getWayIn()  { return wayIn; }
    public Point2D getWayOut() { return wayOut; }

    public List<Point2D> getWaypoints() {
        return Collections.unmodifiableList(waypoints);
    }

    public int getWaypointCount() { return waypoints.size(); }


    private void drawPolyline(GraphicsContext gc) {
        gc.beginPath();
        gc.moveTo(waypoints.get(0).getX(), waypoints.get(0).getY());
        for (int i = 1; i < waypoints.size(); i++) {
            gc.lineTo(waypoints.get(i).getX(), waypoints.get(i).getY());
        }
        gc.stroke();
    }
}
}
