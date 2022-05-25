package game_issue;

import advanced.Buffer;
import display.*;
import game_issue.piece_type.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    public static Player winner = null;


    private static boolean firstLaunch = true;
    public static Coordinate[][] plate = new Coordinate[8][8];
    public static Player white = new Player(true){
        @Override
        public String toString() {
            return "WHITE";
        }
    };
    public static Player black =  black = new Player(false){
        @Override
        public String toString() {
            return "BLACK";
        }
    };

    public static ArrayList<Piece> allPieces = new ArrayList<>();
    public static HashMap map = new HashMap();
    public static ArrayList<Buffer> buffers = new ArrayList<>();

    public static int id = 0;
    protected static int tempID = 0;

    public static Coordinate[] previous = {null, null};

    //Initialize the game
    public static void initialize(){

        if(!firstLaunch){
            winner = null;
            id = 0;
            map = new HashMap();
            allPieces = new ArrayList<>();
            buffers = new ArrayList<>();
            previous[0] = null;
            previous[1] = null;
        }

        white = new Player(true){
            @Override
            public String toString() {
                return "WHITE";
            }
        };
        black = new Player(false){
            @Override
            public String toString() {
                return "BLACK";
            }
        };

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                plate[i][j] = new Coordinate(i, j);
            }
        }

        Piece[] pawnW = new Piece[8], pawnB = new Piece[8];
        Piece rookW0, rookW1, rookB0, rookB1,
                knightW0, knightW1, knightB0, knightB1,
                bishopW0, bishopW1, bishopB0, bishopB1,
                queenW, queenB, KingW, KingB;

        for (int i = 0; i < 8; i++) {
            pawnW[i] = new Pawn("pawn", i, 1);
            white.addPiece(pawnW[i]);
            pawnB[i] = new Pawn("pawn", i, 6);
            black.addPiece(pawnB[i]);
        }

        rookW0 = new Rook("rook", 0, 0);
        rookW1 = new Rook("rook", 7, 0);
        rookB0 = new Rook("rook", 0, 7);
        rookB1 = new Rook("rook", 7, 7);

        knightW0 = new Knight("knight", 1, 0);
        knightW1 = new Knight("knight", 6, 0);
        knightB0 = new Knight("knight", 1, 7);
        knightB1 = new Knight("knight", 6, 7);

        bishopW0 = new Bishop("bishop", 2, 0);
        bishopW1 = new Bishop("bishop", 5, 0);
        bishopB0 = new Bishop("bishop", 2, 7);
        bishopB1 = new Bishop("bishop", 5, 7);

        queenW = new Queen("queen", 3, 0);
        queenB = new Queen("queen", 3, 7);

        KingW = new King("king", 4, 0);
        KingB = new King("king", 4, 7);

        white.addPiece(rookW0);
        white.addPiece(rookW1);
        white.addPiece(knightW0);
        white.addPiece(knightW1);
        white.addPiece(bishopW0);
        white.addPiece(bishopW1);
        white.addPiece(queenW);
        white.addPiece(KingW);

        black.addPiece(rookB0);
        black.addPiece(rookB1);
        black.addPiece(knightB0);
        black.addPiece(knightB1);
        black.addPiece(bishopB0);
        black.addPiece(bishopB1);
        black.addPiece(queenB);
        black.addPiece(KingB);

        buffers.add(new Buffer());

        if(!firstLaunch){
            Game1.removeAvail();
            Game1.removeThreat();
            Game1.removeSelection();
        }

        firstLaunch = false;
    }

    //Eliminate a piece
    public static void eliminate(int x, int y){
        Coordinate coo = Game.plate[x][y];
        Piece piece = coo.getPiece();
        if(piece != null){
            System.out.printf("%s at (%d, %d) is eliminated", piece, x, y);
            piece.getOwner().pieces.remove(piece);
            allPieces.remove(piece);
            map.remove(piece);
            map.remove(coo);

            System.out.println();
        }
    }

    public static boolean regret(){
        int id = buffers.size()-1;
        if(id >= 2 && ((id%2 == 0) ? white : black).getRegretRemain() > 0){
            toBuffer(id-2);
            buffers.remove(buffers.size()-1);
            buffers.remove(buffers.size()-1);
            id -= 2;
            return true;
        }else return false;
    }

    //return to a round before
    public static void toBuffer(int id){
        Buffer during = buffers.get(id);

        white.setCastling(during.isCastlingW());
        white.setCaL(during.isCaLW());
        white.setCaR(during.isCaRW());

        black.setCastling(during.isCastlingB());
        black.setCaL(during.isCaLB());
        black.setCaR(during.isCaRB());

        allPieces = (ArrayList<Piece>) during.allPieces.clone();
        white.pieces = (ArrayList<Piece>) during.whiteList.clone();
        black.pieces = (ArrayList<Piece>) during.blackList.clone();
        map = (HashMap) during.map.clone();
        Coordinate[] pre = during.previous;
        if(pre[0] != null){
            Game1.changePrevious(pre[0].x(), pre[0].y(), pre[1].x(), pre[1].y());
        }else {
            if(previous[0] != null){
                previous[0].setPrevious(false);
                previous[1].setPrevious(false);
                previous[0] = null;
                previous[1] = null;
            }
        }
        tempID = id;
    }
}