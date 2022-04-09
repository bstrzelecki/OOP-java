package com.base.entities.plants;

import com.base.Journal;
import com.base.Position;
import com.base.World;
import com.base.entities.Entity;

import java.util.ArrayList;
import java.util.Random;

public abstract class Plant extends Entity {

    public Plant(Position position) {
        super(position);
        initiative = 0;
    }

    @Override
    public void update(Journal journal) {
        super.update(journal);
        spread(journal);
    }

    protected void spread(Journal journal) {
        Random rng = World.getRandomNumberGenerator();
        if (rng.nextInt(4) != 0) return;
        ArrayList<Position> positions = world.getNeighbouringFreeTiles(getPosition());
        if (positions.isEmpty()) return;
        seed(positions.get(rng.nextInt(positions.size())), journal);
    }

    private void seed(Position position, Journal journal) {
        try {
            Entity e = this.getClass().getConstructor(Position.class).newInstance(position);
            world.addEntity(e);
            journal.addEntry(this + " on tile " + position + " successfully spread to " + e.getPosition() + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCollision(Entity entity, Journal journal) {
        world.deleteEntity(this);
        journal.addEntry(this + " on tile " + position + " was attacked by " + entity);
    }
}
