package sample;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class Main {

    JButton clearButton, blackButton, blueButton, greenButton, redButton, magentaButton,eraserButton;
    Board b;



    ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == clearButton) {
                b.client.send("C");
                b.clear();
            } else if (ev.getSource() == blackButton) {
                b.black();
            } else if (ev.getSource() == blueButton) {
                b.blue();
            } else if (ev.getSource() == greenButton) {
                b.green();
            } else if (ev.getSource() == redButton) {
                b.red();
            } else if (ev.getSource() == magentaButton) {
                b.magenta();
            } else if (ev.getSource() == eraserButton) {
                b.eraser();
            }
        }
    };

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {

        JFrame frame = new JFrame("Whiteboard");
        String name = JOptionPane.showInputDialog(frame, "What's your name?");
        while (name != null){
            if ("".equals(name))name = JOptionPane.showInputDialog(frame, "Name field is empty!");
            else if (!name.matches("[A-Za-z]*"))name = JOptionPane.showInputDialog(frame, "Write a valid name! (i.e. [A-Z,a-z])");
            else break;
        }
        if(name == null){
            System.exit(0);
        }
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        b = new Board(name);
        content.add(b, BorderLayout.CENTER);

        JPanel menu = new JPanel();

        clearButton = new JButton("Clear");
        clearButton.addActionListener(actionListener);
        blackButton = new JButton("Black");
        blackButton.addActionListener(actionListener);
        blueButton = new JButton("Blue");
        blueButton.addActionListener(actionListener);
        greenButton = new JButton("Green");
        greenButton.addActionListener(actionListener);
        redButton = new JButton("Red");
        redButton.addActionListener(actionListener);
        magentaButton = new JButton("Magenta");
        magentaButton.addActionListener(actionListener);


        ImageIcon img = new ImageIcon("eraser.png");
        Image img1 = img.getImage() ;
        Image newimg = img1.getScaledInstance( 20,20, java.awt.Image.SCALE_SMOOTH ) ;
        img = new ImageIcon( newimg );
        eraserButton = new JButton(img);
        eraserButton.addActionListener(actionListener);

        menu.add(eraserButton);
        menu.add(greenButton);
        menu.add(blueButton);
        menu.add(blackButton);
        menu.add(redButton);
        menu.add(magentaButton);
        menu.add(clearButton);
        content.add(menu, BorderLayout.NORTH);

        frame.setSize(600, 600);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(frame,
                        "Do you want to Exit ?", "Exit Confirmation : ",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    b.close();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                else
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
        frame.setVisible(true);

    }
}