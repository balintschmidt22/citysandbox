package view;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The menu when the player starts the game
 * @author bruno
 */
public class WelcomeWindow {
    private static JFrame frame;
    private static JLabel label;
    private static JPanel panel;
    private JButton buttonNew;
    private JButton buttonExit;

    /**
     *
     */
    public WelcomeWindow(){
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon("src/res/background3.jpg");
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
            }    
        };
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        Font menuFont = new Font("Arial", Font.BOLD, 40);
        Font buttonFont = new Font("Arial", Font.PLAIN, 15);
        frame = new JFrame("Main Window");
        ImageIcon icon = new ImageIcon("src/res/name.png");
        Image img = icon.getImage().getScaledInstance(350, 120, Image.SCALE_SMOOTH);
        this.label = new JLabel(new ImageIcon(img));
        label.setFont(menuFont);
        label.setOpaque(true);
        Dimension buttonSize = new Dimension(400, 50);
        buttonNew = new JButton();
        buttonExit = new JButton();
        buttonNew.setText("New Game");
        buttonExit.setText("Exit");
        buttonNew.setFont(buttonFont);
        buttonExit.setFont(buttonFont);
        buttonNew.setPreferredSize(buttonSize);
        buttonExit.setPreferredSize(buttonSize);
        buttonNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow gamewindow = new GameWindow();
                GameWindow.date = LocalDate.now();
                frame.setVisible(false);
            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        label.setAlignmentX(frame.CENTER_ALIGNMENT);
        buttonExit.setAlignmentX(frame.CENTER_ALIGNMENT);
        buttonNew.setAlignmentX(frame.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(130));
        panel.add(Box.createVerticalGlue());
                        panel.add(label);
        panel.add(Box.createVerticalStrut(150));
               panel.add(Box.createVerticalGlue());
        panel.add(buttonNew);
        panel.add(Box.createVerticalStrut(20));
               panel.add(Box.createVerticalGlue());
        panel.add(buttonExit);
        panel.add(Box.createVerticalStrut(200));
        panel.add(Box.createVerticalGlue());
        panel.setPreferredSize(new Dimension(700, 700)); // Set the preferred size of the panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
