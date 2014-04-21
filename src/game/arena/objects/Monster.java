package game.arena.objects;

import game.arena.GameMap;
import game.util.Coord;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public final class Monster extends APersonage {

    int tmp_map[][] = new int[GameMap.SIZE_MAP][GameMap.SIZE_MAP];
    int x = -1, y = -1;
    Coord path_map[][] = new Coord[GameMap.SIZE_MAP][GameMap.SIZE_MAP];
    Queue<Coord> q = new LinkedList<Coord>();
    Stack<Coord> path = new Stack<Coord>();
    boolean fFlag = false;

    public Monster(int startX, int startY, Color c) {
        super(startX, startY, c);
    }

    void setupSearch() {
        // copy matrix and reset path_map
        for(int i =  0; i < GameMap.SIZE_MAP; i++) {
            for (int j = 0; j < GameMap.SIZE_MAP; j++) {

                this.tmp_map[i][j] = this.gmap.map[i][j];
                this.path_map[i][j] = new Coord(-1, -1);
            }
        }
        int de=1;
        // reset
        this.fFlag = false;
        this.path.clear();
        this.q.clear();

        // init
        int debug = 1;
        this.path_map[this.currX][this.currY].setXY(this.currX, this.currY);
        this.tmp_map[this.gmap.player.getCurrX()]
                    [this.gmap.player.getCurrY()] = 6;  // set player
        this.tmp_map[this.currX]
                    [this.currY] = 9;                       // set monster

        q.add(  this.getCurrentPos()  );
        this.tmp_map[this.currX][this.currY] = 5; // visited
    }

    private void BFS() {
        //
        if (q.isEmpty()) return;
        Coord curr = q.remove();
        System.out.println("->" + curr.x + ", " + curr.y + " : " + this.tmp_map[curr.x][curr.y]);
        if (this.tmp_map[curr.x][curr.y] == 6) {
            fFlag = true;
            System.out.println("FIND:" + curr.x + " " + curr.y);
            x = curr.x;
            y = curr.y;
            return;
        }
        //this.tmp_map[curr.x][curr.y] = 5; // visited

        // push in queue children (from 4 possible)
        if (curr.x > 0 && (this.tmp_map[curr.x - 1][curr.y] == 0 || this.tmp_map[curr.x - 1][curr.y] == 6)) { // UP
            q.add(new Coord(curr.x - 1, curr.y));
            this.path_map[curr.x - 1][curr.y].setXY(curr.x, curr.y);
            if (this.tmp_map[curr.x-1][curr.y] != 6) this.tmp_map[curr.x-1][curr.y] = 5; // visited
        }
        if (curr.x < GameMap.SIZE_MAP-1 && (this.tmp_map[curr.x + 1][curr.y] == 0 || this.tmp_map[curr.x + 1][curr.y] == 6)) { // DOWN
            q.add(new Coord(curr.x + 1, curr.y));
            this.path_map[curr.x + 1][curr.y].setXY(curr.x, curr.y);
            if (this.tmp_map[curr.x+1][curr.y] != 6) this.tmp_map[curr.x+1][curr.y] = 5; // visited
        }
        if (curr.y > 0 && (this.tmp_map[curr.x][curr.y - 1] == 0 || this.tmp_map[curr.x][curr.y - 1] == 6)) {  // LEFT
            q.add(new Coord(curr.x, curr.y - 1));
            this.path_map[curr.x][curr.y - 1].setXY(curr.x, curr.y);
            if (this.tmp_map[curr.x][curr.y-1] != 6) this.tmp_map[curr.x][curr.y-1] = 5; // visited
        }
        if (curr.y < GameMap.SIZE_MAP-1 && (this.tmp_map[curr.x][curr.y + 1] == 0 || this.tmp_map[curr.x][curr.y + 1] == 6)) { // RIGHT
            q.add(new Coord(curr.x, curr.y + 1));
            this.path_map[curr.x][curr.y + 1].setXY(curr.x, curr.y);
            if (this.tmp_map[curr.x][curr.y+1] != 6) this.tmp_map[curr.x][curr.y+1] = 5; // visited
        }
        this.BFS();
    }

    private Coord backTrace(Coord p) {
        if (this.path_map[p.x][p.y].x == p.x && this.path_map[p.x][p.y].y == p.y) {
            if (!path.empty()) {
                path.pop();
                if (path.empty()) {
                    return this.gmap.player.getCurrentPos();
                    //int err2 = 3;
                }
                return path.peek();
            } else {
                int err = 2;
            }
        }
        path.push(new Coord(this.path_map[p.x][p.y].x, this.path_map[p.x][p.y].y));
        System.out.println("stack-> " + path.peek());
        int d =1;
        return backTrace(path.peek());
    }

    public Coord getNextStep() {
        this.setupSearch();
        this.BFS();
        if (!fFlag) {
            int error = 1;
        }
        int deb = this.x + this.y;
        return this.backTrace(new Coord(this.x, this.y));
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(
                currX*GameMap.SIZE_FIELD+2,
                currY*GameMap.SIZE_FIELD+2,
                      GameMap.SIZE_FIELD-3,
                      GameMap.SIZE_FIELD-3
        );
        g.setColor(Color.BLACK);
    }

}
