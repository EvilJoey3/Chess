package game_issue.piece_type;

import game_issue.Coordinate;
import game_issue.Game;
import game_issue.Piece;

public class Queen extends Piece {
    public Queen(String type, int x, int y) {
        super(type, x, y);
    }

    public void getAvail() {
        int X = x(), Y = y();
        int i = X, j = Y;
        while(i < 8 && j < 8){
            Coordinate c = Game.plate[i][j];
            if(i == X || j == Y) {
                i++;
                j++;
                continue;
            }else if(c.getPiece() == null){
                c.setAvailable(true);
            }else if(c.getPiece().isWhite() == isWhite()){
                break;
            }else {
                c.setAvailable(true);
                break;
            }
            i++;
            j++;
        }
        i = X; j = Y;
        while(i > -1 && j < 8){
            Coordinate c = Game.plate[i][j];
            if(i == X || j == Y) {
                i--;
                j++;
                continue;
            }else if(c.getPiece() == null){
                c.setAvailable(true);
            }else if(c.getPiece().isWhite() == isWhite()){
                break;
            }else {
                c.setAvailable(true);
                break;
            }
            i--;
            j++;
        }
        i = X; j = Y;
        while(i > -1 && j > -1){
            Coordinate c = Game.plate[i][j];
            if(i == X || j == Y) {
                i--;
                j--;
                continue;
            }else if(c.getPiece() == null){
                c.setAvailable(true);
            }else if(c.getPiece().isWhite() == isWhite()){
                break;
            }else {
                c.setAvailable(true);
                break;
            }
            i--;
            j--;
        }
        i = X; j = Y;
        while(i < 8 && j > -1){
            Coordinate c = Game.plate[i][j];
            if(i == X || j == Y) {
                i++;
                j--;
                continue;
            }else if(c.getPiece() == null){
                c.setAvailable(true);
            }else if(c.getPiece().isWhite() == isWhite()){
                break;
            }else {
                c.setAvailable(true);
                break;
            }
            i++;
            j--;
        }
        int x = x(), y = y();
        for (i = x; i < 8; i++) {
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
        for (i = x; i > -1; i--) {
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
        for (i = y; i < 8; i++) {
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
        for (i = y; i > -1; i--) {
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
        int X = x(), Y = y();
        int i = X, j = Y;
        while(i < 8 && j < 8){
            Coordinate c = Game.plate[i][j];
            if(i == X || j == Y) {
                i++;
                j++;
                continue;
            }else if(c.getPiece() == null){
                threat(c);
            }else {
                threat(c);
                break;
            }
            i++;
            j++;
        }
        i = X; j = Y;
        while(i > -1 && j < 8){
            Coordinate c = Game.plate[i][j];
            if(i == X || j == Y) {
                i--;
                j++;
                continue;
            }else if(c.getPiece() == null){
                threat(c);
            }else {
                threat(c);
                break;
            }
            i--;
            j++;
        }
        i = X; j = Y;
        while(i > -1 && j > -1){
            Coordinate c = Game.plate[i][j];
            if(i == X || j == Y) {
                i--;
                j--;
                continue;
            }else if(c.getPiece() == null){
                threat(c);
            }else {
                threat(c);
                break;
            }
            i--;
            j--;
        }
        i = X; j = Y;
        while(i < 8 && j > -1){
            Coordinate c = Game.plate[i][j];
            if(i == X || j == Y) {
                i++;
                j--;
                continue;
            }else if(c.getPiece() == null){
                threat(c);
            }else {
                threat(c);
                break;
            }
            i++;
            j--;
        }
        int x = x(), y = y();
        for (i = x; i < 8; i++) {
            Coordinate c = Game.plate[i][y];
            if(i == x){ continue; }
            else if(c.getPiece() == null){
                threat(c);
            }else{
                threat(c);
                break;
            }
        }
        for (i = x; i > -1; i--) {
            Coordinate c = Game.plate[i][y];
            if(i == x){ continue; }
            else if(c.getPiece() == null){
                threat(c);
            }else{
                threat(c);
                break;
            }
        }
        for (i = y; i < 8; i++) {
            Coordinate c = Game.plate[x][i];
            if(i == y){ continue; }
            else if(c.getPiece() == null){
                threat(c);
            }else{
                threat(c);
                break;
            }
        }
        for (i = y; i > -1; i--) {
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
}
