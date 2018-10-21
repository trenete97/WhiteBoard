package com.example.mario.whiteboard;

public class Board {

    String name;

    public Board(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void drawLine (String line) {
        Line _line = new Line(line, false);

    }


}
