package game.arena.objects;

import game.arena.GameMap;

import java.awt.*;

public final class Player extends APersonage {

    public Player(int startX, int startY, Color c) {
        super(startX, startY, c);
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(
                currY* GameMap.SIZE_FIELD+2,
                currX*GameMap.SIZE_FIELD+2,
                      GameMap.SIZE_FIELD-3,
                      GameMap.SIZE_FIELD-3
        );
        g.setColor(Color.BLACK);
    }
}
