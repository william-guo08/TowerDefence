package com.example.towerdefence;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import static javafx.application.Application.launch;


public class App extends Application{
    int canvas_width = 800;
    int canvas_height = 600;
    int vite = 5;
    int ram = 100;
    double spawn = 0;

    List<Truppa> truppe = new ArrayList<>();
    List<Nemico> nemici = new ArrayList<>();
    Path percorso;
    int waveCount = 0;
    int MAX_ENEMIES = 30;
    double SPAWN_INTERVAL = (1*Math.random()*5);

    double lastTime = 0;

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(canvas_width, canvas_height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //crea lista immutabile di punti di svolta o start/end
        percorso = new Path(List.of(
                new Point2D(  0, 300),
                new Point2D(150, 300),
                new Point2D(150, 150),
                new Point2D(400, 150),
                new Point2D(400, 450),
                new Point2D(650, 450),
                new Point2D(650, 300),
                new Point2D(800, 300)));
        //3 truppe aggiunte al percorso
        truppe.add(new Truppa(100, 15, 1.0, 30, "Firewall", new Point2D(270, 150)));
        truppe.add(new Truppa(80, 25, 1.5, 40, "AntivirusScanner", new Point2D(400, 310)));
        truppe.add(new Truppa(60, 10, 0.8, 20, "Sandbox", new Point2D(530, 450)));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                };
                double delta = (now-lastTime)/1_000_000_000;
                lastTime = now;
                update(delta);
                render(gc, canvas_width, canvas_height);

            }
        };
        timer.start();
        javafx.scene.Group root = new Group(canvas);
        stage.setScene(new Scene(root, canvas_width, canvas_height));
        stage.setTitle("Tower Defence");
        stage.show();
    };

    private void render(GraphicsContext gc, int W, int H) {
        gc.setFill(Color.rgb(20, 20, 35));
        gc.fillRect(0, 0, W, H);

        percorso.draw(gc);
        for (Nemico n : nemici) n.draw(gc);     //disegna i nemici
        for (Truppa t : truppe) t.draw(gc);     //disegna le truppe
        drawHUD(gc, W);

        // Schermata game over
        if (vite <= 0) {
            gc.setFill(Color.rgb(0, 0, 0, 0.6));
            gc.fillRect(0, 0, W, H);
            gc.setFill(Color.RED);
            gc.setFont(javafx.scene.text.Font.font(48));
            gc.fillText("GAME OVER", W / 2.0 - 120, H / 2.0);
        }
    };
    private void drawHUD(GraphicsContext gc, int W) {
        gc.setFill(Color.rgb(10, 10, 20, 0.75));
        gc.fillRect(0, 0, W, 36);

        gc.setFont(javafx.scene.text.Font.font(14));

        gc.setFill(Color.LIMEGREEN);
        gc.fillText("RAM: " + ram, 10, 22);

        gc.setFill(Color.ORANGERED);
        gc.fillText("PC Health: " + vite, 120, 22);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Nemico: " + nemici.size() + "  Spawn: " + waveCount + "/" + MAX_ENEMIES, 260, 22);
    };

    private void update(double delta) {
        if (vite <= 0) return;

        // Spawna nemici a intervalli regolari
        spawn += delta;
        if (spawn >= SPAWN_INTERVAL && waveCount < MAX_ENEMIES) {
            spawnEnemy();
            spawn = 0;
            waveCount++;
        }

        // Muovi nemici e rimuovi quelli morti/arrivati
        Iterator<Nemico> it = nemici.iterator();
        while (it.hasNext()) {
            Nemico n = it.next();

            if (!n.isAlive()) {
                n.defeat();
                ram += n.getDrop();
                it.remove();
                continue;
            }

            boolean raggiunto = n.walk(percorso, delta);
            if (raggiunto) {
                vite--;
                it.remove();
                System.out.println("Nemico arrivato! PC health: " + vite);
            }
        }

        // Truppa attaccano il nemico più vicino
        for (Truppa t : truppe) {
            if (!t.isAlive()) continue;
            Nemico bersaglio = t.target(nemici);
            t.attack(bersaglio, delta);
        }
    }
    private void spawnEnemy() {
        Nemico n = switch (waveCount % 4) {
            case 0 -> new Nemico( 140,  80,  5, 10,"Worm");
            case 1 -> new Nemico(120,  50,  8, 20,"Trojan");
            case 2 -> new Nemico( 110,  60,  6, 15, "Adware");
            default-> new Nemico(200,  40, 12, 30, "Ransomware");
        };
        //int health, int atk, double speed, int goldDrop, String role
        nemici.add(n);
        System.out.println("Spawn: " + n.getRole());
    }


    public static void main(String[] args) {
        launch(args);
    }
}