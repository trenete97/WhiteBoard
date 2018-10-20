package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private String color;
    List<Pair<Number, Number>> points;
    public Line(String color) {
        points = new ArrayList<>();
        this.color = color;
    }

    public void addPoint(int x, int y) {
        points.add(new Pair<>(x, y));
    }

    public String getColor() {
        return color;
    }

    public List<Pair<Number, Number>> getPoints() {
        return points;
    }

    public void printPoints() {
        System.out.println("Color: " + color);
        for (int i = 0; i < points.size(); ++i) {
            System.out.println("X: " + points.get(i).getKey() + " Y: " + points.get(i).getValue());
        }
    }
}
