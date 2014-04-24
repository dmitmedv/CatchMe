package game.gui;

import javax.swing.*;

public class Game1 {
    public static void main(String[] args) {
        JFrame f = new JFrame("Game1");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyPanel panel = new MyPanel();
        panel.addKeyListener(panel);
        panel.setFocusable(true);
        f.add(panel);
        f.setSize(700, 700);
        f.setVisible(true);
    }
}