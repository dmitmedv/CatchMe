package game.arena.objects;

import game.arena.GameMap;
import java.awt.*;
import game.util.*;

abstract class APersonage {
    protected int currX;
    protected int currY;
    protected boolean visible = true;
    protected Color color;
    public GameMap gmap;

    Coord getCurrentPos() {
        return new Coord(currX, currY);
    }
    public void moveXY(Coord coord) {
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
            case Direction.UP:   currY--; break;
            case Direction.LEFT: currX--; break;
            case Direction.DOWN: currY++; break;
            case Direction.RIGHT:currX++; break;
        }
    }
    abstract public void paint(Graphics g);
}