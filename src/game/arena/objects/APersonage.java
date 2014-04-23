package game.arena.objects;

import game.arena.GameMap;
import java.awt.*;
import game.util.*;

public abstract class APersonage {
    public int currX;
    public int currY;
    public int direct = Direction.DOWN; // direction of motion
    protected boolean visible = true;
    protected Color color;
    public GameMap gmap;

    Coordinate getCurrentPos() {
        return new Coordinate(currX, currY);
    }
    public void moveXY(Coordinate coord) {
        this.currX = coord.x;
        this.currY = coord.y;
    }

    public int getCurrX() {
        return currX;
    }

    public int getCurrY() {
        return currY;
    }

    APersonage(int x, int y, Color c) {
        this.currX = x;
        this.currY = y;
        this.color = c;
    }

    final public void move(int direct) {
        switch (direct) {
            case Direction.UP:   currX--; break;
            case Direction.LEFT: currY--; break;
            case Direction.DOWN: currX++; break;
            case Direction.RIGHT:currY++; break;
        }
    }
    abstract public void paint(Graphics g);
}