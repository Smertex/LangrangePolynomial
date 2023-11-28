package com.cgvsu.protocurvefxapp;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
            final int POINT_RADIUS_POL = 1;

            graphicsContext.clearRect(0, 0, 800, 600);

            graphicsContext.fillOval(clickPoint.getX() - POINT_RADIUS, clickPoint.getY() - POINT_RADIUS, 2 * POINT_RADIUS, 2 * POINT_RADIUS);

            points.add(clickPoint);

            if (points.size() > 1) {
                final Point2D lastPoint = points.get(points.size() - 1);
                LagrangePolynomial lagrangePolynomial = new LagrangePolynomial();
                ArrayList<Point2D> pointsPolynomial = lagrangePolynomial.interpolatePoints(points, points.size());

                for (int i = 0; i < pointsPolynomial.size(); i++) {
                    graphicsContext.fillOval(pointsPolynomial.get(i).getX() - POINT_RADIUS_POL, pointsPolynomial.get(i).getY() - POINT_RADIUS_POL, POINT_RADIUS_POL * 2, POINT_RADIUS_POL * 2);
                }

                for (int i = 0; i < points.size() - 1; i++) {
                    graphicsContext.fillOval(points.get(i).getX() - POINT_RADIUS, points.get(i).getY() - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
                }

            }
        }

        if (event.getButton() == MouseButton.SECONDARY && points.size() > 1) {
            points.removeLast();
            graphicsContext.clearRect(0, 0, 800, 600);

            final int POINT_RADIUS = 3;
            final int POINT_RADIUS_POL = 1;

            LagrangePolynomial lagrangePolynomial = new LagrangePolynomial();
            ArrayList<Point2D> pointsPolynomial = lagrangePolynomial.interpolatePoints(points, points.size());

            for (int i = 0; i < pointsPolynomial.size(); i++) {
                graphicsContext.fillOval(pointsPolynomial.get(i).getX() - POINT_RADIUS_POL, pointsPolynomial.get(i).getY() - POINT_RADIUS_POL, POINT_RADIUS_POL * 2, POINT_RADIUS_POL * 2);
            }

            for (int i = 0; i < points.size(); i++) {
                graphicsContext.fillOval(points.get(i).getX() - POINT_RADIUS, points.get(i).getY() - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
            }
        }
    }
}