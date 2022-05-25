package advanced.timers;

import advanced.*;
import advanced.Timer;
import display.Game1;
import game_issue.*;

import javax.swing.*;

public class TurnTimer extends Timer {

    //Constructors
    public TurnTimer(int period) {
        super(period);
    }

    public TurnTimer(){
        super(90);
    }

    @Override
    protected void periodEvent() {
        Player player = Game.id%2 == 0 ? Game.white : Game.black;
        StupidAI stupidAI = new StupidAI();
        stupidAI.setIam(player);
        stupidAI.move();
        SwingUtilities.updateComponentTreeUI(Game1.frame);
        Game1.roundLabel.setText(" "+Game1.during+" Round "+(Game.id/2+1));
        Game1.panel.repaint();
    }

    @Override
    protected void secEvent() {
        Game1.turnTimeLabel.setText("Time: "+time);
        SwingUtilities.updateComponentTreeUI(Game1.frame);
        Game1.panel.repaint();
    }
}
