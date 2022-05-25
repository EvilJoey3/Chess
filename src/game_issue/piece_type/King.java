package game_issue.piece_type;

import game_issue.Coordinate;
import game_issue.Game;
import game_issue.Piece;
import game_issue.Player;

public class King extends Piece {
    private final int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    private final int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};

    public King(String type, int x, int y) {
        super(type, x, y);
    }

    public void getAvail(){
        for (int i = 0; i < 8; i++) {
            int x = x()+dx[i], y = y()+dy[i];
            if(x < 0 || x > 7 || y < 0 || y > 7){ continue; }
            Coordinate c = Game.plate[x][y];
            boolean potential = isWhite() ? c.isThreatB() : c.isThreatW();
            if(!potential){
                if(c.getPiece() == null) c.setAvailable(true);
                else if (c.getPiece().isWhite() == isWhite()){ continue; }
                else {c.setAvailable(true);}
            }
        }
        King aK = getAnotherKing();
        int x1 = aK.x(), y1 = aK.y();
        if(Math.abs(x()-x1)+Math.abs(y()-y1) < 4.5){
            for (int i = 0; i < 8; i++) {
                int x = x1+dx[i], y = y1+dy[i];
                if(x < 0 || x > 7 || y < 0 || y > 7){ continue; }
                Game.plate[x][y].setAvailable(false);
            }
        }
        if(getOwner().isCastling() && x() == 4){
            boolean canCastleL = true, canCastleR = true;
            Piece rookL = Game.plate[0][y()].getPiece(), rookR = Game.plate[7][y()].getPiece();
            if(rookL == null || !rookL.getType().equals("rook") || rookL.isWhite() != isWhite()) getOwner().setCaL(false);
            if(rookR == null || !rookR.getType().equals("rook") || rookR.isWhite() != isWhite()) getOwner().setCaR(false);
            for (int i = 1; i <= 2; i++) {
                if(true){
                    Coordinate cL = Game.plate[x()-i][y()];
                    Coordinate cR = Game.plate[x()+i][y()];
                    if(isWhite()){
                        if(cL.isThreatB() || cL.getPiece() != null || !getOwner().isCaL()) canCastleL = false;
                        if(cR.isThreatB() || cR.getPiece() != null || !getOwner().isCaR()) canCastleR = false;
                    }else {
                        if(cL.isThreatW() || cL.getPiece() != null || !getOwner().isCaL()) canCastleL = false;
                        if(cR.isThreatW() || cR.getPiece() != null || !getOwner().isCaR()) canCastleR = false;
                    }
                }else {
                    canCastleL = false;
                    canCastleR = false;
                }
            }
            if(canCastleL && !considerThreat()) Game.plate[x()-2][y()].setAvailable(true);
            if(canCastleR && !considerThreat()) Game.plate[x()+2][y()].setAvailable(true);
        }

        super.getAvail();
    }

    public void setThreat(){
        for (int i = 0; i < 8; i++) {
            int x = x()+dx[i], y = y()+dy[i];
            if(x < 0 || x > 7 || y < 0 || y > 7){ continue; }
            Coordinate c = Game.plate[x][y];
            boolean potential = isWhite() ? c.isThreatB() : c.isThreatW();
            if(!potential) threat(c);
        }
        King aK = getAnotherKing();
        int x1 = aK.x(), y1 = aK.y();
        if(Math.abs(x()-x1)+Math.abs(y()-y1) < 4.5){
            for (int i = 0; i < 8; i++) {
                int x = x1+dx[i], y = y1+dy[i];
                if(x < 0 || x > 7 || y < 0 || y > 7){ continue; }
                Coordinate c = Game.plate[x][y];
                if(threats.contains(c)){
                    threats.remove(c);
                    if(isWhite()) c.setThreatW(false);
                    else c.setThreatB(false);
                }
            }
        }
    }

    public void to(int x, int y){
        if(y == y()){
            if(x-x() == -2 && Game.plate[0][y].getPiece() != null){
                Game.plate[0][y].to(3, y);
                System.out.printf("%s: castling!\n", getOwner());
            }else if(x-x() == 2 && Game.plate[7][y].getPiece() != null){
                Game.plate[7][y].to(5, y);
                System.out.printf("%s: castling!\n", getOwner());
            }
        }

        getOwner().setCastling(false);
        super.to(x, y);
    }

    public King getAnotherKing(){
        Player enemy = isWhite() ? Game.black : Game.white;
        return enemy.getKing();
    }

    //GS
}
