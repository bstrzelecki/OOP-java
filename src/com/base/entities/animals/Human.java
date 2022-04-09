package com.base.entities.animals;

import com.base.Journal;
import com.base.Position;
import com.base.SerializationContext;
import com.base.Serializer;
import com.base.entities.Entity;

import java.awt.*;
import java.util.ArrayList;

public class Human extends Animal {
    private int cooldown = 0;
    private int activeTimer = 0;

    public Human(Position position) {
        super(position);
        strength = 5;
        initiative = 4;
        symbol = 'X';
        color = Color.white;

    }

    public void update(Journal journal, Position direction, ActionType action) {
        incrementAge();
        if (action == ActionType.Move) {
            Position pos = new Position(position.getX() + direction.getX(), position.getY() + direction.getY());
            if(pos.getX() >= world.getSizeX())return;
            if(pos.getY() >= world.getSizeY())return;
            if(pos.getX() < 0)return;
            if(pos.getY() < 0)return;
            move(pos);
        }
        if (action == ActionType.Ability) {
            if (cooldown == 0) {
                startAbility(journal);
            } else {
                journal.addEntry(this + "'s ability is still on cooldown");
            }
        }
        updateAbility(journal);
    }

    @Override
    public void serialize(Serializer serializer) {
        super.serialize(serializer);
        serializer.setInt("cooldown", cooldown);
        serializer.setInt("activeTimer", activeTimer);
    }

    @Override
    public void deserialize(SerializationContext context) {
        super.deserialize(context);
        cooldown = context.getInt("cooldown");
        activeTimer = context.getInt("activeTimer");
    }

    private void updateAbility(Journal journal) {
        if (cooldown > 0 && activeTimer == 0) cooldown--;
        if (activeTimer > 0) {
            activeTimer--;
            ArrayList<Position> positions = world.getNeighbouringTiles(getPosition());
            for (Position pos : positions) {
                clearTile(pos);
            }
        }
        if (cooldown != 0 || activeTimer != 0) {
            journal.addEntry(this + "'s ability duration(" + activeTimer + "/5)" + " cooldown(" + cooldown + "/5)");
        }
    }

    private void clearTile(Position position) {
        Entity entity = world.getEntityAtPosition(position);
        world.deleteEntity(entity);
    }

    private void startAbility(Journal journal) {
        cooldown = 5;
        activeTimer = 5;
        journal.addEntry(this + " successfully activated ability!");
    }
}
