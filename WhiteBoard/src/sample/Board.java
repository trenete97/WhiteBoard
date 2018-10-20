package sample;

import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    private String colorSelected = "Black";
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
                lines.add(new Line(colorSelected));
                lines.get(0).addPoint(oldX, oldY);

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent ev) {
                X = ev.getX();
                Y = ev.getY();
                lines.get(lines.size()-1).addPoint(oldX, oldY);
                JSONObject line = new JSONObject();
                line.put("Points", lines.get(lines.size()-1).getPoints());
                line.put("Color", lines.get(lines.size()-1).getColor());
                client.send(line.toJSONString());
                if (g2d != null) {
                    g2d.drawLine(oldX, oldY, X, Y);
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
        colorSelected = "Black";
        repaint();
        lines.clear();
    }

    public void red() {
        g2d.setPaint(Color.red);
        colorSelected = "Red";
    }

    public void black() {
        g2d.setPaint(Color.black);
        colorSelected = "Black";
    }

    public void magenta() {
        g2d.setPaint(Color.magenta);
        colorSelected = "Magenta";
    }

    public void green() {
        g2d.setPaint(Color.green);
        colorSelected = "Green";
    }

    public void blue() {
        g2d.setPaint(Color.blue);
        colorSelected = "Blue";
    }

    public void printLines() {
        for (int i = 0; i < lines.size(); ++i) {
            System.out.println("Linea: " + i);
            lines.get(i).printPoints();
        }
    }
    public void drawLine(String s) {
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String color = (String) json.get("Color");
        List<PairInt> points = (List<PairInt>) json.get("Points");
        Line l = new Line(color, points);

        //setColor
        int x = (int) l.points.get(0).a;
        int y = (int) l.points.get(0).b;
        lines.add(l);
        for (int i = 1; i < l.points.size(); ++i) {
            g2d.drawLine(x, y, (int) l.points.get(i).a, (int) l.points.get(i).b);
            x = (int) l.points.get(i).a;
            y = (int) l.points.get(i).b;
        }

    }

    private void setColor(String color) {
        switch (color) {
            case ("Black"):
                black();
                break;
            case ("Red"):
                red();
                break;
            case ("Blue"):
                blue();
                break;
            case ("Green"):
                green();
                break;
            case ("Magenta"):
                magenta();
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