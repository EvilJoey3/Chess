package game_issue;

import game_issue.piece_type.King;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private final boolean isWhite;
    public ArrayList<Piece> pieces;
    private final HashMap mapPP;
    private int regretRemain;
    private boolean castling;
    private boolean caL;
    private boolean caR;

    //Construct a player & declare white or black
    public Player(boolean isWhite){
        caL = true;
        caR = true;
        castling = true;

        regretRemain = 3;
        this.isWhite = isWhite;
        int yOriginal = isWhite ? 0 : 7;
//        Piece king  = new King("king", 4, yOriginal);
        this.pieces = new ArrayList<>();
//        this.addPiece(king);
        mapPP = new HashMap();

        if(isWhite){
            mapPP.put("pawn", "pawnW");
            mapPP.put("rook", "rookW");
            mapPP.put("knight", "knightW");
            mapPP.put("bishop", "bishopW");
            mapPP.put("queen", "queenW");
            mapPP.put("king", "kingW");
        }else {
            mapPP.put("pawn", "pawnB");
            mapPP.put("rook", "rookB");
            mapPP.put("knight", "knightB");
            mapPP.put("bishop", "bishopB");
            mapPP.put("queen", "queenB");
            mapPP.put("king", "kingB");
        }
    }

    //Match piece & player
    public void addPiece(Piece p){
        p.setOwner(this);
        pieces.add(p);
    }

    //Get the king
    public King getKing(){
        Piece k = null;
        for (Piece p : pieces) {
            if(p.getType().equals("king")){
                k = p;
                break;
            }
        }
        return (King) k;
    }

    //GS
    public boolean isWhite() {
        return isWhite;
    }

    public HashMap getMapPP() {
        return mapPP;
    }

    public int getRegretRemain() {
        return regretRemain;
    }

    public void setRegretRemain(int regretRemain) {
        this.regretRemain = regretRemain;
    }

    public boolean isCastling() {
        return castling;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }

    public boolean isCaL() {
        return caL;
    }

    public void setCaL(boolean caL) {
        this.caL = caL;
    }

    public boolean isCaR() {
        return caR;
    }

    public void setCaR(boolean caR) {
        this.caR = caR;
    }
}
