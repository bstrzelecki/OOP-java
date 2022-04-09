package com.base.entities.animals;

import com.base.Position;
import com.base.entities.Entity;

import java.awt.*;

public class Fox extends Animal {
    public Fox(Position position) {
        super(position);
        strength = 3;
        initiative = 7;
        symbol = 'F';
        color = Color.orange;
    }

    @Override
    protected void move(Position pos) {
        if (canMove(position)) {
            super.move(pos);
        }
    }

    private boolean canMove(Position position) {
        Entity e = world.getEntityAtPosition(position);
        if (e == null) return true;
        return e.getStrength() <= getStrength();
    }
}
