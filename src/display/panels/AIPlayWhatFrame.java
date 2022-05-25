package display.panels;

import display.Game1;
import game_issue.Game;
import game_issue.Mode;

import javax.swing.*;

public class AIPlayWhatFrame extends JFrame {
    JLabel label = new JLabel("You play");
    JButton white = new JButton("WHITE");
    JButton black = new JButton("BLACK");
    JButton cancel = new JButton("Cancel");

    JPanel labelPanel = new JPanel();
    JPanel whitePanel = new JPanel();
    JPanel blackPanel = new JPanel();
    JPanel cancelPanel = new JPanel();

    Box boxH = Box.createHorizontalBox();
    Box boxV = Box.createVerticalBox();

    public AIPlayWhatFrame(){
        init();
    }

    void init(){
        labelPanel.add(label);
        whitePanel.add(white);
        blackPanel.add(black);
        cancelPanel.add(cancel);
        boxH.add(whitePanel);
        boxH.add(blackPanel);
        boxH.add(cancelPanel);
        boxV.add(labelPanel);
        boxV.add(boxH);
        add(boxV);

        white.addActionListener((e)->{
            Game.initialize();
            Game1.stupidAI.setIam(Game.black);
            Game1.playWithAI = true;
            setVisible(false);
            Game1.display();

            setVisible(false);
            System.out.println("* You play WHITE! *\n");
        });

        black.addActionListener((e)->{
            Game.initialize();
            Game1.stupidAI.setIam(Game.white);
            Game1.playWithAI = true;
            setVisible(false);
            Game1.display();

            setVisible(false);
            System.out.println("* You play BLACK! *\n");
        });

        cancel.addActionListener((e)->{
            setVisible(false);
            Mode.THEME.action();
            Mode.aicho.setVisible(false);
            System.out.println("* Canceled! *\n");
        });

        setSize(300,150);
        setTitle("Player Choice");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
