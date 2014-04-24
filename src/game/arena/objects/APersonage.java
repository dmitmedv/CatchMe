package game.arena.objects;

import game.arena.GameMap;
import java.awt.*;
import game.util.*;

public abstract class APersonage {
    public int currX;
    public int currY;
    protected int prevCurrX;
    protected int prevCurrY;
    protected int direct = Direction.DOWN; // direction of motion
    protected boolean visible = true;
    protected Color color;
    public GameMap gmap;

    APersonage(int x, int y, Color c) {
        this.currX = x;
        this.currY = y;
        this.color = c;
        this.prevCurrX = x;
        this.prevCurrY = y;
    }

    Coordinate getCurrentPos() {
        return new Coordinate(currX, currY);
    }

    public void moveXY(Coordinate coord) {
        this.currX = coord.x;
        this.currY = coord.y;
        if (this.prevCurrX > this.currX) this.direct = Direction.UP;
        else if (this.prevCurrX < this.currX) this.direct = Direction.DOWN;
        else if (this.prevCurrY > this.currY) this.direct = Direction.LEFT;
        else if (this.prevCurrY < this.currY) this.direct = Direction.RIGHT;
        this.prevCurrX = this.currX;
        this.prevCurrY = this.currY;
    }

    public int getCurrX() {
        return currX;
    }

    public int getCurrY() {
        return currY;
    }

    final public void move(int direct) {
        switch (direct) {
            case Direction.UP:
                if (this.direct == Direction.UP) currX--;
                break;
            case Direction.LEFT:
                if (this.direct == Direction.LEFT) currY--;
                break;
            case Direction.DOWN:
                if (this.direct == Direction.DOWN) currX++;
                break;
            case Direction.RIGHT:
                if (this.direct == Direction.RIGHT) currY++;
                break;
        }
    }

    final public void setDirection(int newDirection) {
        switch (newDirection) {
            case Direction.UP:
                this.direct = Direction.UP;
                break;
            case Direction.RIGHT:
                this.direct = Direction.RIGHT;
                break;
            case Direction.DOWN:
                this.direct = Direction.DOWN;
                break;
            case Direction.LEFT:
                this.direct = Direction.LEFT;
                break;
        }
    }

    final public int getDirection() {
        return this.direct;
    }

    abstract public void paint(Graphics g);

}