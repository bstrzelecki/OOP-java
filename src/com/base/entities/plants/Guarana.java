package com.base.entities.plants;

import com.base.Journal;
import com.base.Position;
import com.base.entities.Entity;
import com.base.entities.animals.Animal;

import java.awt.*;

public class Guarana extends Plant {
    public Guarana(Position position) {
        super(position);
        symbol = 'G';
        strength = 0;
        color = Color.pink;
    }

    @Override
    public void onCollision(Entity entity, Journal journal) {
        if (entity instanceof Animal) {
            ((Animal) entity).empower(3);
            journal.addEntry(entity + " on tile " + entity.getPosition() + " ate " + this + " and got empowered.");
        }
        super.onCollision(entity, journal);
    }
}
