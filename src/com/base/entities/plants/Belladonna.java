package com.base.entities.plants;

import com.base.Journal;
import com.base.Position;
import com.base.entities.Entity;

import java.awt.*;

public class Belladonna extends Plant {
    public Belladonna(Position position) {
        super(position);
        symbol = 'B';
        color = Color.green.darker();
        strength = 99;
    }

    @Override
    public void onCollision(Entity entity, Journal journal) {
        if (entity.getStrength() > getStrength()) {
            super.onCollision(entity, journal);
            return;
        }
        journal.addEntry(entity + " on tile " + entity.getPosition() + " ate " + this + " and died.");
        world.deleteEntity(entity);
    }
}
