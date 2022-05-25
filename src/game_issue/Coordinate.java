package game_issue;

import display.*;
import display.Panel;

import javax.swing.*;
import java.awt.*;

public class Coordinate {
    private int xy;
    private boolean available;
    private boolean selected;
    private boolean previous;
    private boolean threatW;
    private boolean threatB;
    private static String identicalSuffix = "1";

    public Coordinate(int x, int y){
        xy = 10*x+y;
        available = false;
        selected = false;
        previous = false;
        threatW = false;
        threatB = false;
    }

    //Move to a coordinate or capture piece
    public void to(int x, int y){
        ((Piece) Game.map.get(this)).to(x, y);
    }

    //To get information of a coordinate easier
    public int x(){
        return xy/10;
    }

    public int y(){
        return xy%10;
    }

    //Paint the pieces
    public void paint(Graphics g, JPanel panel){
        Piece piece = (Piece) Game.map.get(this);
        if(piece != null){
            Game1.placeOn(x(), y(), piece.getPicName()+identicalSuffix, g, panel);
        }
        if(previous){
            Game1.placeOn(x(), y(), "yellowBox", g, panel);
        }
        if(available){
            Game1.placeOn(x(), y(), Panel.getIndicName(), g, panel);
        }
        if(piece != null && piece.getType().equals("king") && considerThreat()){
            Game1.placeOn(x(), y(), "rainbowBox", g, panel);
        }
        if(selected){
            Game1.placeOn(x(), y(), "redBox", g, panel);
        }
        /*if(threatW){
            Game1.placeOn(x(), y(), "blueBox", g, panel);
        }*/
    }

    //Get the threat that should be considered
    public boolean considerThreat(){
        Piece piece = (Piece) Game.map.get(this);
        if(piece == null) return false;
        else if(piece.isWhite()) return threatB;
        else return threatW;
    }

    //GS
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    public boolean isThreatW() {
        return threatW;
    }

    public void setThreatW(boolean threatW) {
        this.threatW = threatW;
    }

    public boolean isThreatB() {
        return threatB;
    }

    public void setThreatB(boolean threatB) {
        this.threatB = threatB;
    }

    public static String getIdenticalSuffix() {
        return identicalSuffix;
    }

    public static void setIdenticalSuffix(String identicalSuffix) {
        Coordinate.identicalSuffix = identicalSuffix;
    }

    public int getXy() {
        return xy;
    }

    public void setXy(int xy) {
        this.xy = xy;
    }

    public Piece getPiece() {
        return (Piece) Game.map.get(this);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", xy/10, xy%10);
    }
}
