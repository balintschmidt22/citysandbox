package view;

///////////////////////////////////////////////////////////////////////////////
////This class contains the VIEW for the main panel of the game, containing////
////the buttons that represent the Fields on the map.                      ////
///////////////////////////////////////////////////////////////////////////////

import java.awt.Dimension;
import model.GameEngine;
import res.ResourceLoader;

//refractor imports
import model.Field;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import model.Road;
import model.buildings.School;
import model.buildings.Stadium;
import model.buildings.University;


/**
 * Class for the paint methods and painting the map.
 * @author Bálint
 */
public class GameGUI extends JPanel implements MouseMotionListener {
    
    // Hover feature
    private int currentX = -1;
    private int currentY = -1;
    private int prevX = -1;
    private int prevY = -1;
    private boolean isFieldSelected = false;

        
    //Connection with GameEngine class
    private GameEngine gameEngine;

    /**
     * for loading images
     */
    protected final ResourceLoader loader;
    
    //refactored attributes
    private final int BSIZE_X;
    private final int BSIZE_Y;
    private final int FIELDSIZE = 35; //Size of each squares/fields (px)
    
    //The Building Menu
    private BuildingMenu buildingMenu;
    private InfoMenu infoMenu;
    
    //Pause
    protected boolean paused = false;
    
    // Road building
    private boolean roadBuildMode = false;
    
