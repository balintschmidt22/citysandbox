package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import model.*;

/**
 * Class for the basic building menu which appears when clicking on a buildable field.
 * @author Arni
 */
public class BuildingMenu {
    
    private final GameGUI gameGUI;
    private boolean visible = false;
    private final JFrame frame;
    private final JLabel label;
    private ZoneBuildingMenu zoneBuildingMenu;
    private SpecialBuildingMenu specialBuildingMenu;

    //coordinates used to call other methods
    private int x;
    private int y;
    
    /**
     *
     * @param gameGUI
     */
    public BuildingMenu(GameGUI gameGUI) {
        //init
        this.gameGUI = gameGUI;
        zoneBuildingMenu=new ZoneBuildingMenu(gameGUI);
        specialBuildingMenu=new SpecialBuildingMenu(gameGUI);

        
        // Create the pop-up window
        frame = new JFrame("City Building Menu");
        frame.setSize(300, 600);
        
        // Create the label to display the currently selected field
        this.label = new JLabel("Default text");
        label.setHorizontalAlignment(JLabel.CENTER); // center align the text

        // Create the buttons with pictures and text
        JButton button1 = new JButton();
        button1.setIcon(gameGUI.loader.getImage("zone"));
        button1.setText("Zones");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoneBuildingMenu.setVisible();
                zoneBuildingMenu.setCoordinates(x,y);
                setInvisible();
            }
        });

        JButton button2 = new JButton();
        button2.setIcon(gameGUI.loader.getImage("specialBuilding"));
        button2.setText("Special buildings");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                specialBuildingMenu.setVisible();
                specialBuildingMenu.setCoordinates(x,y);
                setInvisible();
            }
        });

        JButton button3 = new JButton();
        button3.setIcon(gameGUI.loader.getImage("road"));
        button3.setText("Road Building Mode");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI.roadBuildModeOn();
                setInvisible();
            }
        });
        
        // Set the size of the buttons
        Dimension buttonSize = new Dimension(400, 50);

        // Set the font of the button labels
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        // Set the alignment of the button labels to center
        button1.setHorizontalAlignment(SwingConstants.CENTER);
        button2.setHorizontalAlignment(SwingConstants.CENTER);
        button3.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the size of the button labels to fit the buttons
        button1.setPreferredSize(buttonSize);
        button1.setFont(buttonFont);
        button2.setPreferredSize(buttonSize);
        button2.setFont(buttonFont);
        button3.setPreferredSize(buttonSize);
        button3.setFont(buttonFont);

        // Create a vertical box layout for the buttons
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(10)); // Add a little space between the label and the buttons
        vbox.add(label);
        vbox.add(Box.createVerticalStrut(10)); // Add a little space between the buttons
        vbox.add(button1);
        vbox.add(Box.createVerticalStrut(10)); // Add a little space between the buttons
        vbox.add(button2);
        vbox.add(Box.createVerticalStrut(10)); // Add a little space between the buttons
        vbox.add(button3);

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
     * Updates the coordinate of the field in the label
     */
    public void updateLabelText() {
        String text = "Currently selected: ";
        text += "x: " + String.valueOf(this.y) + " y: " + String.valueOf(this.x);
        
        label.setText(text);
    }

    /**
     *
     * @return
     */
    public ZoneBuildingMenu getZoneBuildingMenu() {
        return zoneBuildingMenu;
    }

    /**
     *
     * @return
     */
    public SpecialBuildingMenu getSpecialBuildingMenu() {
        return specialBuildingMenu;
    }

}
