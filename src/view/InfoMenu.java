package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import model.Road;
import model.buildings.School;
import model.zones.Zone;
import model.buildings.SpecialBuilding;
import model.buildings.University;

/**
 * Class for the informations menu
 * @author Bálint
 */
public class InfoMenu {
    private final GameGUI gameGUI;
    private final Box vbox;
    private final JButton removeButton;
    private final JButton upgradeButton;
    private final JButton incButton;
    private final JButton decButton;
    private final JButton statsButton;
    private final JButton repairButton;
    private boolean visible = false;
    private final JFrame frame;
    private final JLabel pos;
    private final JLabel capacity;
    private final JLabel currentPpl;
    private final JLabel isConnectedToRoad;
    private final JLabel connectedWorkplaces;
    private final JLabel tax;
    private final JLabel happiness;
    private final JLabel upkeep;
    private final JLabel setTax;
    private final JLabel students;
    private final Component space;
    private final Component space2;
    private StatMenu statMenu;
    
    //coordinates used to call other methods
    private int x;
    private int y;
    
    /**
     *
     * @param gameGUI
     */
    public InfoMenu(GameGUI gameGUI) {
        //init
        this.gameGUI = gameGUI;
        
        // Create the pop-up window
        frame = new JFrame("Info Menu");
        frame.setMinimumSize(new Dimension(300, 300));
        
        space = Box.createVerticalStrut(10);
        space2 = Box.createVerticalStrut(10);
        // Create the label to display the currently selected field
        this.pos = new JLabel("Default text");
        pos.setHorizontalAlignment(JLabel.CENTER); // center align the text
        capacity = new JLabel("");
        capacity.setHorizontalAlignment(JLabel.CENTER);
        currentPpl = new JLabel("");
        currentPpl.setHorizontalAlignment(JLabel.CENTER);
        isConnectedToRoad = new JLabel("");
        isConnectedToRoad.setHorizontalAlignment(JLabel.CENTER);
        connectedWorkplaces = new JLabel("");
        connectedWorkplaces.setHorizontalAlignment(JLabel.CENTER);
        happiness = new JLabel("");
        happiness.setHorizontalAlignment(JLabel.CENTER);
        upkeep = new JLabel("");
        upkeep.setHorizontalAlignment(JLabel.CENTER);
        tax = new JLabel("");
        tax.setHorizontalAlignment(JLabel.CENTER);
        setTax = new JLabel("");
        setTax.setText("Tax");
        setTax.setHorizontalAlignment(JLabel.CENTER);
        students = new JLabel("");
        students.setHorizontalAlignment(JLabel.CENTER);
        
        
        // Create the buttons with pictures and text
        removeButton = new JButton();
        removeButton.setText("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI.getGameEngine().removeField(x, y);
                setInvisible();
            }
        });
        
        // This button checks for connected workplaces (placeholder)
        upgradeButton = new JButton();
        upgradeButton.setText("Upgrade");
        upgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**/
                gameGUI.getGameEngine().upgradeField(x,y);
                updateButtons();
            }
        });
        
        // This button manages the repair function of damaged buildings
        repairButton = new JButton();
        repairButton.setText("Repair - 500");
        repairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**/
                gameGUI.getGameEngine().repairBuilding(x,y);
                vbox.remove(repairButton);
                
            }
        });
        
        //Increasing tax
        incButton = new JButton();
        incButton.setText("+");
        incButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var field = gameGUI.getGameEngine().getFieldAt(x, y);
                Zone zone = (Zone)field;
                if(zone.getTaxMultiplier() < 60){
                    zone.setTaxMultiplier(zone.getTaxMultiplier()+1);
                    zone.setTax();
                    zone.changeHappiness(-3);
                    updateLabelText();
                }
            }
        });
        
        //Decreasing tax
        decButton = new JButton();
        decButton.setText("-");
        decButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var field = gameGUI.getGameEngine().getFieldAt(x, y);
                Zone zone = (Zone)field;
                if(zone.getTaxMultiplier() > 15){
                    zone.setTaxMultiplier(zone.getTaxMultiplier()-1);
                    zone.setTax();
                    zone.changeHappiness(3);
                    updateLabelText();
                }
            }
        });
        
        statsButton = new JButton();
        statsButton.setText("Stats");
        statsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                statMenu = new StatMenu(gameGUI);
                statMenu.setVisible();
                statMenu.setCoordinates(x,y);
                setInvisible();
            }
        });
        
        // Set the size of the buttons
        Dimension buttonSize = new Dimension(400, 50);
        Dimension taxButtonSize = new Dimension(80, 50);
        
        // Set the font of the button labels
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        // Set the alignment of the button labels to center
        removeButton.setHorizontalAlignment(SwingConstants.CENTER);
        upgradeButton.setHorizontalAlignment(SwingConstants.CENTER);
        statsButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Set the size of the button labels to fit the buttons
        removeButton.setPreferredSize(buttonSize);
        removeButton.setFont(buttonFont);
        upgradeButton.setPreferredSize(buttonSize);
        upgradeButton.setFont(buttonFont);
        statsButton.setPreferredSize(buttonSize);
        statsButton.setFont(buttonFont);
        
        incButton.setHorizontalAlignment(SwingConstants.CENTER);
        decButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        removeButton.setPreferredSize(taxButtonSize);
        removeButton.setFont(buttonFont);
        upgradeButton.setPreferredSize(taxButtonSize);
        upgradeButton.setFont(buttonFont);

        // Create a vertical box layout for the buttons
        vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(10)); // Add a little space between the label and the buttons
        vbox.add(pos);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(capacity);
        vbox.add(upkeep);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(currentPpl);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(happiness);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(tax);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(isConnectedToRoad);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(connectedWorkplaces);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(students);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(incButton);
        vbox.add(setTax);
        vbox.add(decButton); 
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(removeButton);
        vbox.add(upgradeButton);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(statsButton);
        vbox.add(repairButton);
        

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
        //updateButtons();
        visible = true;
        frame.setVisible(visible);
    }
    
    /**
     *
     */
    public void setInvisible() {
        //updateButtons();
        visible = false;
        frame.setVisible(visible);
    }
    
    // Method to update the currently selected coordinates

    /**
     * Sets the coordinates of the selected field
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
     * Updates the informations in the menu.
     */
    public void updateLabelText() {
        String text = "Currently selected: ";
        text += "x: " + String.valueOf(this.y) + " y: " + String.valueOf(this.x);
        
        pos.setText(text);
        
        var field = gameGUI.getGameEngine().getFieldAt(x, y);
        if(field instanceof Zone zone){
            capacity.setText("Capacity: " + String.valueOf(zone.getCapacity()));
            currentPpl.setText("Current people: " + String.valueOf(zone.getCurrentPeople()));
            happiness.setText("Happiness: " + String.valueOf(zone.getHappiness()));
            upkeep.setText("");
            students.setText("");
            tax.setText("Current tax: " + String.valueOf(zone.getCurrentTax()) + "$");
            isConnectedToRoad.setText("Connected to the main road: " + String.valueOf(((Zone)field).isConnectedToMainRoad()));

            
//            if (field instanceof Residential) {
//                
//                String workplaceConnectionMessage = "Connected to the following workplaces: ";
//                    if (((Residential)field).getConnectedWorkplaces() != null) {
//                        for (int i = 0; i < ((Residential)field).getConnectedWorkplaces().size(); i++) {
//                            // coordinates are flipped here, again :DDDD
//                            workplaceConnectionMessage += "x= " + ((Residential)field).getConnectedWorkplaces().get(i).getPosY();
//                            workplaceConnectionMessage += "y= " + ((Residential)field).getConnectedWorkplaces().get(i).getPosX();
//                            workplaceConnectionMessage += ";    ";
//
//                        }
//                    }
//                    connectedWorkplaces.setText(workplaceConnectionMessage);
//                
//            }
//            else {
//                connectedWorkplaces.setText("");
//            }
        }else if(field instanceof SpecialBuilding sb){
            capacity.setText("");
            upkeep.setText("Upkeep: " + String.valueOf(sb.getUpkeep()) + "$");
            tax.setText("");
            happiness.setText("");
            currentPpl.setText("");
            isConnectedToRoad.setText("Connected to the main road: " + String.valueOf(((SpecialBuilding)field).isConnectedToMainRoad()));
            connectedWorkplaces.setText("");
            students.setText("");
            if (sb instanceof School s){
            students.setText("Students: " +s.getStudents().size());
            }
            if (sb instanceof University u){
            students.setText("University students: " +u.getStudents().size());
            }
            
        }else if(field instanceof Road rd){
            capacity.setText("");
            upkeep.setText("Upkeep: " + String.valueOf(rd.getUpkeep()) + "$");
            tax.setText("");
            happiness.setText("");
            currentPpl.setText("");
            isConnectedToRoad.setText("");
            connectedWorkplaces.setText("");
            students.setText("");

        }
        else{
            capacity.setText("");
            upkeep.setText("");
            tax.setText("");
            happiness.setText("");
            currentPpl.setText("");
            isConnectedToRoad.setText("");
            connectedWorkplaces.setText("");
            students.setText("");
        }
        updateButtons();
        frame.pack();
        frame.repaint();
    }
    
    /**
     *
     */
    public void updateButtons() {
        var field = gameGUI.getGameEngine().getFieldAt(x, y);
        Component[] comps = vbox.getComponents();
        for(Component comp : comps){
            if(comp instanceof JButton){
                vbox.remove(comp);
            }else if(setTax.equals(comp)){
                vbox.remove(comp);
            }else if(space.equals(comp)){
                vbox.remove(space);
            }else if(space2.equals(comp)){
                vbox.remove(space2);
            }
        }
        if (field instanceof Zone zone && zone.getCurrentPeople() > 0) {
            vbox.add(upgradeButton);
            String upgradeText = "Upgrade - ";
            if ((((Zone)field)).getUpgradeState() != 3) {
                upgradeText += ((Zone)field).getBuildingCost();
            }
            else {
                upgradeText += "MAX";
            }
            upgradeButton.setText(upgradeText);
            vbox.add(incButton);
            vbox.add(setTax);
            vbox.add(decButton);
            vbox.add(space2);
            vbox.add(upgradeButton);
            vbox.add(space);
            vbox.add(statsButton);
        }
        else{
            vbox.add(removeButton);
        }
        
        if (field.isDamaged() && !field.isRepairing()) {
            vbox.add(repairButton);
        }
    }

    /**
     *
     * @return
     */
    public StatMenu getStatMenu() {
        return statMenu;
    }
}
