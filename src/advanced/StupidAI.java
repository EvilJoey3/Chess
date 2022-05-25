package advanced;

import display.Game1;
import game_issue.*;

import java.util.ArrayList;
import java.util.Random;

public class StupidAI {
    private Player iam = Game.white;
    public boolean move(){
        int cou = 0;

        int pieL = iam.pieces.size();
        Random ran = new Random();

        Piece selec = null;
        ArrayList<Coordinate> coos = new ArrayList<>();
        int cooL = 0;

        if(pieL != 0){
            while (true){
                int ind = ran.nextInt(pieL);
                selec = iam.pieces.get(ind);
                selec.getAvail();

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Coordinate detec = Game.plate[i][j];
                        if(detec.isAvailable()) coos.add(detec);
                    }
                }

                cooL = coos.size();
                if(cooL != 0) break;
                else Game1.removeAvail();

                if(cou >= 3000){
                    System.out.println("StupidAI fails to move\n");
                    return false;
                }
                cou++;
            }
        }else {
            System.out.println("StupidAI fails to move\n");
            return false;
        }

        if(cooL != 0){
            int ind1 = ran.nextInt(cooL);
            Coordinate c = coos.get(ind1);
            int x0 = selec.x(), y0 = selec.y(),
                    x = c.x(), y = c.y();

            System.out.println("StupidAI's step: ");
            selec.to(x, y);
            Game1.changePrevious(x0, y0, x, y);
            Game1.switchDuring();
            Game1.regret.setText(Game1.during+"Regret");
            Game1.giveUp.setText(Game1.during+"Give up & save");
            Game.buffers.add(new Buffer());
            Game.id++;

            System.out.println();

//            Game1.removeAvail();
//            Game1.removeThreat();
//            Game1.setAllThreat();

            return true;
        }else {
            Game1.removeAvail();
            System.out.println("StupidAI fails to move\n");
            return false;
        }
    }

    //GS

    public Player getIam() {
        return iam;
    }

    public void setIam(Player iam) {
        this.iam = iam;
    }
}
