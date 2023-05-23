package view;

/**
 *
 * @author bruno
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import model.buildings.Forest;
import model.buildings.Police;
import model.buildings.School;
import model.buildings.Stadium;
import model.buildings.University;

/**
 * Class for building the special elements.
 * @author Bálint
 */
public class SpecialBuildingMenu {
    
    private final GameGUI gameGUI;
    private boolean visible = false;
    private final JFrame frame;
    private final JLabel label;


    //coordinates used to call other methods
    private int x;
    private int y;
    
    /**
     *
     * @param gameGUI
     */
    public SpecialBuildingMenu(GameGUI gameGUI) {
        //init
        this.gameGUI = gameGUI;
        
        // Create the pop-up window
        frame = new JFrame("Special Building Menu");
        frame.setSize(300, 600);
        
        // Create the label to display the currently selected field
        this.label = new JLabel("Default text");
        label.setHorizontalAlignment(JLabel.CENTER); // center align the text

        // Create the buttons with pictures and text
        JButton button1 = new JButton();
        button1.setIcon(gameGUI.loader.getImage("forest"));
        button1.setText("Forest - 1000$");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI.getGameEngine().buildField(new Forest(x, y), x, y);
                setInvisible();
            }
        });

        JButton button2 = new JButton();
        button2.setIcon(gameGUI.loader.getImage("stadium"));
        button2.setText("Stadium - 2500$");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI.getGameEngine().buildField(new Stadium(x, y), x, y);
                setInvisible();
            }
        });

        JButton button3 = new JButton();
        button3.setIcon(gameGUI.loader.getImage("police"));
        button3.setText("Police - 1500$");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI.getGameEngine().buildField(new Police(x, y), x, y);
                setInvisible();
            }
        });
        JButton button4 = new JButton();
        button4.setIcon(gameGUI.loader.getImage("school"));
        button4.setText("School - 2000$");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI.getGameEngine().buildField(new School(x, y), x, y);
                setInvisible();
            }
        });
        JButton button5 = new JButton();
        button5.setIcon(gameGUI.loader.getImage("university"));
        button5.setText("University - 3500$");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI.getGameEngine().buildField(new University(x, y), x, y);
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
        button4.setHorizontalAlignment(SwingConstants.CENTER);
        button5.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the size of the button labels to fit the buttons
        button1.setPreferredSize(buttonSize);
        button1.setFont(buttonFont);
        button2.setPreferredSize(buttonSize);
        button2.setFont(buttonFont);
        button3.setPreferredSize(buttonSize);
        button3.setFont(buttonFont);
        button4.setFont(buttonFont);
        button4.setPreferredSize(buttonSize);
        button5.setFont(buttonFont);
        button5.setPreferredSize(buttonSize);

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
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(button4);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(button5);

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
     * Sets the coordinates of the selected field.
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
     * Updates the label in the menu.
     */
    public void updateLabelText() {
        String text = "Currently selected: ";
        text += "x: " + String.valueOf(this.y) + " y: " + String.valueOf(this.x);
        
        label.setText(text);
    }

}
