package com.base.ui;

import com.base.IScreenToCellConverter;
import com.base.Position;
import com.base.entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WorldMapView extends JPanel {
    private Image image;
    private IEntitySpawnActionListener listener;
    private IScreenToCellConverter converter;

    public WorldMapView() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Position position = converter.convertToCellPosition(e.getX(), e.getY());

                String[] spawnableEntities = new String[]{"Sheep", "Wolf", "Fox", "Antelope", "Turtle", "CyberSheep", "Belladonna", "Grass", "Dandelion", "Hogweed", "Guarana"};
                String string = (String) JOptionPane.showInputDialog(getParent(), "Pick an entity", "Entity picker", JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("FileView.fileIcon"), spawnableEntities, spawnableEntities[0]);
                Entity entity = null;
                if (string == null || string.equals("null")) return;
                try {
                    entity = (Entity) Class.forName("com.base.entities.animals." + string).getConstructor(Position.class).newInstance(position);
                } catch (ClassNotFoundException notFound) {
                    try {
                        entity = (Entity) Class.forName("com.base.entities.plants." + string).getConstructor(Position.class).newInstance(position);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                listener.onEntitySpawnAction(position, entity);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void setCellConverter(IScreenToCellConverter converter) {
        this.converter = converter;
    }

    public void setOnEntitySpawnAction(IEntitySpawnActionListener listener) {
        this.listener = listener;
    }

    public void setImage(Image image) {
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
