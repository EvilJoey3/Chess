package display.panels;

import advanced.Buffer;
import advanced.Game2;
import display.Game1;
import game_issue.Coordinate;
import game_issue.Game;
import game_issue.Mode;
import game_issue.Piece;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class AIChoiceFrame extends JFrame {
    Box boxH = Box.createHorizontalBox();
    Box boxV = Box.createVerticalBox();

    JPanel pvpPanel = new JPanel();
    JPanel pvaPanel = new JPanel();
    JPanel labelPanel = new JPanel();
    JPanel cancelPanel = new JPanel();
    JPanel impoPanel = new JPanel();

    JButton pvp = new JButton("Player");
    JButton pva = new JButton("StupidAI");
    JButton cancel = new JButton("Cancel");
    JButton impo = new JButton("Import");

    JLabel label = new JLabel("Playing options");

    public AIChoiceFrame(){
        init();
    }

    void init(){
        pvpPanel.add(pvp);
        pvaPanel.add(pva);
        labelPanel.add(label);
        cancelPanel.add(cancel);
        impoPanel.add(impo);

        boxH.add(pvpPanel);
        boxH.add(pvaPanel);
        boxH.add(impoPanel);
        boxH.add(cancelPanel);
        boxV.add(labelPanel);
        boxV.add(boxH);
        add(boxV);

        pvp.addActionListener((e)->{
            Game.initialize();
            Game1.playWithAI = false;
            setVisible(false);
            Game1.display();
            System.out.println("* Play with a player! *\n");
        });

        pva.addActionListener((e)->{
            new AIPlayWhatFrame();
            setVisible(false);
            System.out.println("* Play with StupidAI! *\n");
        });

        impo.addActionListener(e->{
            JFileChooser chooser = new JFileChooser();
//            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showDialog(null, "Confirm");
            File fileChosen = chooser.getSelectedFile();
            if(fileChosen != null && fileChosen.getName().endsWith(".txt")){
                Game.initialize();
                Game.buffers = new ArrayList<>();
                Boolean b = Buffer.reappear(fileChosen);
                Game1.playWithAI = false;
                Game1.display();

                int l = Game.buffers.size();
                if(!b || l == 0){
                    Game1.frame.remove(Game1.panel);
                    Game1.frame.remove(Game1.turnTimeLabel);
                    Game1.frame.remove(Game1.roundLabel);
                    Game1.frame.remove(Game1.regState);
                    Game1.GmenuBar.removeAll();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            Game1.frame.remove(Game1.pl[i][j]);
                        }
                    }
                    /*Game1.GmenuBar.removeAll();
                    Game1.frame.remove(Game1.playB);
                    Game1.frame.remove(Game1.recordB);
                    Game1.frame.remove(Game1.rankB);*/
                    System.out.println("SHIT!\n");
                } else {
                    Game.toBuffer(l-1);
                    Game.id = l-1;
                    Game1.during = (l-1)/2 == 0 ? "White: " : "Black: ";
                    Game1.roundLabel.setText(" "+Game1.during+" Round "+(Game.id/2+1));
                    Game1.panel.repaint();

                    Game.allPieces = new ArrayList<>();
                    Game.black.pieces = new ArrayList<>();
                    Game.white.pieces = new ArrayList<>();
                    for (Object o:
                         Game.map.keySet()) {
                        if(o instanceof Piece) {
                            Game.allPieces.add((Piece) o);
                            if(((Piece) o).isWhite()){
                                Game.white.pieces.add((Piece) o);
                            }else {
                                Game.black.pieces.add((Piece) o);
                            }
                            System.out.println(o+ Game.map.get(o).toString());
                        }
                    }


                    Game.buffers = new ArrayList<>();
                    Game.buffers.add(new Buffer());

                    Game1.removeThreat();
                    Game1.setAllThreat();
                    Game2.test();

                    setVisible(false);
                    System.out.println("* Continue a round! *\n");
                }
            }else {
                Game1.frame.remove(Game1.panel);
                new ErrorFrame(String.format("FORMAT 104: except txt"));
            }
        });

        cancel.addActionListener((e)->{
            Mode.THEME.action();
            setVisible(false);
            System.out.println("* Canceled! *\n");
        });

        setAlwaysOnTop(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(350, 150);
        setTitle("Your Choice");
        setVisible(true);
    }
}
