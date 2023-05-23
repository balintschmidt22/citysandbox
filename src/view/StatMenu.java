package view;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.pop.Person;
import model.zones.Zone;

/**
 * Show the stats of people in the selected zone
 * @author Bálint
 */
public class StatMenu {
    private final GameGUI gameGUI;
    private boolean visible = false;
    private final JFrame frame;
    private final JLabel label;
    private final Box vbox;

    //coordinates used to call other methods
    private int x;
    private int y;
    
    /**
     *
     * @param gameGUI
     */
    public StatMenu(GameGUI gameGUI) {
        //init
        this.gameGUI = gameGUI;
        
        // Create the pop-up window
        frame = new JFrame("Stat Menu");
        frame.setMinimumSize(new Dimension(200, 400));
        
        // Create the label to display the currently selected field
        this.label = new JLabel("Default text");
        label.setHorizontalAlignment(JLabel.CENTER); // center align the text


        // Create a vertical box layout for the buttons
        vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(10)); // Add a little space between the label and the buttons
        vbox.add(label);
        vbox.add(Box.createVerticalStrut(10)); // Add a little space between the buttons


        // Add the buttons to the frame
        frame.add(vbox);

        // Show the pop-up window
        frame.setVisible(visible);
    }
    
    // Method to set the BuildingMenu frame to visible, gets called by GameGUI
    // when a field is clicked

    /**
     *
     */
    public void setVisible() {
        visible = true;
        frame.setVisible(visible);
    }
    
    /**
     *
     */
    public void setInvisible() {
        visible = false;
        frame.setVisible(visible);
    }
    
    // Method to update the currently selected coordinates

    /**
     *
     * @param x
     * @param y
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
        updateLabelText();
    }
    
    // Method to update the label's text

    /**
     *
     */
    public void updateLabelText() {
        var field = gameGUI.getGameEngine().getFieldAt(x, y);
        if(field instanceof Zone zone){
            Component[] comps = vbox.getComponents();
            for(Component comp : comps){
                vbox.remove(comp);
            }
            String text = "Currently selected: ";

            text += "x: " + String.valueOf(this.y) + " y: " + String.valueOf(this.x);
            label.setText(text);

            vbox.add(label);
            vbox.add(Box.createVerticalStrut(10));

            for(Person p : zone.getPeopleInZone()){
                JLabel stat = new JLabel("");
                stat.setText("Age: " + String.valueOf(p.getAge()) + " Happiness: " + String.valueOf(p.getHappiness())+ " Education: " + String.valueOf(p.getEducation()));
                vbox.add(stat);
            }
        }
        frame.repaint();
        frame.pack();
    }
}

