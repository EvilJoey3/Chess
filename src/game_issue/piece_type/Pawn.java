package game_issue.piece_type;

import display.Game1;
import display.panels.PromoteFrame;
import game_issue.Coordinate;
import game_issue.*;

public class Pawn extends Piece{

    public boolean first2Step;

    public Pawn(String type, int x, int y) {
        super(type, x, y);
        first2Step = false;
    }
    public void getAvail(){
        if(y() != 0 && y() != 7){
            int factor = isWhite() ? 1 : -1;
            int line = isWhite() ? 1 : 6;
            boolean forward1 = true, forward2 = true;
            if(Game.plate[x()][y()+1*factor].getPiece() != null) forward1 = false;
            if(y() != line || !forward1 ||
                    Game.plate[x()][y()+2*factor].getPiece() != null) forward2 = false;
            if(forward1) Game.plate[x()][y()+1*factor].setAvailable(true);
            if(forward2) Game.plate[x()][y()+2*factor].setAvailable(true);

            if(judge(-1, factor)) Game.plate[x()-1][y()+factor].setAvailable(true);
            if(judge(1, factor)) Game.plate[x()+1][y()+factor].setAvailable(true);

            eatenPassant(x(), y());
        }

        super.getAvail();
    }

    public void setThreat(){
        if(y() != 0 && y() != 7){
            int specialY = isWhite() ? y()+1 : y()-1;
            if(x() != 0) threat(Game.plate[x()-1][specialY]);
            if(x() != 7) threat(Game.plate[x()+1][specialY]);
        }
    }

    public void eatenPassant(int x0, int y0) {
        int passantY = isWhite() ? 4 : 3;
        int specialY = isWhite() ? y0+1 : y0-1;

        if(y() == passantY){
            Coordinate coo = null;
            Piece piece = null;
            if(x() != 0){
                coo = Game.plate[x0-1][y0];
                piece = coo.getPiece();
                if(piece != null && piece instanceof Pawn &&
                        isWhite() != piece.isWhite() && ((Pawn) piece).first2Step && coo.isPrevious()){
                    Game.plate[x0-1][specialY].setAvailable(true);
                }
            }

            if(x() != 7){
                coo = Game.plate[x0+1][y0];
                piece = coo.getPiece();
                if(piece != null && piece instanceof Pawn &&
                        isWhite() != piece.isWhite() && ((Pawn) piece).first2Step && coo.isPrevious()){
                    Game.plate[x0+1][specialY].setAvailable(true);
                }
            }
        }
    }

    public  boolean judge(int dx,int dy){
        if(y()>=1&&y()<=6){
            if((x() == 0 && dx == -1) || (x() == 7 && dx == 1)) return false;
            Piece during = Game.plate[x()][y()].getPiece(), target = Game.plate[x()+dx][y()+dy].getPiece();
            if(during == null || target == null) return false;
//            boolean judgeColor = Game.id/2 == 0;
            return during.isWhite() != target.isWhite() /*&& target instanceof Pawn*/;
        }else return false;
    }

    @Override
    public void to(int x, int y) {
        int assY = isWhite() ? y-1 : y+1;
        int specialDy = isWhite()? 1 : -1;

        Piece assPiece = null;
        if(assY < 8 && assY > -1) assPiece = Game.plate[x][assY].getPiece();

        if(Math.abs(x-x()) == 1 && y-y() == specialDy &&
                assPiece instanceof Pawn && ((Pawn) assPiece).first2Step &&
                assPiece.isWhite() != isWhite()){
            Game.eliminate(x, assY);
            System.out.println(getOwner()+": Eat Passant!");
        }

        if(Math.abs(y-y()) == 2) first2Step = true;
        else first2Step = false;


        super.to(x, y);

        int yPro = isWhite() ? 7 : 0;
        if(y == yPro) promote();
    }

    void promote(){
        if(Game1.playWithAI && isWhite() == Game1.stupidAI.getIam().isWhite()){
            PromoteFrame.transform(this, "queen");
        }else new PromoteFrame(this);
    }
}

