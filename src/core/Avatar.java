package core;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Avatar {
    
    private String file_name = "";
    private BufferedImage img;
    private ImageIcon icon;

    public Avatar(String file_name, BufferedImage img) {
        this.file_name = file_name;
        this.img = img;
        this.icon = new ImageIcon(img);
        //this.icon = new ImageIcon(img.getScaledInstance(Constants.TILE_X, Constants.TILE_Y, Image.SCALE_SMOOTH));
    }

    public String getFileName() {
        return file_name;
    }

    public BufferedImage getImg() {
        return img;
    }

    public ImageIcon getIcon() {
        return icon;
    }

       
}
