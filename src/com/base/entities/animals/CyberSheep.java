package com.base.entities.animals;

import com.base.Journal;
import com.base.Position;
import com.base.entities.plants.Hogweed;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CyberSheep extends Sheep {
    public CyberSheep(Position position) {
        super(position);
        strength = 11;
        initiative = 4;
        color = Color.cyan.brighter();
        symbol = 'C';
    }

    @Override
    public void update(Journal journal) {
        ArrayList<Position> hogweeds = world.getEntities(Hogweed.class);
        if (hogweeds.size() == 0) {
            super.update(journal);
        } else {
            Position min = hogweeds.get(0);
            int minValue = manhattanDistance(this.getPosition(), min);
            for (Position pos : hogweeds) {
                int next = manhattanDistance(this.getPosition(), pos);
                if (next < minValue) {
                    min = pos;
                    minValue = next;
                }
            }
            if (min.getX() != getPosition().getX()) {
                move(new Position(position.getX() + (position.getX() > min.getX() ? -1 : 1), getPosition().getY()));
                return;
            }
            if (min.getY() != getPosition().getY()) {
                move(new Position(position.getX(), getPosition().getY() + (position.getY() > min.getY() ? -1 : 1)));
            }
        }
    }

    private static int manhattanDistance(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
