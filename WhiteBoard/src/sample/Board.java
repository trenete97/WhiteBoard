package sample;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class Board extends JComponent {

    private Image image;
    private Graphics2D g2d;
    private int X, Y, oldX, oldY;
    private String colorSelected = "N";
    public List<Line> lines;
    ChatClient client;
    String name;

    public Board(String name) {
        client = new ChatClient(this);
        client.startConnection(name);
        this.name = name;
        lines = new ArrayList<>();
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
                lines.add(new Line(colorSelected, true));
                lines.get(0).addPoint(oldX, oldY);

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent ev) {
                X = ev.getX();
                Y = ev.getY();
                lines.get(lines.size()-1).addPoint(oldX, oldY);
                client.send(lines.get(lines.size()-1).LineToString());
                if (g2d != null) {
                    g2d.drawLine(oldX, oldY, X, Y);
                    lines.get(lines.size()-1).addPoint(X, Y);
                    lines.add(new Line(colorSelected, true));
                    repaint();
                    oldX = X;
                    oldY = Y;
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2d = (Graphics2D) image.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }

        g.drawImage(image, 0, 0, null);
    }

    public void clear() {
        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, getSize().width, getSize().height);
        g2d.setPaint(Color.black);
        colorSelected = "N";
        repaint();
        lines.clear();
    }

    public void red() {
        g2d.setPaint(Color.red);
        colorSelected = "R";
    }

    public void black() {
        g2d.setPaint(Color.black);
        colorSelected = "N";
    }

    public void magenta() {
        g2d.setPaint(Color.magenta);
        colorSelected = "M";
    }

    public void green() {
        g2d.setPaint(Color.green);
        colorSelected = "G";
    }

    public void blue() {
        g2d.setPaint(Color.blue);
        colorSelected = "B";
    }

    public void drawLine(String s) {
        Line l = new Line(s, false);

        setColor(l.getColor());
        int x = (int) l.points.get(0).a;
        int y = (int) l.points.get(0).b;
        lines.add(l);
        for (int i = 1; i < l.points.size(); ++i) {
            g2d.drawLine(x, y, (int) l.points.get(i).a, (int) l.points.get(i).b);
            repaint();
            x = (int) l.points.get(i).a;
            y = (int) l.points.get(i).b;
        }
        setColor(colorSelected);

    }

    private void setColor(String color) {
        switch (color) {
            case ("N"):
                g2d.setPaint(Color.black);
                break;
            case ("R"):
                g2d.setPaint(Color.red);
                break;
            case ("B"):
                g2d.setPaint(Color.blue);
                break;
            case ("G"):
                g2d.setPaint(Color.green);
                break;
            case ("M"):
                g2d.setPaint(Color.magenta);
                break;

        }
    }

    public void close() {
        client.close();
    }

    public String getName() {
        return name;
    }


}