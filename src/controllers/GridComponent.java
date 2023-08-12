package controllers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JComponent;

import utils.Constants;

public class GridComponent extends JComponent {
    private int id;
    private SpriteComponent sprite;
    private BufferedImage background;

    public GridComponent(int id) {
        this.id = id;
        this.background = randomBackground();
    }

    private BufferedImage randomBackground() {
        Random rand = new Random();
        int rand_num = rand.nextInt(LoadSave.loaded_backgrounds.size());
        return LoadSave.loaded_backgrounds.get(rand_num);
    }

    public void setSprite(SpriteComponent sprite) {
        this.sprite = sprite;

        if (sprite != null) {
            sprite.setSource(this);
            sprite.setMoveContainer(this.getParent());
            add(sprite);
            repaint();
        }
    }

    public void removeSpriteFromGrid() {
        remove(sprite);
        this.sprite = null;
        repaint();
    }

    public boolean isGridComponentOccupied() {
        return (sprite != null);
    }

    public int getId() {
        return this.id;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constants.BASE_TILE_X, Constants.BASE_TILE_Y); // tile size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        if (sprite != null) {
            sprite.setSize(getWidth(), getHeight());
        }
    }
}
