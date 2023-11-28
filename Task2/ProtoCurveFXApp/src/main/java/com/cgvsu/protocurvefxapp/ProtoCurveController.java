package com.cgvsu.protocurvefxapp;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ProtoCurveController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    ArrayList<Point2D> points = new ArrayList<Point2D>();

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        canvas.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY -> handlePrimaryClick(canvas.getGraphicsContext2D(), event);
                case SECONDARY -> handlePrimaryClick(canvas.getGraphicsContext2D(), event);
            }
        });


    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            final Point2D clickPoint = new Point2D(event.getX(), event.getY());

            final int POINT_RADIUS = 3;

            graphicsContext.clearRect(0, 0, 800, 600);

            graphicsContext.fillOval(clickPoint.getX() - POINT_RADIUS, clickPoint.getY() - POINT_RADIUS, 2 * POINT_RADIUS, 2 * POINT_RADIUS);

            points.add(clickPoint);
            Helper.bubbleSort(points);

            if (points.size() > 1) {
                final Point2D lastPoint = points.get(points.size() - 1);
                LagrangePolynomial lagrangePolynomial = new LagrangePolynomial();
                ArrayList<Point2D> pointsPolynomial = lagrangePolynomial.interpolatePoints(points);

                for (int i = 0; i < points.size(); i++) {
                    graphicsContext.fillOval(points.get(i).getX() - POINT_RADIUS, points.get(i).getY() - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
                }

                for (int i = 0; i < pointsPolynomial.size() - 1; i++) {
                    graphicsContext.strokeLine(pointsPolynomial.get(i).getX(), pointsPolynomial.get(i).getY(), pointsPolynomial.get(i + 1).getX(), pointsPolynomial.get(i + 1).getY());
                }

            }
        }

        if (event.getButton() == MouseButton.SECONDARY && points.size() > 1) {
            points.removeLast();
            Helper.bubbleSort(points);
            graphicsContext.clearRect(0, 0, 800, 600);

            final int POINT_RADIUS = 3;

            LagrangePolynomial lagrangePolynomial = new LagrangePolynomial();
            ArrayList<Point2D> pointsPolynomial = lagrangePolynomial.interpolatePoints(points);

            for (int i = 0; i < points.size(); i++) {
                graphicsContext.fillOval(points.get(i).getX() - POINT_RADIUS, points.get(i).getY() - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
            }

            for (int i = 0; i < pointsPolynomial.size() - 1; i++) {
                graphicsContext.strokeLine(pointsPolynomial.get(i).getX(), pointsPolynomial.get(i).getY(), pointsPolynomial.get(i + 1).getX(), pointsPolynomial.get(i + 1).getY());
            }
        }
    }
}