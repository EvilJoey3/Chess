package game_issue.piece_type;

import game_issue.*;

public class Knight extends Piece {
    private static final int[] dx = {1, 2, 2, 1, -1, -2, -2, -1};
    private static final int[] dy = {2, 1, -1, -2, -2, -1, 1, 2};

    public Knight(String type, int x, int y) {
        super(type, x, y);
    }

    public void getAvail(){
        for (int i = 0; i < 8; i++) {
            int x = x()+dx[i], y = y()+dy[i];
            if(x < 0 || x > 7 || y < 0 || y > 7){ continue; }
            Coordinate c = Game.plate[x][y];
            if(c.getPiece() == null) c.setAvailable(true);
            else if (c.getPiece().isWhite() == isWhite()){ continue; }
            else {c.setAvailable(true);}
        }

        super.getAvail();
    }

    public void setThreat(){
        for (int i = 0; i < 8; i++) {
            int x = x()+dx[i], y = y()+dy[i];
            if(x < 0 || x > 7 || y < 0 || y > 7){ continue; }
            Coordinate c = Game.plate[x][y];
            threat(c);
        }
    }
}
