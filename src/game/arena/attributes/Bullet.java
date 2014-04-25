package game.arena.attributes;

import game.arena.GameMap;
import game.util.Direction;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;

public class Bullet {
    private int direction;
    private int currX;
    private int currY;
    private int speed;
    private Color color = Color.GRAY;
    Timer timer = new Timer();
    private GameMap gmap;
    private Move move = new Move();

    public Bullet(int startX, int startY, int speed, int direction, GameMap gmap) {
        this.currX = startX;
        this.currY = startY;
        this.speed = speed;
        this.direction = direction;
        this.gmap = gmap;
        timer.schedule(move, 0, speed);
    }

    public void move() {
        switch (this.direction) {
            case Direction.UP:
                break;
            case Direction.LEFT:
                break;
            case Direction.DOWN:
                if (gmap.isFreeXY(this.currX, this.currY)) {
                    this.currX++;
                }
                break;
            case Direction.RIGHT:
                if (gmap.isFreeXY(this.currX, this.currY)) {
                    this.currY++;
                }
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

    class Move extends TimerTask {
        public void run() {
            move();
            System.out.println("motion of bullet: " + currX + " " + currY);
            if (! gmap.isFreeXY(currX, currY) ) {
                timer.cancel();
                System.out.println("END bullet");
            }
        }
    }
}
