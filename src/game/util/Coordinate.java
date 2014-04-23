package game.util;

public class Coordinate {
    public int x,y;
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y; }

    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}