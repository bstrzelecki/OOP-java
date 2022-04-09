package com.base;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HexWorld extends World {
    public HexWorld(int sizeX, int sizeY) {
        super(sizeX, sizeY);
        cellSize = 16;
    }

    @Override
    public ArrayList<Position> getNeighbouringTiles(Position pos) {
        ArrayList<Position> output = new ArrayList<>();
        if (pos.getX() > 0) {
            output.add(new Position(pos.getX() - 1, pos.getY()));
            if (pos.getY() < getSizeY() - 1) {
                output.add(new Position(pos.getX() - 1, pos.getY() + 1));
            }
        }
        if (pos.getX() < getSizeX() - 1) {
            output.add(new Position(pos.getX() + 1, pos.getY()));
            if (pos.getY() > 0) {
                output.add(new Position(pos.getX() + 1, pos.getY() - 1));
            }
        }
        if (pos.getY() < getSizeY() - 1) {
            output.add(new Position(pos.getX(), pos.getY() + 1));
        }
        if (pos.getY() > 0) {
            output.add(new Position(pos.getX(), pos.getY() - 1));
        }
        return output;
    }

    @Override
    public IScreenToCellConverter getConverter() {
        return (x, y) -> {
            int cellX = 0;
            int cellY = 0;
            int cellSize = 16;
            float min = Float.MAX_VALUE;
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {

                    float dx = Math.abs(x - ((i * cellSize * 2) + (j * cellSize) + cellSize));
                    float dy = Math.abs(y - ((j * cellSize * 2) + cellSize));
                    float next = (float) Math.sqrt(dx * dx + dy * dy);

                    if (next < min) {
                        min = next;
                        cellX = i;
                        cellY = j;
                    }
                }
            }
            return new Position(cellX, cellY);
        };
    }

    @Override
    protected Image parseRenderBatch() {
        BufferedImage img = new BufferedImage(sizeX * cellSize * 2 + sizeY * cellSize - cellSize,
                sizeY * cellSize * 2, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = img.getGraphics();
        int horizontalOffset = cellSize;
        for (SpriteDescriptor sd : batch.getSprites()) {
            graphics.setColor(sd.getColor());

            Polygon hex = new Polygon();
            for (int i = 0; i < 6; i++) {
                int x = (int) (cellSize + sd.getPosition().getY() * horizontalOffset +
                        2 * cellSize * sd.getPosition().getX() + cellSize * Math.sin(i * 2 * Math.PI / 6));
                int y = (int) (cellSize + 2 * cellSize * sd.getPosition().getY()
                        - cellSize * Math.cos(i * 2 * Math.PI / 6));
                hex.addPoint(x, y);
            }
            graphics.drawString(String.valueOf(sd.getSymbol()),
                    cellSize + sd.getPosition().getY() * horizontalOffset +
                            sd.getPosition().getX() * 2 * cellSize - cellSize / 4,
                    cellSize + sd.getPosition().getY() * 2 * cellSize + cellSize / 4);
            graphics.drawPolygon(hex);
        }
        return img;
    }

    @Override
    protected Position getDirectionFromAction(char action) {
        if (action == 'q') return new Position(0, -1);
        if (action == 'w') return new Position(1, -1);
        if (action == 'd') return new Position(0, 1);
        if (action == 's') return new Position(-1, 1);
        if (action == 'e') return new Position(1, 0);
        if (action == 'a') return new Position(-1, 0);
        return new Position(0, 0);
    }
}
