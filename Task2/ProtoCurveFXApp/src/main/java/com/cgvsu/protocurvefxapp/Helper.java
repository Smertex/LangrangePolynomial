package com.cgvsu.protocurvefxapp;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Helper {
        public static ArrayList<Point2D> bubbleSort(ArrayList<Point2D> points){
            for(int i = 0; i < points.size() - 1; i++){
                for(int j = 0; j < points.size() - 1; j ++){
                    if(points.get(j).getX() > points.get(j + 1).getX()) {
                        Point2D subObj = points.get(j);
                        points.set(j, points.get(j + 1));
                        points.set(j + 1, subObj);
                    }
                }
            }
            return points;
        }
}
