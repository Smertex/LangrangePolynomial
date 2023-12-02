package com.cgvsu.protocurvefxapp;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class LagrangePolynomial {
    public ArrayList<Point2D> interpolatePoints(ArrayList<Point2D> points) {
        ArrayList<Point2D> pointsCopy = new ArrayList<>(points);
        Helper.bubbleSort(pointsCopy);

        int n = pointsCopy.size();
        ArrayList<Point2D> resultPoints = new ArrayList<>();

        int check = (int) pointsCopy.get(n - 1).getX();

        for (int xi = (int) pointsCopy.get(0).getX(); xi <= check; xi++) {
            double result = 0;
            for (int i = 0; i < n; i++) {

                double term = pointsCopy.get(i).getY();

                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        term = term * (xi - pointsCopy.get(j).getX()) / (pointsCopy.get(i).getX() - pointsCopy.get(j).getX());
                    }
                }
                result += term;
            }

            resultPoints.add(new Point2D(xi, result));
        }

        return resultPoints;
    }

}
