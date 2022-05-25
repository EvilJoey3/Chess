package display;

import advanced.Game2;
import advanced.timers.RefreshTimer;
import game_issue.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Panel extends JPanel {
    private static String bgName = "bgY";
    private static String indicName = "cross";
//    private static int preWidth = 600;
//    private static int preHeight = 825;
    private static int preScale = 71;

    public Panel(){
        super();
    }

    public void paintPieces(Graphics g, JPanel panel){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Coordinate now = Game.plate[i][j];
                    now.paint(g, panel);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        double ratio = Game2.ratio();
        g.drawImage(Game1.getImage(bgName), -15, -5, (int) (609*ratio), (int) (905*ratio), this);
        g.drawImage(Game1.getImage("plate"+Coordinate.getIdenticalSuffix()), 0, 0,
                (int) (ratio*585), (int) (ratio*585),this);
        paintPieces(g, this);

//        int width = Game1.frame.getWidth();
//        int height = Game1.frame.getHeight();
//        double rat = (double) width/(double) height;
//        double standard = (double) 600/(double) 825;
//        if(!(rat <  standard + 0.02 &&  rat > standard - 0.02)){
//            if(height != preHeight){
//                width = (int) ((double) height*standard);
//            }else if(width != preWidth){
//                height = (int) ((double) width/standard);
//            }
//            Game1.frame.setSize(width, height);
//            preWidth = width;
//            preHeight = height;
//        }
//        if(width < 200) Game1.frame.setSize(200, 275);
//
        if(Game1.getMode() == Mode.GAME){

            int scale = ra(71);
            if(preScale != scale){
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        int[] p = Game1.p(i, j);
                        Game1.pl[i][j].setBounds(p[0], p[1], scale, scale);
                    }
                }
                preScale = scale;
                Game1.roundLabel.setBounds(ra(15), ra(600), ra(270), ra(50));
                Game1.regState.setBounds(ra(15), ra(675), ra(450), ra(50));
                Game1.turnTimeLabel.setBounds(ra(300) ,ra(600) ,ra(150) ,ra(50));
                Game1.roundLabel.setFont(new Font("", Font.ITALIC, ra(24)));
                Game1.regState.setFont(new Font("", Font.ITALIC, ra(24)));
                Game1.turnTimeLabel.setFont(new Font("", Font.ITALIC, ra(24)));
            }

            Point point = MouseInfo.getPointerInfo().getLocation();
            int[] pp = Game1.reverseP(point.x, point.y);

            if(pp[0] < 8 && pp[0] > -1 && pp[1] < 8 && pp[1] > -1){
                Game1.placeOn(pp[0], pp[1], "blueBox", g, this);
                repaint();
            }
        }

    }

    protected static int ra(int i){ return Game2.ra(i); }

    //GS
    public static String getBgName() {
        return bgName;
    }

    public static void setBgName(String bgName) {
        Panel.bgName = bgName;
    }

    public static String getIndicName() {
        return indicName;
    }

    public static void setIndicName(String indicName) {
        Panel.indicName = indicName;
    }
}
