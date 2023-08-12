package views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import controllers.GridComponent;
import controllers.SpriteComponent;
import events.SpriteMoveListener;
import utils.Constants;

public class GamePanel extends JPanel implements ContainerListener {
    
    private GridComponent[] gridComponents;
    private ArrayList<SpriteComponent> sprites;
    private SpriteMoveListener spriteMoveListener;
    private int numRows = Constants.TILE_ROWS; //num Tiles across Game window
    private int numCols = Constants.TILE_COLS;

    public GamePanel() {
        super();
        setSize(new Dimension(Constants.PIXELS_X, Constants.PIXELS_Y)); //Xtile x row and Ytile x col
        setLayout(new GridLayout(numRows, numCols));

        sprites = new ArrayList<>();
        gridComponents = new GridComponent[Constants.GRID_LENGTH]; //row x col

        for (int i = 0; i < gridComponents.length; i++) {
            gridComponents[i] = new GridComponent(i);
            gridComponents[i].addContainerListener(this);
            add(gridComponents[i]);
        }
    }

    public int getMapRows() {
        return this.numRows;
    }
    public int getMapCols() {
        return this.numCols;
    }
    public GridComponent[] getGrid() {
        return this.gridComponents;
    }
    public SpriteComponent getSpriteById(int id) {
        return this.sprites.get(id);
    }

    public int addSprite(BufferedImage img, int x, int y) {
        SpriteComponent sprite = new SpriteComponent(sprites.size());
        sprite.setImage(img);
        sprite.setSpriteMoveListener(spriteMoveListener);
        sprites.add(sprite);
        int gridIdx = getGridIdx(x, y);
        gridComponents[gridIdx].setSprite(sprite);
        return sprites.size() - 1;
    }

    public void removeSprite(int sprite_id, int x, int y) {
        //remove sprite from grid
        int gridIdx = getGridIdx(x, y);
        gridComponents[gridIdx].removeSpriteFromGrid();
        //remove sprite by id from sprite list
        sprites.remove(sprite_id);
        //update all sprite ids
        for(int i = 0; i < sprites.size(); i++) {
            sprites.get(i).setId(i);
        }
    }

    public int getGridIdx(int x, int y) {
        return y * numCols + x;
    }

    public void addSpriteMoveListener(SpriteMoveListener listener) {
        this.spriteMoveListener = listener;
    }

    /**
     * Converts pixel coordinates to the grid location.
     * @param x relative x pixel value with respect to the frame.
     * @param y relative y pixel value with respect to the frame.
     * @return A <code>Point</code> containing the grid location.
     */
    public Point getGridLocation(int x, int y) {
        int gridWidth = gridComponents[0].getWidth();
        int gridHeight = gridComponents[0].getHeight();

        return new Point(x / gridWidth, y / gridHeight);
    }

    @Override
    public void componentAdded(ContainerEvent containerEvent) {
        if (containerEvent.getContainer() instanceof GridComponent) {
            var gridComponent = (GridComponent) containerEvent.getContainer();
            if (containerEvent.getChild() instanceof SpriteComponent) {
                SpriteComponent spriteComponent = (SpriteComponent) containerEvent.getChild();
                int x = gridComponent.getId() % numCols;
                int y = gridComponent.getId() / numRows;
                spriteMoveListener.spriteMoved(spriteComponent.getId(), x, y);
            }
        }
    }

    @Override
    public void componentRemoved(ContainerEvent containerEvent) {}
}
