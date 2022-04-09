package com.base;

import com.base.entities.Entity;
import com.base.entities.animals.ActionType;
import com.base.entities.animals.Human;
import com.base.ui.IVisualAdapter;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class World {


    private static Random rng;
    protected final RenderBatch batch = new RenderBatch();
    private final Journal journal = new Journal();
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Entity> added = new ArrayList<>();
    private final ArrayList<Entity> removed = new ArrayList<>();
    protected int cellSize = 20;
    protected int sizeX, sizeY;
    private IVisualAdapter window;

    public World(int sizeX, int sizeY) {
        rng = new Random();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public static Random getRandomNumberGenerator() {
        return rng;
    }

    public void setWindow(IVisualAdapter window) {
        this.window = window;
    }

    public void clear() {
        entities.clear();
        added.clear();
        redrawView();
    }

    public void draw() {
        applyAddition();
        redrawView();
    }

    public ArrayList<Position> getEntities(Class<? extends Entity> entityClass) {
        ArrayList<Position> output = new ArrayList<>();
        for (Entity e : entities) {
            if (e.getClass().equals(entityClass)) {
                output.add(e.getPosition());
            }
        }
        return output;
    }

    public void runOneFrame() {
        runOneFrame(' ');
    }

    public void runOneFrame(char action) {
        applyAddition();
        applyRemoval();
        entities.forEach(e -> {
            if (!isDeleted(e)) {
                if (e instanceof Human) {
                    if (action == 'x') {
                        ((Human) e).update(journal, new Position(0, 0), ActionType.Ability);
                    } else {
                        ((Human) e).update(journal, getDirectionFromAction(action), ActionType.Move);
                    }
                } else {
                    e.update(journal);

                }

            }
        });
        window.updateJournal(journal);
        journal.clear();
        redrawView();
    }

    public void reSize(int x, int y) {
        for (Entity e : entities) {
            if (e.getPosition().getX() >= x) {
                deleteEntity(e);
            }
            if (e.getPosition().getY() >= y) {
                deleteEntity(e);
            }
        }
        sizeX = x;
        sizeY = y;
        applyRemoval();
        redrawView();
    }

    public void addEntity(Entity entity) {
        if (isTileEmpty(entity.getPosition())) {
            added.add(entity);
        }
        entity.setWorld(this);
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean isTileEmpty(Position pos) {
        for (Entity e : entities) {
            if (e.getPosition().equals(pos)) {
                return false;
            }
        }
        for (Entity e : added) {
            if (e.getPosition().equals(pos)) {
                return false;
            }
        }
        return true;
    }


    public ArrayList<Position> getNeighbouringFreeTiles(Position pos) {
        ArrayList<Position> output = getNeighbouringTiles(pos);
        output.removeIf(tile -> !isTileEmpty(tile));
        return output;
    }

    public abstract ArrayList<Position> getNeighbouringTiles(Position pos);

    public Entity getEntityAtPosition(Position pos) {
        for (Entity e : entities) {
            if (e.getPosition().equals(pos)) {
                return e;
            }
        }
        return null;
    }

    public void save(String fileName) {
        Serializer s = serialize();
        try {
            s.save(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Serializer serialize() {
        Serializer s = new Serializer();
        s.setSubEntryName("World");
        s.setInt("sizeX", sizeX);
        s.setInt("sizeY", sizeY);
        for (Entity entity : entities) {
            s.setSubEntryName(entity.getClass().getName());
            entity.serialize(s);
        }
        return s;
    }

    public void deserialize(Serializer serializer) {
        Scanner sc = new Scanner(serializer.toString());
        load(sc);
    }

    public abstract IScreenToCellConverter getConverter();

    public void deleteEntity(Entity entity) {
        removed.add(entity);
    }

    public void notifyUpdate(Entity entity) {
        for (Entity e : entities) {
            if (isDeleted(e)) continue;
            if (e.getPosition().equals(entity.getPosition()) && e != entity) {
                e.onCollision(entity, journal);
            }
        }
    }

    public void load(String fileName) {
        Scanner file;
        try {
            file = new Scanner(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        load(file);
    }

    protected abstract Image parseRenderBatch();

    protected abstract Position getDirectionFromAction(char action);

    private void redrawView() {
        batch.clear();
        for (Entity e : entities) {
            if (isDeleted(e)) continue;
            e.draw(batch);
        }
        Image img = parseRenderBatch();
        window.updateMap(img);
    }

    private void load(Scanner file) {
        entities.clear();
        SerializationContext context = new SerializationContext();
        boolean isInitializing = false;
        Entity current = null;
        while (file.hasNextLine()) {
            String line = file.nextLine();
            if (line.equals("!!")) {
                if (isInitializing) {
                    reInit(context);
                } else {
                    if (current != null) {
                        current.deserialize(context);
                    } else {
                        System.out.println("Missing context!");
                    }
                }
                break;
            }
            if (line.startsWith("!")) {
                if (isInitializing) {
                    reInit(context);
                    isInitializing = false;
                } else if (current != null) {
                    current.deserialize(context);
                }
                context = new SerializationContext();
                String trimmed = line.replace('!', ' ').trim();
                if (trimmed.equals("World")) {
                    isInitializing = true;
                    current = null;
                } else {
                    try {
                        current = (Entity) Class.forName(trimmed).getConstructor(Position.class).newInstance(new Position(-1, -1));
                    } catch (Exception e) {
                        e.printStackTrace();
                        current = null;
                        continue;
                    }
                    addEntity(current);
                }
            } else {
                context.populate(line);
            }
        }
        applyAddition();
    }

    private void reInit(SerializationContext context) {
        sizeY = context.getInt("sizeY");
        sizeX = context.getInt("sizeX");
    }

    private void applyAddition() {
        ArrayList<Entity> tmp = new ArrayList<>(added);
        added.clear();
        for (Entity e : tmp) {
            if (isTileEmpty(e.getPosition())) {
                entities.add(e);
            }
        }
        entities.sort((l, r) -> {
            if (l.getInitiative() != r.getInitiative()) return Integer.compare(r.getInitiative(), l.getInitiative());
            return Integer.compare(r.getAge(), l.getAge());
        });
    }

    private void applyRemoval() {
        entities.removeAll(removed);
        removed.clear();
    }

    private boolean isDeleted(Entity entity) {
        return removed.contains(entity);
    }
}
