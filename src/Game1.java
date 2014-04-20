import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.*;

class Game1 {
    public static void main(String[] args) {
        JFrame f = new JFrame("Game1");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyPanel panel = new MyPanel();
        panel.addKeyListener(panel);
        panel.setFocusable(true);
        f.add(panel);
        f.setSize(400, 400);
        f.setVisible(true);
    }
}

class MyPanel extends JPanel implements KeyListener  {

    Player player = new Player(0, 0, Color.BLUE);
    Monster monster = new Monster(2, 2, Color.RED);
    GameMap map = new GameMap(player, monster);
    public boolean gameOver = false;

    MyPanel() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (monster.currX == player.currX && monster.currY == player.currY) {
                    System.out.println("GAME OVER!!!");
                    int de =1;
                }
                monster.moveXY(  monster.getNextStep()  );
                if (monster.currX == player.currX && monster.currY == player.currY) {
                    System.out.println("GAME OVER!!!");
                    int de =1;
                }

                repaint();
            }
        };
        Timer timer = new Timer(2000, actionListener);
        timer.start();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if(gameOver) return;
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if ( map.isFreeXY(player.getCurrX()-1, player.getCurrY()) ) player.move(Direction.LEFT);
                break;
            case KeyEvent.VK_UP:
                if ( map.isFreeXY(player.getCurrX(), player.getCurrY()-1) ) player.move(Direction.UP);
                break;
            case KeyEvent.VK_RIGHT:
                if ( map.isFreeXY(player.getCurrX()+1, player.getCurrY()) ) player.move(Direction.RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                if ( map.isFreeXY(player.getCurrX(), player.getCurrY()+1) ) player.move(Direction.DOWN);
                break;
        }
        repaint();
    }

    public void keyReleased(KeyEvent keyEvent) {}
    public void keyTyped(KeyEvent keyEvent) {}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.paint(g);
        if (gameOver) g.drawString("GAME OVER", 20, 20);
    }
}

abstract class APersonage {
    protected int currX;
    protected int currY;
    protected boolean visible = true;
    protected Color color;
    public GameMap gmap;

    Coord getCurrentPos() {
        return new Coord(currX, currY);
    }
    void moveXY(Coord coord) {
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

final class Player extends APersonage {

    Player(int startX, int startY, Color c) {
        super(startX, startY, c);
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

final class Monster extends APersonage {

    int tmp_map[][] = new int[GameMap.SIZE_MAP][GameMap.SIZE_MAP];
    int x = -1, y = -1;
    Coord path_map[][] = new Coord[GameMap.SIZE_MAP][GameMap.SIZE_MAP];
    Queue<Coord> q = new LinkedList<Coord>();
    Stack<Coord> path = new Stack<Coord>();
    boolean fFlag = false;

    Monster(int startX, int startY, Color c) {
        super(startX, startY, c);
    }

    void setupSearch() {
        // copy matrix and reset path_map
        for(int i =  0; i < GameMap.SIZE_MAP; i++)
            for(int j = 0; j < GameMap.SIZE_MAP; j++) {

                this.tmp_map[i][j] = this.gmap.map[i][j];
                this.path_map[i][j] = new Coord(-1, -1);
            }
        // reset
        this.fFlag = false;
        this.path.clear();
        this.q.clear();

        // init
        //System.out.println(this.gmap.player.getCurrX());
        //System.out.println(this.gmap.player.getCurrY());
        int debug = 1;
        this.path_map[this.currX][this.currY].setXY(this.currX, this.currY);
        this.tmp_map[this.gmap.player.getCurrX()]
                    [this.gmap.player.getCurrY()] = 6;  // set player
        this.tmp_map[this.currX]
                    [this.currY] = 9;                       // set monster

        q.add(  this.getCurrentPos()  );
    }

    private void BFS() {
        //
        if (q.isEmpty()) return;
        Coord curr = q.remove();
        System.out.println("->" + curr.x + ", " + curr.y);
        int deb = 1;
        if (this.tmp_map[curr.x][curr.y] == 6) {
            fFlag = true;
            System.out.println("FIND:" + curr.x + " " + curr.y);
            x = curr.x;
            y = curr.y;
            return;
        }
        this.tmp_map[curr.x][curr.y] = 5; // visited

        // push in queue children (from 4 possible)
        if (curr.x > 0 && (this.tmp_map[curr.x - 1][curr.y] == 0 || this.tmp_map[curr.x - 1][curr.y] == 6)) {
            q.add(new Coord(curr.x - 1, curr.y));
            this.path_map[curr.x - 1][curr.y].setXY(curr.x, curr.y);
        }
        if (curr.x < GameMap.SIZE_MAP-1 && (this.tmp_map[curr.x + 1][curr.y] == 0 || this.tmp_map[curr.x + 1][curr.y] == 6)) {
            q.add(new Coord(curr.x + 1, curr.y));
            this.path_map[curr.x + 1][curr.y].setXY(curr.x, curr.y);
        }
        if (curr.y > 0 && (this.tmp_map[curr.x][curr.y - 1] == 0 || this.tmp_map[curr.x][curr.y - 1] == 6)) {
            q.add(new Coord(curr.x, curr.y - 1));
            this.path_map[curr.x][curr.y - 1].setXY(curr.x, curr.y);
        }
        if (curr.y < GameMap.SIZE_MAP-1 && (this.tmp_map[curr.x][curr.y + 1] == 0 || this.tmp_map[curr.x][curr.y + 1] == 6)) {
            q.add(new Coord(curr.x, curr.y + 1));
            this.path_map[curr.x][curr.y + 1].setXY(curr.x, curr.y);
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

class GameMap {

    final static public int SIZE_FIELD = 10;
    final static public int SIZE_MAP = 30;

    Player player;
    Monster monster;

    int map[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    GameMap(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
        monster.gmap = this;
    }

    public boolean isFreeXY(int x, int y) {
        if (x < 0 || y < 0 || x == this.SIZE_MAP || y == this.SIZE_MAP) return false;
        return map[y][x] == 0;
    }

    public void paint(Graphics g) {
        // grid
        for (int x = 0; x <= 300; x+=10) {
            g.drawLine(0 + x, 0, 0 + x, 300);
            g.drawLine(0, 0 + x, 300, 0 + x);
        }

        // walls
        for (int y = 0; y < 30; y++) {
            for (int x = 0; x < 30; x++) {
                if (map[y][x] == 1) {
                    g.fillRect(x*10, y*10, 10, 10);
                }
            }
        }

        // objects
        player.paint(g);
        monster.paint(g);
    }
}

class Coord {
    public int x,y;
    Coord(int x, int y) {
        this.x = x;
        this.y = y; }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y; }

    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}

interface Direction {
    public static final int UP    = 1;
    public static final int RIGHT = 2;
    public static final int DOWN  = 3;
    public static final int LEFT  = 4;
}