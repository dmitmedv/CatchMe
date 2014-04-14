import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.Timer;

class SwingPaintDemo3 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
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

    public boolean gameOver = false;

    MyPanel() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                // monster1
                if (player.getX() > monster.getX()) monster.move(2);
                if (player.getX() < monster.getX()) monster.move(4);
                if (player.getY() > monster.getY()) monster.move(3);
                if (player.getY() < monster.getY()) monster.move(1);
                if (player.getX() == monster.getX() && player.getY() == monster.getY()) gameOver = true;

                // monster2
                if (player.getX() > monster2.getX()) monster2.move(2);
                if (player.getX() < monster2.getX()) monster2.move(4);
                if (player.getY() > monster2.getY()) monster2.move(3);
                if (player.getY() < monster2.getY()) monster2.move(1);
                if (player.getX() == monster2.getX() && player.getY() == monster2.getY()) gameOver = true;
                repaint();
            }
        };
        Timer timer = new Timer(200, actionListener);
        timer.start();

    }

    Player player = new Player(Color.BLUE, 0, 0);
    Player monster = new Player(Color.RED, 280,280);
    Player monster2 = new Player(Color.RED, 280, 0);
    GameMap map = new GameMap();

    public void keyPressed(KeyEvent keyEvent) {
        //System.out.println(keyEvent.getKeyCode());
        if(gameOver) return;
        switch (keyEvent.getKeyCode()) {
            case 37:
                player.move(4);
                break;
            case 38:
                player.move(1);
                break;
            case 39:
                player.move(2);
                break;
            case 40:
                player.move(3);
                break;
        }
        repaint();
    }

    public void keyReleased(KeyEvent keyEvent) {}
    public void keyTyped(KeyEvent keyEvent) {}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.paintPlayer(g);
        monster.paintPlayer(g);
        monster2.paintPlayer(g);
        map.paintMap(g);
        if (gameOver) {
            g.drawString("GAME OVER", 20, 20);
        }
    }
}

class Player {

    private int xPos = 2;
    private int yPos = 2;
    private int width = 7;
    private int height = 7;
    private Color color;

    Player(Color color, int offX, int offY) {
        this.color = color;
        xPos+=offX;
        yPos+=offY;
    }

    public void setX(int xPos){
        this.xPos = xPos;
    }

    public int getX(){
        return xPos;
    }

    public void setY(int yPos){
        this.yPos = yPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void move(int direction) {
        switch (direction) {
            case 1:
                if (yPos > 10) yPos-=10;
                break;
            case 2:
                if (xPos < 300) xPos += 10;
                break;
            case 3:
                if (yPos < 300) yPos+=10;
                break;
            case 4:
                if (xPos > 10) xPos-=10;
                break;
        }
    }

    public void paintPlayer(Graphics g) {
        g.setColor(color);
        g.fillRect(xPos, yPos, width, height);
        g.setColor(Color.BLACK);
    }
}

class GameMap {
    public void paintMap(Graphics g) {
        for(int x = 0; x <= 300; x+=10) {
            g.drawLine(0 + x, 0, 0 + x, 300);
            g.drawLine(0, 0 + x, 300, 0 + x);
        }
    }
}

/*
class Monster implements Runnable {
    Thread t;
    Graphics g;
    Monster() {
        t = new Thread(this, "Demo");
        System.out.println("Create thread");
        t.start();
    }

    public void run() {
        try {
            for(int i = 5; i > 0; i--) {
                System.out.println("t: " + i);
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            System.out.println("t-break");
        }
        System.out.println("t-end");
    }
}*/
