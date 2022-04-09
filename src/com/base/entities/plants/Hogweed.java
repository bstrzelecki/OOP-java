package com.base.entities.plants;

import com.base.Journal;
import com.base.Position;
import com.base.entities.Entity;
import com.base.entities.animals.Animal;
import com.base.entities.animals.CyberSheep;

import java.awt.*;
import java.util.ArrayList;

public class Hogweed extends Belladonna {
    public Hogweed(Position position) {
        super(position);
        symbol = 'H';
        strength = 10;
        color = Color.green.brighter();
    }

    @Override
    public void update(Journal journal) {
        super.update(journal);
        ArrayList<Position> positions = world.getNeighbouringTiles(getPosition());
        for (Position pos : positions) {
            clearTile(pos);
        }
    }

    private void clearTile(Position position) {
        Entity entity = world.getEntityAtPosition(position);
        if (entity != null) {
            if (entity instanceof CyberSheep) return;
            if (entity instanceof Animal) {
                world.deleteEntity(entity);
            }
        }
    }
}
