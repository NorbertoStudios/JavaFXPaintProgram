package com.norbertostudios;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;

public class Controller
{
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Slider slider;
    @FXML
    private Label brushSize;

    public boolean isErasing =false;

    public void initialize() {

        GraphicsContext g = canvas.getGraphicsContext2D();

        colorPicker.setValue(Color.BLACK);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            brushSize.setText(Double.toString(newValue.intValue()));
        });

        canvas.setOnMouseDragged(e -> {
            double size = slider.getValue();
            double x = e.getX();
            double y = e.getY();

            if (isErasing) {
               g.clearRect(x,y,size,size);
            }else
            {
                g.setFill(colorPicker.getValue());
                g.fillOval(x, y, size, size);
            }
        });

    }

    public void onErase()
    {
        isErasing = !isErasing;
    }

    public void onSave()
    {
        try
        {
            Image snapshot = canvas.snapshot(null,null);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null), "png",new File("paint.png"));
        }catch (Exception e){}
    }

    public  void onExit()
    {
        Platform.exit();
    }
}
