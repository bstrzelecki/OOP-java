package com.base.entities.animals;

import com.base.Journal;
import com.base.Position;
import com.base.World;
import com.base.entities.Entity;

import java.util.ArrayList;
import java.util.Random;

public abstract class Animal extends Entity {

    protected Position lastPosition;

    public Animal(Position position) {
        super(position);
    }

    @Override
    public void onCollision(Entity entity, Journal journal) {
        if (entity instanceof Animal) {
            if (entity.getClass().getName().equals(this.getClass().getName())) {
                reproduce((Animal) entity, journal);
            } else {
                onAttacked(entity, journal);
            }
        }
    }

    public void undoMovement() {
        position = lastPosition;
    }

    public void empower(int strength) {
        this.strength += strength;
    }

    @Override
    public void update(Journal journal) {
        super.update(journal);
        moveAction();
        if (lastPosition != position)
            journal.addEntry(this + " moved from " + lastPosition + " to " + position);
    }

    protected void onAttacked(Entity entity, Journal journal) {
        if (entity.getStrength() >= getStrength()) {
            journal.addEntry(this + " on tile " + position.toString() + " was attacked by " + entity + " and lost.");
            world.deleteEntity(this);
            return;
        }
        if (entity.getStrength() < getStrength()) {
            journal.addEntry(this + " on tile " + position.toString() + " was attacked by " + entity + " and won.");
            world.deleteEntity(entity);
        }
    }

    protected void moveAction() {
        ArrayList<Position> positions = world.getNeighbouringTiles(getPosition());
        Random rng = World.getRandomNumberGenerator();
        move(positions.get(rng.nextInt(positions.size())));

    }

    protected void move(Position pos) {
        lastPosition = position;
        position = pos;
        world.notifyUpdate(this);
    }

    private void reproduce(Animal animal, Journal journal) {
        animal.undoMovement();
        try {
            ArrayList<Position> positions = world.getNeighbouringFreeTiles(this.getPosition());
            if (positions.size() == 0) {
                return;
            }
            Random rng = World.getRandomNumberGenerator();
            Position pos = positions.get(rng.nextInt(positions.size()));
            Entity e = this.getClass().getConstructor(Position.class).newInstance(pos);
            world.addEntity(e);
        } catch (Exception e) {
            e.printStackTrace();
            journal.addEntry("!!!Error during reproduction");
        }
        journal.addEntry(this + " on tile " + this.getPosition() + " successfully mated.");
    }

}
