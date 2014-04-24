package game.arena.attributes;

import game.arena.GameMap;
import game.util.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bullet {
    private int direction;
    private int currX;
    private int currY;
    private int speed;
    private Color color = Color.GRAY;
    Timer timer;
    private GameMap gmap;

    public Bullet(int speed, int direction, GameMap gmap) {
        this.speed = speed;
        this.direction = direction;
        this.gmap = gmap;
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                move();
                if ( false ) {
                    /*gameOver = true;*/
                    timer.stop();
                }
                //repaint();
                System.out.println("motion of bullet" + currX + " " + currY);
            }
        };
        timer = new Timer(speed, actionListener);
    }

    public void move() {
        switch (this.direction) {
            case Direction.UP:
                break;
            case Direction.LEFT:
                break;
            case Direction.DOWN:
                if (gmap.isFreeXY(this.currX, this.currX)) {
                    this.currX++;
                }
                break;
            case Direction.RIGHT:
                break;
        }
    }

    public void paint(Graphics g) {
        // body
        g.setColor(color);
        g.fillOval(
                currY * GameMap.SIZE_FIELD + 2,
                currX * GameMap.SIZE_FIELD + 2,
                GameMap.SIZE_FIELD - 3,
                GameMap.SIZE_FIELD - 3
        );
        g.setColor(Color.BLACK);
    }
}
