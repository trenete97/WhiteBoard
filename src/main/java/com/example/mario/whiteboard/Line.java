package com.example.mario.whiteboard;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private String color;
    List<PairInt> points;

    public Line(String s, Boolean single) {
        points = new ArrayList<>();
        if (single) color = s;
        else {
            points = new ArrayList<>();
            color = "";
            color += s.charAt(1);
            int i = 4;
            while (s.charAt(i) != ']') {
                String numb = "";
                int x, y;
                while (s.charAt(i) != ',') {
                    numb += s.charAt(i);
                    ++i;
                }
                x = Integer.parseInt(numb);
                numb = "";
                ++i;
                while (s.charAt(i) != ';') {
                    numb += s.charAt(i);
                    ++i;
                }
                y = Integer.parseInt(numb);
                points.add(new PairInt(x, y));
                ++i;
            }
        }

    }

    public String LineToString() {
        String ret = "";
        ret += color;
        ret += " [";
        for(int i = 0; i < points.size(); ++i) {
            ret += points.get(i).a;
            ret += ",";
            ret += points.get(i).b;
            ret += ";";
        }
        ret += "]";
        return ret;
    }

    public Line(String color, List<PairInt> points) {
        this.color = color;
        this.points = points;
    }

    public void addPoint(int x, int y) {
        PairInt p = new PairInt(x, y);
        points.add(p);
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