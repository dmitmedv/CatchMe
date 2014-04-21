package game.util;

public class Coord {
    public int x,y;
    public Coord(int x, int y) {
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