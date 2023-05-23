package view;

///////////////////////////////////////////////////////////////////////////////
////This class contains the VIEW for the Main Game Window and the different////
////menues and options on the top and the stat displays on the bottom row. ////
///////////////////////////////////////////////////////////////////////////////

//test for my personal branch

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The frame of the game, with informations and menupoints
 * @author Bálint
 */
public class GameWindow {
    private final GameGUI gameGUI;
    
    //Refractored attributes

    /**
     * Speed of the game. Default: 2 sec
     */
    public static int FREQ = 2000;
    
    //Swing GUI elements
    private static JFrame frame;
    private JPanel bottomPanel;
    private JLabel time;
    private JLabel budget;
    private JLabel income;
    private JLabel happiness;
    private JLabel population;
    private static JLabel pausedLabel;
    private static JLabel roadBuildingLabel;
    //Must be here because of forest

    /**
     * Current date in the game
     */
    public static LocalDate date = LocalDate.now();
    private int month;
    
    //Timer variable

    /**
     * Timer for tick method and repainting
     */
    public static Timer timer;
    private Timer timer2;
    
    /**
     *
     */
    public GameWindow(){
        //Initializing the GameGUI
        gameGUI = new GameGUI();
        
        frame = new JFrame("Sandbox City");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().add(gameGUI, BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        //NEW GAME & EXIT MENU
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        
        JMenuItem exitMenuItem = new JMenuItem("Exit to main menu");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                WelcomeWindow gamewindow = new WelcomeWindow();
                frame.dispose();
            }
        });
        
        
        //Speed menu
        JMenu gameSpeed = new JMenu("Game Speed");
        //gameSpeed.setPreferredSize(new Dimension(80, 30));
        menuBar.add(gameSpeed);
        
        
        JMenuItem fasterMenuItem = new JMenuItem("Faster");
        gameSpeed.add(fasterMenuItem);
        fasterMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                FREQ = 1000;
                timer.setDelay(FREQ);
                //2x faster
            }
        });
        
        JMenuItem normalMenuItem = new JMenuItem("Normal");
        gameSpeed.add(normalMenuItem);
        normalMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                FREQ = 2000;
                timer.setDelay(FREQ);
            }
        });
        
        JMenuItem slowerMenuItem = new JMenuItem("Slower");
        gameSpeed.add(slowerMenuItem);
        slowerMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                FREQ = 5000;
                timer.setDelay(FREQ);
                //2x slower
            }
        });
        
        // Catastrophe menu
        JMenu catastropheMenu = new JMenu("Catastrophe");
        menuBar.add(catastropheMenu);
        
        JMenuItem invoke1MenuItem = new JMenuItem("Residential");
        catastropheMenu.add(invoke1MenuItem);
        invoke1MenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                gameGUI.getGameEngine().getCatastrophe().invokeDisaster(1);
            }
        });
        JMenuItem invoke2MenuItem = new JMenuItem("Industrial");
        catastropheMenu.add(invoke2MenuItem);
        invoke2MenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                gameGUI.getGameEngine().getCatastrophe().invokeDisaster(2);
            }
        });
        JMenuItem invoke3MenuItem = new JMenuItem("Service");
        catastropheMenu.add(invoke3MenuItem);
        invoke3MenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                gameGUI.getGameEngine().getCatastrophe().invokeDisaster(3);
            }
        });
        JMenuItem invoke4MenuItem = new JMenuItem("Special");
        catastropheMenu.add(invoke4MenuItem);
        invoke4MenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                gameGUI.getGameEngine().getCatastrophe().invokeDisaster(4);
            }
        });
        
        //bottom panel
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        frame.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setPreferredSize(new Dimension(frame.getWidth(), 30));
        
        time = new JLabel("Time: ");
        budget = new JLabel("Budget: ");
        income = new JLabel("Income: ");
        happiness = new JLabel("Happiness: ");
        population = new JLabel("Population: ");
        pausedLabel = new JLabel();
        roadBuildingLabel = new JLabel();
        
        bottomPanel.add(time);
        bottomPanel.add(budget);
        bottomPanel.add(income);
        bottomPanel.add(happiness);
        bottomPanel.add(population);
        bottomPanel.add(pausedLabel);
        bottomPanel.add(roadBuildingLabel);
        
        
        //Frame final methods called
        
        //Sets the size of the frame to match the board size
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        //Starts the game tick timer
        timer = new Timer(FREQ, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                month = date.getMonthValue();
                if(month == 1){
                    gameGUI.getGameEngine().newYear();
                }
                tick();
                bottomBarUpdate();
            }
        });
        
        //If the game is e.g. on slow mode the happiness status changes with a lot of delay
        //which ruins the gameplay
        timer2 = new Timer(150, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                gameGUI.getGameEngine().setAvgHappiness();
                happiness.setText("Happiness: " + gameGUI.getGameEngine().getAvgHappiness());
                budget.setText("Budget: " + gameGUI.getGameEngine().getBudget() + " $");
                if (gameGUI.getRoadBuildMode()) {
                    roadBuildingLabel.setText("      Road Building mode on! Press ESC to exit");
                }
                if (!gameGUI.getRoadBuildMode()) {
                    roadBuildingLabel.setText("");
                }
            }
        });
        
        bottomBarUpdate();
        timer.start();
        timer2.start();
    }
    
    /**
     *
     */
    public void tick(){
        if(!gameGUI.paused){
            gameGUI.tick();
        }
    }
    
    /**
     * Updates the labels in the bottom status bar.
     */
    public void bottomBarUpdate(){

        if(!gameGUI.paused){
            time.setText("Date: " + date);
            income.setText("Income: " + gameGUI.getGameEngine().getIncome() + " $");
            population.setText("Population: " + gameGUI.getGameEngine().getPop());
            unsetPaused();
            
            date = date.plusMonths(1); //+1 month every second
            

        }
        else {
            setPaused();
        }

    }
    
    /**
     *
     */
    public static void setPaused(){
        //Method to set paused label instantly when 'p' is pressed
        pausedLabel.setText("         Game is Paused!");
    }
    
    /**
     *
     */
    public static void unsetPaused(){
        pausedLabel.setText("");
    }
    
    /**
     *
     * @return
     */
    public static JFrame getFrame() {
        return frame;
    }
    
    /**
     *
     */
    public static void roadBuildingOn() {
        roadBuildingLabel.setText("      Road Building mode on! Press ESC to exit");
    }

    /**
     *
     */
    public static void roadBuildingOff() {
        roadBuildingLabel.setText("");
    }
}
