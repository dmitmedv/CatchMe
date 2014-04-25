package game.gui;

import game.arena.GameMap;
import game.arena.objects.Monster;
import game.arena.objects.Player;
import game.util.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MyPanel extends JPanel implements KeyListener {

    Player player = new Player(0, 0, Color.ORANGE);
    Monster monster = new Monster(0, 5, Color.RED);
    GameMap map = GameMap.getInstance(player, monster);
    public boolean gameOver = false;
    Timer timer;

    MyPanel() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                monster.moveXY(  monster.getNextStep()  );
                if ( gameOver() ) {
                    gameOver = true;
                    timer.stop();
                }
                repaint();
            }
        };
        timer = new Timer(180, actionListener);
    }

    public void keyPressed(KeyEvent keyEvent) {
        if(gameOver) return;  // refact!
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if ( map.isFreeXY(player.getCurrY()-1, player.getCurrX())) player.move(Direction.LEFT);
                player.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_UP:
                if ( map.isFreeXY(player.getCurrY(), player.getCurrX()-1)) player.move(Direction.UP);
                player.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_RIGHT:
                if ( map.isFreeXY(player.getCurrY()+1, player.getCurrX())) player.move(Direction.RIGHT);
                player.setDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                if ( map.isFreeXY(player.getCurrY(), player.getCurrX()+1) ) player.move(Direction.DOWN);
                player.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_SPACE:
                player.shoot();
                break;
            case KeyEvent.VK_ESCAPE:
                timer.stop();
                break;
            case KeyEvent.VK_ENTER:
                timer.start();
                break;
        }
        if ( gameOver() ) {
            gameOver = true;
            timer.stop();
        }
        repaint();
    }

    public void keyReleased(KeyEvent keyEvent) {}
    public void keyTyped(KeyEvent keyEvent) {}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.paint(g);
        if ( gameOver ) g.drawString("GAME OVER", 20, 20);
    }

    private boolean gameOver() {
        return (monster.currX == player.currX && monster.currY == player.currY);
    }
}