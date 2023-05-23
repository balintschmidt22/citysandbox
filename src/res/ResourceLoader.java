package res;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Class for loading images
 * @author bruno
 */
public class ResourceLoader {
    private ImageIcon img2 = new ImageIcon();    
    private ImageIcon img = new ImageIcon();    
    private Image newimg;
    
    public ResourceLoader(){};
    
    public ImageIcon getImage(String name){
        load(name);
        return img2;
    }
    
    /**
     * Loads png with given name
     * @param name 
     */
    public void load(String name){
        //Only works with png!
        img = new ImageIcon("src/res/" + name + ".png");
        newimg = img.getImage().getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
        img2 = new ImageIcon(newimg); 
    }
}
