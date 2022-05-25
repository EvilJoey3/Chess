package display.panels;

import advanced.Buffer;
import advanced.timers.TurnTimer;
import display.Game1;
import game_issue.Game;
import game_issue.Mode;
import advanced.StupidAI;
import advanced.UserPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SaveFrame extends JFrame {
    JLabel declare = new JLabel();
    JLabel indicate = new JLabel();
    JLabel mainLabel = new JLabel("Restart or Quit?");
    JLabel wLabel = new JLabel("Player name of White:");
    JLabel bLabel = new JLabel("Player name of Black:");

    JTextArea wArea = new JTextArea(1, 20);
    JTextArea bArea = new JTextArea(1, 20);

    JButton restart = new JButton("Restart");
    JButton quit = new JButton("Quit");

    JPanel declarePanel = new JPanel();
    JPanel indicatePanel = new JPanel();
    JPanel mainLabelPanel = new JPanel();
    JPanel wLabelPanel = new JPanel();
    JPanel bLabelPanel = new JPanel();
    JPanel wAreaPanel = new JPanel();
    JPanel bAreaPanel = new JPanel();
    JPanel restartPanel = new JPanel();
    JPanel quitPanel = new JPanel();

    Box boxH = Box.createHorizontalBox();
    Box boxV = Box.createVerticalBox();

    public SaveFrame() {
        Font fon = new Font("", Font.BOLD, 16);
        indicate.setFont(fon);
        mainLabel.setFont(fon);
        indicate.setFont(fon);
        wLabel.setFont(fon);
        bLabel.setFont(fon);

        init();
        SwingUtilities.updateComponentTreeUI(getContentPane());
    }

    void init(){

        String s0 = null;
        if(Game.winner == null) s0 = "IT'S A DRAW!";
        else if (Game.winner.isWhite()) s0 = "WHITE WINS!";
        else s0 = "BLACK WINS!";
        declare.setText(s0);
        indicate.setText(feedback());

        declarePanel.add(declare);
        indicatePanel.add(indicate);
        mainLabelPanel.add(mainLabel);
        restartPanel.add(restart);
        quitPanel.add(quit);

        boxH.add(restartPanel);
        boxH.add(quitPanel);

        boxV.add(declarePanel);
        if(!Game1.playWithAI){
            wLabelPanel.add(wLabel);
            bLabelPanel.add(bLabel);
            wAreaPanel.add(wArea);
            bAreaPanel.add(bArea);

            boxV.add(wLabelPanel);
            boxV.add(wAreaPanel);
            boxV.add(bLabelPanel);
            boxV.add(bAreaPanel);
        }

        boxV.add(indicatePanel);
        boxV.add(mainLabelPanel);
        boxV.add(boxH);
        add(boxV);

        restart.addActionListener((e)->{

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Game1.pl[i][j].setVisible(true);
                }
            }

            scoring();
            setVisible(false);
            if(!Game1.playWithAI){
                Game.initialize();
                Game1.turnTimer = new TurnTimer();
                Game1.turnTimer.start();
            }else {
                Game1.stupidAI = new StupidAI();
                Game1.GmenuBar.removeAll();

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Game1.frame.remove(Game1.pl[i][j]);
                    }
                }
                Game1.frame.remove(Game1.regState);
                Game1.frame.remove(Game1.roundLabel);
                Game1.frame.remove(Game1.panel);
                new AIPlayWhatFrame();
            }
            SwingUtilities.updateComponentTreeUI(Game1.frame.getContentPane());
            System.out.println("* Restart! *\n");
        });

        quit.addActionListener((e)->{
            scoring();
            setVisible(false);
            Game1.GmenuBar.removeAll();
            Game1.Gmenu.remove(Game1.regret);
            Game1.Gmenu.remove(Game1.giveUp);
            Game1.Gmenu.remove(quit);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Game1.frame.remove(Game1.pl[i][j]);
                }
            }
            Game1.frame.remove(Game1.turnTimeLabel);
            Game1.frame.remove(Game1.regState);
            Game1.frame.remove(Game1.roundLabel);
            Game1.frame.remove(Game1.panel);
            Mode.THEME.action();
            System.out.println("* Quit the game! *\n");
        });

        setAlwaysOnTop(true);
        if(Game1.playWithAI){ setSize(300, 150); }
        else setSize(300, 450);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Saving Part");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    String feedback(){
        String str = Buffer.getSavePath() + File.separator+"Save_";
        File fi = null;
        int cou = -1;
        while (fi == null || fi.exists()){
            cou++;
            fi = new File(str+cou+".txt");
        }
        cou--;
        return "The game is saved in Save_"+cou+".txt";
    }

    void scoring(){
        String wt = wArea.getText(), bt = bArea.getText();
        if(!wt.equals("") && !bt.equals("") && !Game1.playWithAI && !wt.equals(bt)){
            int wwc, bwc;
            if(Game.winner != null){
                wwc = Game.winner.isWhite() ? 2 : 0;
                bwc = wwc == 0 ? 2 : 0;
            }else {
                wwc = 1;
                bwc = 1;
            }
            UserPlayer.updateRank(wt, wwc);
            UserPlayer.updateRank(bt, bwc);
        }
    }
}
