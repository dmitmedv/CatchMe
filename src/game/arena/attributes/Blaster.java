package game.arena.attributes;

import game.arena.GameMap;
import game.util.Direction;

import java.util.ArrayList;

public class Blaster implements Gun {

    private ArrayList<Bullet> bulletsOnMap = new ArrayList<Bullet>();

    @Override
    public void shoot(int startX, int startY, int direction) {
        System.out.println("shoot!");
        bulletsOnMap.add(new Bullet(startX, startY, 200, direction, GameMap.getInstance()));
    }
}
