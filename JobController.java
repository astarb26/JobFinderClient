package com.example.jobfinderclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class JobController extends Thread {

    @FXML
    private Button btn;

    @FXML
    private Canvas canv;

    private GraphicsContext gc;
    private boolean stop = false;
    private int x = 10, y = 10;
    final int SIZE = 90;

    @FXML
    public void initialize() {
        gc = canv.getGraphicsContext2D();
        start();
    }

    @Override
    public void run() {
        super.run();
        while(!stop) {
            final int SIZE = 40;
            gc.clearRect(0, 0, canv.getWidth(), canv.getHeight());
            gc.setFill(Color.RED);
            gc.fillOval(x, y, SIZE, SIZE);

            y++;
            x++;
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void btnPressed(ActionEvent event) {
        stop = true;
    }
}

