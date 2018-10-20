package sample;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private String color;
    List<PairInt> points;

    public Line(String color) {
        points = new ArrayList<>();
        this.color = color;
    }

    public Line(String color, List<PairInt> points) {
        this.color = color;
        this.points = points;
    }

    public void addPoint(int x, int y) {
        points.add(new PairInt(x, y));
    }

    public String getColor() {
        return color;
    }

    public List<PairInt> getPoints() {
        return points;
    }

    public void printPoints() {
        System.out.println("Color: " + color);
        for (int i = 0; i < points.size(); ++i) {
            System.out.println("X= " + points.get(i).a + " Y= " + points.get(i).b);
        }
    }
}