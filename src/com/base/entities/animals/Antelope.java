package com.base.entities.animals;

import com.base.Journal;
import com.base.Position;
import com.base.World;
import com.base.entities.Entity;

import java.awt.*;
import java.util.Random;

public class Antelope extends Animal {
    public Antelope(Position position) {
        super(position);
        strength = 4;
        initiative = 4;
        symbol = 'A';
        color = Color.blue;
    }

    @Override
    public void update(Journal journal) {
        Position start = position;
        for (int i = 0; i < 2; ++i) {
            super.update(journal);
        }
        if (!start.equals(position)) {
            journal.addEntry(this + " moved from " + start + " to " + position + ".");
        }
    }

    @Override
    protected void onAttacked(Entity entity, Journal journal) {
        Random rng = World.getRandomNumberGenerator();
        if (rng.nextBoolean()) {
            super.onAttacked(entity, journal);
        } else {
            moveAction();
            journal.addEntry(this + " on tile " + lastPosition + " was attacked by " + entity + " and ran away to " + position + ".");
        }
    }
}


