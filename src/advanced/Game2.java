package advanced;

import display.*;
import display.panels.SaveFrame;
import game_issue.*;
import game_issue.piece_type.King;

import javax.sound.sampled.*;
import java.io.File;

public class Game2 extends Game1 {

    //frame ratio
    public static double ratio(){ return ((double) frame.getWidth())/600; }

    public static int ra(double d){ return (int) (d*ratio()); }

    public static int ra(int d){ return (int) (d*ratio()); }

    //play audios
    public static boolean playSound(AudioInputStream ais){
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean playLoop(AudioInputStream ais){
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    //tests
    public static boolean test(){

        if(perpetualTest() || victoryAndStalemateTest() || repetitiveTest()){
            turnTimer.setBreakPoint(true);

            String regW1 = white.getRegretRemain() > 100 ? "many" : String.valueOf(white.getRegretRemain());
            String regB1 = black.getRegretRemain() > 100 ? "many" : String.valueOf(black.getRegretRemain());
            int timmes = reglimit.getText().endsWith("N") ? 3 : 10000;
            black.setRegretRemain(timmes); white.setRegretRemain(timmes);
            regState.setText(" Regret Chances Remain: "
                    +"W: "+regW1+" "+"B: "+regB1);

            String msg = "Nobody";
            if(winner != null) msg = winner.toString();
            System.out.println(msg + " wins!\n");

            Buffer.save();
            new SaveFrame();
            return true;
        }else return false;
    }

    public static boolean victoryAndStalemateTest(){
        Player current = id%2 == 0 ? white : black;
        Player potentialWinner = id%2 == 0 ? black : white;

        boolean serious = current.getKing().considerThreat();

        boolean determined = true;

        for (Piece piece:
                current.pieces) {
            piece.getAvail();

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(plate[i][j].isAvailable()){
                        determined = false;
                        removeAvail();
                        break;
                    }
                }
                if(!determined) break;
            }
            if(!determined) break;
        }

        if(determined && serious){
            winner = potentialWinner;
            return true;
        }else if(determined) return true;
        else return false;
    }

    public static boolean repetitiveTest(){
        if(buffers.isEmpty()) return false;

        boolean judge = false;
        int almostL = buffers.size()-1;
        int cou = 0;
        Buffer current = buffers.get(almostL);

        for (int i = 0; i < almostL; i++) {
            Buffer buff = buffers.get(i);
            if(current.equals(buff) && current.isWhite() == buff.isWhite()) cou++;
            if(cou >= 2){
                judge = true;
                break;
            }
        }


        return judge;
    }

    public static boolean perpetualTest(){
        /*int l = buffers.size();
        if(l < 6) return false;
        Player current = (l-1)%2 == 0 ? white : black;

        boolean serious = current.getKing().considerThreat();
        if(serious) buffers.get(l-1).danger = true;

        for (int i = 0; i < 2; i++) {
            if(!buffers.get(l-3-2*i).danger) return false;
        }
        return true;*/
        return false;
    }
}
