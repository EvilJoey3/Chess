package game_issue.piece_type;

import display.Game1;
import game_issue.*;

public class Rook extends Piece {
    public Rook(String type, int x, int y) {
        super(type, x, y);
    }

    public void getAvail(){
        int x = x(), y = y();
        for (int i = x; i < 8; i++) {
            Coordinate c = Game.plate[i][y];
            if(i == x){ continue; }
            else if(c.getPiece() == null){
                c.setAvailable(true);
            }else if(c.getPiece().isWhite() == isWhite()){
                break;
            }else {
                c.setAvailable(true);
                break;
            }
        }
        for (int i = x; i > -1; i--) {
            Coordinate c = Game.plate[i][y];
            if(i == x){ continue; }
            if(c.getPiece() == null){
                c.setAvailable(true);
            }else if(c.getPiece().isWhite() == isWhite()){
                break;
            }else {
                c.setAvailable(true);
                break;
            }
        }
        for (int i = y; i < 8; i++) {
            Coordinate c = Game.plate[x][i];
            if(i == y){ continue; }
            else if(c.getPiece() == null){
                c.setAvailable(true);
            }else if(c.getPiece().isWhite() == isWhite()){
                break;
            }else {
                c.setAvailable(true);
                break;
            }
        }
        for (int i = y; i > -1; i--) {
            Coordinate c = Game.plate[x][i];
            if(i == y){ continue; }
            if(c.getPiece() == null){
                c.setAvailable(true);
            }else if(c.getPiece().isWhite() == isWhite()){
                break;
            }else {
                c.setAvailable(true);
                break;
            }
        }

        super.getAvail();
    }

    public void setThreat(){
        int x = x(), y = y();
        for (int i = x; i < 8; i++) {
            Coordinate c = Game.plate[i][y];
            if(i == x){ continue; }
            else if(c.getPiece() == null){
                threat(c);
            }else{
                threat(c);
                break;
            }
        }
        for (int i = x; i > -1; i--) {
            Coordinate c = Game.plate[i][y];
            if(i == x){ continue; }
            else if(c.getPiece() == null){
                threat(c);
            }else{
                threat(c);
                break;
            }
        }
        for (int i = y; i < 8; i++) {
            Coordinate c = Game.plate[x][i];
            if(i == y){ continue; }
            else if(c.getPiece() == null){
                threat(c);
            }else{
                threat(c);
                break;
            }
        }
        for (int i = y; i > -1; i--) {
            Coordinate c = Game.plate[x][i];
            if(i == y){ continue; }
            else if(c.getPiece() == null){
                threat(c);
            }else{
                threat(c);
                break;
            }
        }
    }

    public void to(int x, int y){
        if(x() == 0) getOwner().setCaL(false);
        else if(x() == 7) getOwner().setCaR(false);
        if((x == 0 || x == 7) && (y == 0 || y == 7)){
            Game.white.getKing().getAvail();
            Game.black.getKing().getAvail();
            Game1.removeAvail();
        }
        super.to(x, y);
    }
}