    /**
     * Constructor with mouse events
     */
    public GameGUI(){
        super();
        //Initializing the GameEngine
        gameEngine = new GameEngine();
        loader = new ResourceLoader();
        BSIZE_X = gameEngine.getBoard().getSizeX();
        BSIZE_Y = gameEngine.getBoard().getSizeY();
        
        //Building Menu
        buildingMenu = new BuildingMenu(this);
        infoMenu = new InfoMenu(this);
        
        gameEngine.setGamegui(this);
        
        //refactored code
        addMouseListener(new MouseAdapter() { 

           
            @Override
            public void mousePressed(MouseEvent me) {
              //Only if the click is on the gamefield.  
                if(!paused){
                    if(me.getX()>0 && me.getX()<BSIZE_Y*FIELDSIZE && me.getY()>0 && me.getY()<BSIZE_X*FIELDSIZE){
                        int x = (me.getX())/FIELDSIZE;
                        int y = (me.getY())/FIELDSIZE;

                        if(gameEngine.buildable(y,x)){
                            closeMenus();
                            if (roadBuildMode) {
                                gameEngine.buildField(new Road(y,x), y, x);
                            }
                            else {
                                infoMenu.setInvisible();

                                //BUILD MENU
                                buildingMenu.setVisible();

                                //COORDINATES WERE FLIPPED SOMEWHERE ?????? wtf (probably the matrix indexing is fucking everything up)
                                buildingMenu.setCoordinates(y,x);
                            }
                            
                        }else{
                            //closing unnecessary windows
                            closeMenus();
                            buildingMenu.setInvisible();
                            if (!roadBuildMode) {
                                infoMenu.setVisible();
                                infoMenu.setCoordinates(y, x);
                            }
                        }
                    }
                }
            }

            
        });
        
        addMouseMotionListener(this);
        
        //PAUSE OPTION
        this.getInputMap().put(KeyStroke.getKeyStroke("P"), "p");
        this.getActionMap().put("p", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
                if(paused){
                    GameWindow.setPaused();
                }else{
                    GameWindow.unsetPaused();
                }
            }
        });
        
        // Exiting from road building mode
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        this.getInputMap().put(escapeKeyStroke, "exitRoadBuildMode");
        this.getActionMap().put("exitRoadBuildMode", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                roadBuildMode = false;
            }
        });
        
        this.setPreferredSize(new Dimension(
            BSIZE_Y*FIELDSIZE, 
            BSIZE_X*FIELDSIZE)
        );
    }
    
    //TICK logic

    /**
     * Depends on the game speed, responsible for game logic
     */
    public void tick() {
        //GAMEGUI tick logic here
        gameEngine.tick();
        System.out.println("Tick");
        repaint();

    }

    /**
     * Starts new game
     */
    public void restart(){
        gameEngine = new GameEngine();
        gameEngine.setGamegui(this);
        buildingMenu.setInvisible();
        closeMenus();
        infoMenu.setInvisible();
        repaint();
        paused = false;
    }
    
    /**
     * Paints the map. Some images are loaded before the cycles to achieve better performance
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Image grass = loader.getImage("grass").getImage();
        Image water = loader.getImage("water").getImage();
        Image rock2 = loader.getImage("rock2").getImage();
        Image stadium = loader.getImage("stadium").getImage();
        Image university = loader.getImage("university").getImage();
        Image school = loader.getImage("school").getImage();
        Image damaged = loader.getImage("damaged").getImage();
        for (int i = 0; i < BSIZE_X; ++i) {
            for (int j = 0; j < BSIZE_Y; ++j) {
                Field field = gameEngine.getBoard().getFieldAt(i, j);
                
                String type = field.getImage();
                switch (type) {
                    case "grass":
                        g.drawImage(grass, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE, FIELDSIZE, null);
                        break;
                    case "water":
                        g.drawImage(water, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE, FIELDSIZE, null);
                        break;
                    case "rock2":
                        g.drawImage(grass, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE, FIELDSIZE, null);
                        g.drawImage(rock2, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE, FIELDSIZE, null);
                        break;
                    case "stadium":
                        if(((Stadium)field).isTopLeft()){
                            g.drawImage(grass, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE*2, FIELDSIZE*2, null);
                            g.drawImage(stadium, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE*2, FIELDSIZE*2, null);
                        }
                        break;
                    case "university":
                        if(((University)field).isTopLeft()){
                            g.drawImage(grass, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE*2, FIELDSIZE*2, null);
                            g.drawImage(university, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE*2, FIELDSIZE*2, null);
                        }
                        break;
                    case "school":
                        if(((School)field).isLeft()){
                            g.drawImage(grass, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE*2, FIELDSIZE, null);
                            g.drawImage(school, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE*2, FIELDSIZE, null);
                        }
                        break;
                    case "damaged":
                        g.drawImage(damaged, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE*2, FIELDSIZE*2, null);
                    case "industrial_1", "service_1", "residential_1","roadTurn","roadTurn1","roadTurn2","roadTurn3",
                         "industrial_2", "industrial_3", "residential_2", "residential_3", "service_2", "service_3",
                         "police", "forest1", "forest2":
                        g.drawImage(grass, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE, FIELDSIZE, null);
                        //no break on purpose
                    default:
                        Image placeHolder = loader.getImage(type).getImage();
                        g.drawImage(placeHolder, FIELDSIZE*j, FIELDSIZE*i, FIELDSIZE, FIELDSIZE, null);
                        break;
                }
                

            }
        }
    }
    
    /**
     * Closes unnecessary menu windows
     */
    public void closeMenus(){
        if(buildingMenu.getSpecialBuildingMenu()!=null){
            buildingMenu.getSpecialBuildingMenu().setInvisible();
        }
        if(buildingMenu.getZoneBuildingMenu()!=null){
            buildingMenu.getZoneBuildingMenu().setInvisible();
        }
        if(infoMenu.getStatMenu()!=null){
            infoMenu.getStatMenu().setInvisible();
        }
    }
    
    /**
     *
     * @return
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     *
     * @return
     */
    public int getBSIZE_X() {
        return BSIZE_X;
    }

    /**
     *
     * @return
     */
    public int getBSIZE_Y() {
        return BSIZE_Y;
    }

    /**
     *
     * @return
     */
    public int getFIELDSIZE() {
        return FIELDSIZE;
    }

    /**
     *
     * @param paused
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     *
     * @return
     */
    public InfoMenu getInfoMenu() {
        return infoMenu;
    }

    /**
     * For hover function, not finished.
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     *
     * @param me
     */
    @Override
    public void mouseMoved(MouseEvent me) {
    }
    
    /**
     *
     */
    public void roadBuildModeOn() {
        roadBuildMode = true;
    }
    
    /**
     *
     * @return
     */
    public boolean getRoadBuildMode() {
        return roadBuildMode;
    }
        
    
//    @Override
//    public void mouseMoved(MouseEvent me) {
//        int x = me.getX() / FIELDSIZE;
//        int y = me.getY() / FIELDSIZE;
//
//        // Check if the mouse has moved to a new field.
//        if (prevX != x || prevY != y) {
//            // Reset the state of the previously selected field.
//            if (isFieldSelected) {
//                repaintField(prevX, prevY, isFieldSelected);
//                isFieldSelected = false;
//            }
//
//            // Update the currently selected field and draw a white rectangle over it.
//            prevX = x;
//            prevY = y;
//            repaintField(x, y, isFieldSelected);
//            isFieldSelected = true;
//        }
//    }
//    
//    private void repaintField(int x, int y, boolean deletion) {
//        Graphics g2d = this.getGraphics();
//
//        if (deletion) {
//            repaint();
//        }
//        else {
//            g2d.setColor(new Color(255, 255, 0, 100));
//            g2d.fillRect(x * FIELDSIZE, y * FIELDSIZE, FIELDSIZE, FIELDSIZE);   
//        }
//    }
}