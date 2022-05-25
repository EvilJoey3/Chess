package advanced.timers;

import advanced.Game2;
import display.Game1;
import game_issue.Mode;

import java.awt.*;

public class RefreshTimer extends Thread{
    private boolean breakpoint;
    private static int preWidth = 600;
    private static int preHeight = 825;
    private static int preScale = 71;

    public RefreshTimer(){
        breakpoint = false;
    }
    @Override
    public void run() {
        while (true){
            try {
                int width = Game1.frame.getWidth();
                if(width != preWidth){
                    int height = Game1.frame.getHeight();
                    double standard = (double) 600/(double) 825;
                    if(width > preWidth-20 && width < preWidth+20){
                        height = (int) ((double) width/standard);
                    }
                    if(width < 200) Game1.frame.setSize(200, 275);
                    else Game1.frame.setSize(width, height);
                    preWidth = width;
                }

                sleep(3);
                if(breakpoint) break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    int ra(int t){
        return Game2.ra(t);
    }

    public void setBreakpoint(boolean breakpoint) {
        this.breakpoint = breakpoint;
    }
}
