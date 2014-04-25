package game.arena.objects;

import game.arena.GameMap;
import game.util.Coordinate;
import game.util.Direction;

import java.awt.*;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public final class Monster extends APersonage {

    int tmp_map[][] = new int[GameMap.SIZE_MAP][GameMap.SIZE_MAP];
    int xPlayer = -1, yPlayer = -1;
    Coordinate path_map[][] = new Coordinate[GameMap.SIZE_MAP][GameMap.SIZE_MAP];
    Queue<Coordinate> q = new LinkedList<Coordinate>();
    Stack<Coordinate> pathToPlayer = new Stack<Coordinate>();
    boolean fFlag = false;

    public Monster(int startX, int startY, Color c) {
        super(startX, startY, c);
    }

    void setupSearch() {
        // copy matrix and reset path_map
        for(int i = 0; i < GameMap.SIZE_MAP; i++) {
            for (int j = 0; j < GameMap.SIZE_MAP; j++) {
                this.tmp_map[i][j] = this.gmap.map[i][j];
                this.path_map[i][j] = new Coordinate(-1, -1);
            }
        }

        // reset
        this.fFlag = false;
        this.pathToPlayer.clear();
        this.q.clear();
        this.gmap.pathToPlayer.clear();

        // init
        this.path_map[this.currX][this.currY].setXY(this.currX, this.currY); // label: start point
        this.tmp_map[this.gmap.player.getCurrX()]
                    [this.gmap.player.getCurrY()] = 6;  // set player

        q.add(  this.getCurrentPos()  );
        this.tmp_map[this.currX][this.currY] = 5; // visited
    }

    private void BFS() {
        if (q.isEmpty()) return;
        Coordinate curr = q.remove();
        //System.out.println("->" + curr.x + ", " + curr.y + " : " + this.tmp_map[curr.x][curr.y]); // BFS log
        if (this.tmp_map[curr.x][curr.y] == 6) {
            fFlag = true;
            //System.out.println("FIND:" + curr.x + " " + curr.y);  // location log
            xPlayer = curr.x;
            yPlayer = curr.y;
            return;
        }

        // push in queue children (from 4 possible)
        if (curr.x > 0 && (this.tmp_map[curr.x - 1][curr.y] == 0 || this.tmp_map[curr.x - 1][curr.y] == 6)) { // UP
            q.add(new Coordinate(curr.x - 1, curr.y));
            this.path_map[curr.x - 1][curr.y].setXY(curr.x, curr.y);
            if (this.tmp_map[curr.x-1][curr.y] != 6) this.tmp_map[curr.x-1][curr.y] = 5; // visited
        }
        if (curr.x < GameMap.SIZE_MAP-1 && (this.tmp_map[curr.x + 1][curr.y] == 0 || this.tmp_map[curr.x + 1][curr.y] == 6)) { // DOWN
            q.add(new Coordinate(curr.x + 1, curr.y));
            this.path_map[curr.x + 1][curr.y].setXY(curr.x, curr.y);
            if (this.tmp_map[curr.x+1][curr.y] != 6) this.tmp_map[curr.x+1][curr.y] = 5; // visited
        }
        if (curr.y > 0 && (this.tmp_map[curr.x][curr.y - 1] == 0 || this.tmp_map[curr.x][curr.y - 1] == 6)) {  // LEFT
            q.add(new Coordinate(curr.x, curr.y - 1));
            this.path_map[curr.x][curr.y - 1].setXY(curr.x, curr.y);
            if (this.tmp_map[curr.x][curr.y - 1] != 6) this.tmp_map[curr.x][curr.y - 1] = 5; // visited
        }
        if (curr.y < GameMap.SIZE_MAP-1 && (this.tmp_map[curr.x][curr.y + 1] == 0 || this.tmp_map[curr.x][curr.y + 1] == 6)) { // RIGHT
            q.add(new Coordinate(curr.x, curr.y + 1));
            this.path_map[curr.x][curr.y + 1].setXY(curr.x, curr.y);
            if (this.tmp_map[curr.x][curr.y+1] != 6) this.tmp_map[curr.x][curr.y+1] = 5; // visited
        }
        this.BFS();
    }

    private Coordinate backTrace(Coordinate p) {  // stupid logic!!!
        if (this.path_map[p.x][p.y].x == p.x && this.path_map[p.x][p.y].y == p.y) {
            if (!pathToPlayer.empty()) {

                Coordinate tmp = pathToPlayer.pop();
                System.out.println(tmp);
                if (pathToPlayer.empty()) return this.gmap.player.getCurrentPos();    // VERY stupid logic!!!
                Coordinate result = pathToPlayer.peek();
                return result;
            }
        }
        pathToPlayer.push(new Coordinate(this.path_map[p.x][p.y].x, this.path_map[p.x][p.y].y));
        this.gmap.pathToPlayer.add(this.pathToPlayer.peek());
        //System.out.println("stack-> " + pathToPlayer.peek());  // path to player Log
        return backTrace(pathToPlayer.peek());
    }

    public Coordinate getNextStep() {
        this.setupSearch();
        this.BFS();
        return this.backTrace(new Coordinate(this.xPlayer, this.yPlayer));
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(
                currY * GameMap.SIZE_FIELD + 2,
                currX * GameMap.SIZE_FIELD + 2,
                GameMap.SIZE_FIELD - 3,
                GameMap.SIZE_FIELD - 3
        );
        g.setColor(Color.BLACK);

        // pushka
        switch (this.direct) {
            case Direction.UP:
                g.drawLine(currY * GameMap.SIZE_FIELD + 10 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1,
                        currY * GameMap.SIZE_FIELD + 10 + 1,
                        currX * GameMap.SIZE_FIELD + 0 + 1);
                break;
            case Direction.LEFT:
                g.drawLine(currY * GameMap.SIZE_FIELD + 10 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1,
                        currY * GameMap.SIZE_FIELD + 0 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1);
                break;
            case Direction.DOWN:
                g.drawLine(currY * GameMap.SIZE_FIELD + 10 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1,
                        currY * GameMap.SIZE_FIELD + 10 + 1,
                        currX * GameMap.SIZE_FIELD + 20 + 1);
                break;
            case Direction.RIGHT:
                g.drawLine(currY * GameMap.SIZE_FIELD + 10 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1,
                        currY * GameMap.SIZE_FIELD + 20 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1);
                break;
        }
    }
}