package com.base.entities;

import com.base.*;

import java.awt.*;

public abstract class Entity {

    protected Position position;
    protected World world;

    protected Color color;
    protected char symbol = '?';
    protected int strength, initiative;
    private int age = 0;

    public Entity(Position position) {
        this.position = position;
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public void draw(RenderBatch batch) {
        batch.draw(symbol, color, position.getX(), position.getY());
    }

    public void update(Journal journal) {
        incrementAge();
    }

    public void serialize(Serializer serializer) {
        serializer.setInt("strength", getStrength());
        serializer.setInt("x", getPosition().getX());
        serializer.setInt("y", getPosition().getY());
        serializer.setInt("age", getAge());
    }

    public void deserialize(SerializationContext context) {
        strength = context.getInt("strength");
        position = new Position(context.getInt("x"), context.getInt("y"));
        age = context.getInt("age");
    }

    public int getAge() {
        return age;
    }

    public abstract void onCollision(Entity entity, Journal journal);

    public void setWorld(World world) {
        this.world = world;
    }

    public Position getPosition() {
        return position;
    }

    protected void incrementAge() {
        age++;
    }
}
