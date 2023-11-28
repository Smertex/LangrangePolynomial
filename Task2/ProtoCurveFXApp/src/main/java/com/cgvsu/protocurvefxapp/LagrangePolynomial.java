package com.cgvsu.protocurvefxapp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.function.Function;

public class LagrangePolynomial {
    public ArrayList<Point2D> interpolatePoints(ArrayList<Point2D> points) {
        int n = points.size();
        ArrayList<Point2D> resultPoints = new ArrayList<>();

        for (int xi = (int) points.get(0).getX(); xi <= (int) points.get(n - 1).getX(); xi++) {
            double result = 0;

            for (int i = 0; i < n; i++) {
                double term = points.get(i).getY();

                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        term = term * (xi - points.get(j).getX()) / (points.get(i).getX() - points.get(j).getX());
                    }
                }
                result += term;
            }

            resultPoints.add(new Point2D(xi, result));
        }

        return resultPoints;
    }

}
