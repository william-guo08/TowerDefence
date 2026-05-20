package com.example.towerdefence;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;

import static javafx.application.Application.launch;


public class App{
    int canvas_width = 800;
    int canvas_height = 600;

    //List<Truppa> truppe = new ArrayList<>();
    List<Nemico> nemici = new ArrayList<>();
    Path percorso;

    double lastTime = 0;
    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(canvas_width, canvas_height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //crea lista immutabile di punti di svolta o start/end
        percorso = new Path(List.of(new Point2D(0,300)));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime= now;
                    return;
                };
                double delta = (now-lastTime)/1_000_000_000.0;
                lastTime = now;
                update(delta);
                render(gc, canvas_width, canvas_height);

            }
        };
    };

    private void render(GraphicsContext gc, int W, int H) {
        gc.setFill(Color.rgb(20, 20, 35));
        gc.fillRect(0, 0, W, H);

        path.draw(gc);
        for (Nemico n : enemies) n.draw(gc);
        for (Truppa t : truppe) t.draw(gc);
        drawHUD(gc, W);

        // Schermata game over
        if (pcHealth <= 0) {
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
        gc.fillText("PC Health: " + pcHealth, 120, 22);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Nemico: " + enemies.size() + "  Spawn: " + waveCount + "/" + MAX_ENEMIES, 260, 22);
    };

    private void update(double delta) {
        if (pcHealth <= 0) return;

        // Spawna nemici a intervalli regolari
        spawnTimer += delta;
        if (spawnTimer >= SPAWN_INTERVAL && waveCount < MAX_ENEMIES) {
            spawnEnemy();
            spawnTimer = 0;
            waveCount++;
        }

        // Muovi nemici e rimuovi quelli morti/arrivati
        Iterator<Nemico> it = enemies.iterator();
        while (it.hasNext()) {
            Nemico n = it.next();

            if (!n.isAlive()) {
                n.defeat();
                ram += n.getDrop();
                it.remove();
                continue;
            }

            boolean reached = n.walk(path, delta);
            if (reached) {
                pcHealth--;
                it.remove();
                System.out.println("Nemico arrivato! PC health: " + pcHealth);
            }
        }

        // Truppa attaccano il nemico più vicino
        for (Truppa t : truppe) {
            if (!t.isAlive()) continue;
            Nemico bersaglio = t.target(enemies);
            t.attack(bersaglio, delta);
        }
    }
    private void spawnEnemy() {
        Nemico n = switch (waveCount % 4) {
            case 0 -> new Nemico( 60,  80,  5, 10, new String[]{},        "Worm");
            case 1 -> new Nemico(120,  50,  8, 20, new String[]{"slow"},   "Trojan");
            case 2 -> new Nemico( 80,  60,  6, 15, new String[]{"block"},  "Adware");
            default-> new Nemico(200,  40, 12, 30, new String[]{"lock"},   "Ransomware");
        };
        enemies.add(n);
        System.out.println("Spawn: " + n.getRole());
    }


    public static void main(String[] args) {
        launch(args);
    }
}