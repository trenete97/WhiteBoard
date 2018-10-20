package sample;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

public class Board extends JComponent {

    private Image image;
    private Graphics2D g2d;
    private int X, Y, oldX, oldY;

    public Board() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent ev) {
                X = ev.getX();
                Y = ev.getY();

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
        repaint();
    }

    public void red() {
        g2d.setPaint(Color.red);
    }

    public void black() {
        g2d.setPaint(Color.black);
    }

    public void magenta() {
        g2d.setPaint(Color.magenta);
    }

    public void green() {
        g2d.setPaint(Color.green);
    }

    public void blue() {
        g2d.setPaint(Color.blue);
    }

}