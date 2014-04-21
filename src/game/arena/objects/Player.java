package game.arena.objects;

import game.arena.GameMap;

import java.awt.*;

/**
 * Created by dmedvedev on 21.04.2014.
 */
public final class Player extends APersonage {

    public Player(int startX, int startY, Color c) {
        super(startX, startY, c);
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(
                currX* GameMap.SIZE_FIELD+2,
                currY*GameMap.SIZE_FIELD+2,
                      GameMap.SIZE_FIELD-3,
                      GameMap.SIZE_FIELD-3
        );
        g.setColor(Color.BLACK);
    }
}
