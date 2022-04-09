package com.base;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GridWorld extends World {
    public GridWorld(int sizeX, int sizeY) {
        super(sizeX, sizeY);
    }

    @Override
    public ArrayList<Position> getNeighbouringTiles(Position pos) {
        ArrayList<Position> output = new ArrayList<>();
        if (pos.getX() > 0) output.add(new Position(pos.getX() - 1, pos.getY()));
        if (pos.getY() > 0) output.add(new Position(pos.getX(), pos.getY() - 1));
        if (pos.getX() < getSizeX() - 1) output.add(new Position(pos.getX() + 1, pos.getY()));
        if (pos.getY() < getSizeY() - 1) output.add(new Position(pos.getX(), pos.getY() + 1));
        return output;
    }

    @Override
    public IScreenToCellConverter getConverter() {
        return (x, y) -> new Position(x / 20, y / 20);
    }

    @Override
    protected Image parseRenderBatch() {
        BufferedImage img = new BufferedImage(sizeX * cellSize, sizeY * cellSize, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = img.getGraphics();

        for (SpriteDescriptor sd : batch.getSprites()) {
            graphics.setColor(sd.getColor());
            graphics.drawString(String.valueOf(sd.getSymbol()), sd.getPosition().getX() * cellSize + cellSize / 4, sd.getPosition().getY() * cellSize + 3 * cellSize / 4);
            graphics.drawRect(sd.getPosition().getX() * cellSize, sd.getPosition().getY() * cellSize, cellSize, cellSize);
        }
        return img;
    }

    @Override
    protected Position getDirectionFromAction(char action) {
        if (action == 'w') return new Position(0, -1);
        if (action == 'a') return new Position(-1, 0);
        if (action == 's') return new Position(0, 1);
        if (action == 'd') return new Position(1, 0);
        return new Position(0, 0);
    }
}
