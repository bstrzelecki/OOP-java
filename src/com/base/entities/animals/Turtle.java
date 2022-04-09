package com.base.entities.animals;

import com.base.Journal;
import com.base.Position;
import com.base.World;
import com.base.entities.Entity;

import java.awt.*;
import java.util.Random;

public class Turtle extends Animal {

    public Turtle(Position position) {
        super(position);
        strength = 3;
        initiative = 2;
        symbol = 'T';
        color = Color.yellow;
    }

    @Override
    public void update(Journal journal) {
        Random rng = World.getRandomNumberGenerator();
        if (rng.nextInt(4) == 0) {
            super.update(journal);
        } else {
            incrementAge();
        }
    }

    @Override
    protected void onAttacked(Entity entity, Journal journal) {
        if (entity.getStrength() < 5) {
            if (entity instanceof Animal) {
                Animal animal = (Animal) entity;
                animal.undoMovement();
                journal.addEntry(this + " on tile " + position + " was attacked by " + entity + " and successfully deflected attack.");
            }
        } else {
            super.onAttacked(entity, journal);
        }
    }
}
