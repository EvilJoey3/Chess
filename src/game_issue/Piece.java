package game_issue;

import display.Game1;
import game_issue.piece_type.*;

import java.util.ArrayList;

public class Piece {
    private Player owner;
    private String type;
    public ArrayList<Coordinate> threats = new ArrayList<>();

    public Piece(String type, int x, int y){
        this.type = type;
        Game.map.put(this, Game.plate[x][y]);
        Game.map.put(Game.plate[x][y], this);
        Game.allPieces.add(this);
    }

    //Move to a coordinate or capture piece
    public void to(int x, int y){
        System.out.printf("%s moves to (%d, %d)\n", this, x, y);
        Game.map.remove(Game.map.get(this));
        Game.eliminate(x, y);
        Game.map.put(this, Game.plate[x][y]);
        Game.map.put(Game.plate[x][y], this);

        Game1.removeSelection();
        Game1.removeAvail();

        Game1.removeThreat();
        Game1.setAllThreat();

        System.out.println();
    }

    //Get the image name of the piece
    public String getPicName(){
        return (String) owner.getMapPP().get(type);
    }

    //To get information of a piece easier
    public int x(){
        return ((Coordinate) Game.map.get(this)).getXy()/10;
    }

    public int y(){
        return ((Coordinate) Game.map.get(this)).getXy()%10;
    }

    public int xy(){
        return ((Coordinate) Game.map.get(this)).getXy();
    }

    public boolean isWhite(){ return owner.isWhite(); }

    //Get the threat that should be considered
    public boolean considerThreat(){ return ((Coordinate) Game.map.get(this)).considerThreat(); }

    public void getAvail(){
        Player testCurrentPlayer = Game.id%2 == 0 ? Game.white : Game.black;
        Player testCurrentEnemy = Game.id%2 == 0 ? Game.black : Game.white;
        King testCurrentKing = testCurrentPlayer.getKing();

        Piece atk = null;

        if(testCurrentKing.considerThreat()){
            Coordinate kingCo = (Coordinate) Game.map.get(testCurrentKing);

            for (Piece piece:
                    testCurrentEnemy.pieces) {
                if(piece.threats.contains(kingCo)){
                    atk = piece;
                    break;
                }
            }
        }

        if(atk != null){

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Coordinate co = Game.plate[i][j];
                    if(co.isAvailable()){
                        King king = owner.getKing();
                        Piece target = co.getPiece();
                        if(target == null){

                            if(this instanceof King){

                                if(!(atk instanceof Knight || atk instanceof Pawn)){
                                    int dx1 = co.x() - x(), dy1 = co.y() - y();
                                    int dx2 = co.x() - atk.x(), dy2 = co.y() - atk.y();
                                    if (dx1 * dy2 == dx2 * dy1) {
                                        co.setAvailable(false);
                                    }
                                }

                            }else {

                                //piece type cases

                                if(atk instanceof Knight){
                                    co.setAvailable(false);
                                }else if(atk instanceof Pawn){
                                    if(this instanceof Pawn && ((Pawn) atk).first2Step){
                                        int assY = isWhite() ? co.y()-1 : co.y()+1;
                                        Coordinate assCo = Game.plate[co.x()][assY];
                                        Piece assPiece = assCo.getPiece();

                                        if(assPiece == null || !assPiece.equals(atk)) {
                                            co.setAvailable(false);
                                        }

                                    }else {
                                        co.setAvailable(false);
                                    }
                                }else {
                                    if(atk.threats.contains(co)){
                                        int dx1 = co.x()-king.x(), dy1 = co.y()- king.y();
                                        int dx2 = co.x()-atk.x(), dy2 = co.y()-atk.y();
                                        if(!(dx1*dy2 == dx2*dy1 && (dx1*dx2 < 0 || dy1*dy2 < 0))){
                                            co.setAvailable(false);
                                        }
                                    }else {
                                        co.setAvailable(false);
                                    }
                                }
                            }
                        } else if(!target.equals(atk)){
                            if(this instanceof King){

                                if(!(atk instanceof Knight || atk instanceof Pawn)){
                                    int dx1 = co.x() - x(), dy1 = co.y() - y();
                                    int dx2 = co.x() - atk.x(), dy2 = co.y() - atk.y();
                                    if (dx1 * dy2 == dx2 * dy1) {
                                        co.setAvailable(false);
                                    }
                                }

                            } else {
                                co.setAvailable(false);
                            }
                        }
                    }
                }
            }
        }else {
            for (Piece ePiece:
                    testCurrentEnemy.pieces) {
                if(!(ePiece instanceof Pawn) &&
                        !(ePiece instanceof Knight) &&
                        !(ePiece instanceof King)){
                    King king = owner.getKing();
                    int dx1 = x()-ePiece.x(), dy1 = y()-ePiece.y();
                    int dx2 = x()-king.x(), dy2 = y()-king.y();
                    if(dx1*dy2 == dx2*dy1 && (dx1*dx2 < 0 || dy1*dy2 < 0)){

                        if((ePiece instanceof Rook && dx1*dy2 != 0) ||
                                (ePiece instanceof Bishop && dx1*dy2 == 0)){
                            continue;
                        }

                        boolean b = false;
                        for (Piece rescue:
                                Game.allPieces) {
                            if(rescue.equals(this)) continue;
                            int dx3 = rescue.x()-ePiece.x(), dy3 = rescue.y()-ePiece.y();
                            int dx4 = rescue.x()-king.x(), dy4 = rescue.y()-king.y();
                            if(dx3*dy4 == dx4*dy3 && (dx3*dx4 < 0 || dy3*dy4 < 0)){
                                b = true;
                                break;
                            }
                        }

                        if(!b){
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    Coordinate co = Game.plate[i][j];
                                    Piece pii = co.getPiece();
                                    if(co.isAvailable() && (pii == null ||
                                            !pii.equals(ePiece))){
                                        co.setAvailable(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void setThreat(){}

    public void threat(Coordinate coordinate){
        if(isWhite()) coordinate.setThreatW(true);
        else coordinate.setThreatB(true);
        threats.add(coordinate);
    }

    //GS
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getType() { return type; }

    public void setType(String type) {
        this.type = type;
    }

    //to string

    @Override
    public String toString() {
        if(this == null) return "Empty";
        else return String.format("[%s's %s]", owner, type);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Piece){
            return xy() == ((Piece) obj).xy();
        }else return false;
    }
}
