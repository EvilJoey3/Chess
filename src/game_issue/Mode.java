package game_issue;

import advanced.Game2;
import advanced.timers.RefreshTimer;
import display.*;
import display.panels.*;
import advanced.StupidAI;

import javax.swing.*;
import java.io.File;

public enum Mode{
    THEME{
        static boolean firstLaunch = true;
        @Override
        public void action() {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Game.plate[i][j] = new Coordinate(i, j);
                }
            }
//            Game2.playSound("yijie");
            super.action();
            SwingUtilities.updateComponentTreeUI(Game1.frame.getContentPane());
            Game1.display();
            if(firstLaunch){
                Game1.onWindows = File.separator.equals("\\");
                Game1.frame.decode();
                Game2.playLoop(MajorFrame.bgm);
                firstLaunch = false;
            }
        }
    },
    RANK{
        @Override
        public void action() {
            super.action();
            rankFrame = new RankFrame();
//            SwingUtilities.updateComponentTreeUI(Game1.frame.getContentPane());
//            Game1.display();
        }
    },
    RECORD{
        @Override
        public void action() {
            super.action();
            SwingUtilities.updateComponentTreeUI(Game1.frame.getContentPane());
            Game1.display();
        }
    },
    GAME{
        @Override
        public void action(){
            super.action();
            Game1.stupidAI = new StupidAI();
            SwingUtilities.updateComponentTreeUI(Game1.frame.getContentPane());
            aicho = new AIChoiceFrame();
        }
    };

    public static RankFrame rankFrame;
    public static AIChoiceFrame aicho;

    public void action(){
        Game1.setMode(this);
        Game1.refreshTimer.setBreakpoint(true);
        Game1.refreshTimer = new RefreshTimer();
        Game1.refreshTimer.start();
        boolean b = true;
    }
}

