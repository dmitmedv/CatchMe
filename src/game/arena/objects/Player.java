package game.arena.objects;

import game.arena.GameMap;
import game.util.Direction;

import java.awt.*;

public final class Player extends APersonage {

    public Player(int startX, int startY, Color c) {
        super(startX, startY, c);
    }

    public void paint(Graphics g) {
        // body
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
                        currX * GameMap.SIZE_FIELD + 5 + 1);
                break;
            case Direction.LEFT:
                g.drawLine(currY * GameMap.SIZE_FIELD + 10 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1,
                        currY * GameMap.SIZE_FIELD + 5 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1);
                break;
            case Direction.DOWN:
                g.drawLine(currY * GameMap.SIZE_FIELD + 10 + 1,
                           currX * GameMap.SIZE_FIELD + 10 + 1,
                           currY * GameMap.SIZE_FIELD + 10 + 1,
                           currX * GameMap.SIZE_FIELD + 15 + 1);
                break;
            case Direction.RIGHT:
                g.drawLine(currY * GameMap.SIZE_FIELD + 10 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1,
                        currY * GameMap.SIZE_FIELD + 15 + 1,
                        currX * GameMap.SIZE_FIELD + 10 + 1);
                break;
        }
    }
}
