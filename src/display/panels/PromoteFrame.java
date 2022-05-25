package display.panels;

import advanced.Buffer;
import display.Game1;
import game_issue.Game;
import game_issue.Piece;
import game_issue.Player;
import game_issue.piece_type.*;
import javax.swing.*;

public class PromoteFrame extends JFrame {

    JLabel label = new JLabel("You wanna promote the pawn to");
    JButton roo = new JButton("Rook");
    JButton knigh = new JButton("Knight");
    JButton bisho = new JButton("Bishop");
    JButton quee = new JButton("Queen");

    JPanel labelPanel = new JPanel();
    JPanel rooPanel = new JPanel();
    JPanel knighPanel = new JPanel();
    JPanel bishoPanel = new JPanel();
    JPanel queePanel = new JPanel();

    Box boxH = Box.createHorizontalBox();
    Box boxV = Box.createVerticalBox();

    public PromoteFrame(Piece pawnPiece){
        init(pawnPiece);
    }

    void init(Piece pawnPiece){
        labelPanel.add(label);
        rooPanel.add(roo);
        knighPanel.add(knigh);
        bishoPanel.add(bisho);
        queePanel.add(quee);

        boxH.add(rooPanel);
        boxH.add(knighPanel);
        boxH.add(bishoPanel);
        boxH.add(queePanel);
        boxV.add(labelPanel);
        boxV.add(boxH);
        add(boxV);

        roo.addActionListener((e)->{
            try{
                transform(pawnPiece, "rook");
                setVisible(false);
            }catch (NullPointerException ee){
                setVisible(false);
            }
        });

        knigh.addActionListener((e)->{
            try{
                transform(pawnPiece, "knight");
                setVisible(false);
            }catch (NullPointerException ee){
                setVisible(false);
            }
        });

        bisho.addActionListener((e)->{
            try{
                transform(pawnPiece, "bishop");
                setVisible(false);
            }catch (NullPointerException ee){
                setVisible(false);
            }
        });

        quee.addActionListener((e)->{
            try{
                transform(pawnPiece, "queen");
                setVisible(false);
            }catch (NullPointerException ee){
                setVisible(false);
            }
        });

        setSize(350, 150);
        setTitle("Pawn Promotion");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    public static boolean transform(Piece pawnPiece, String type){
        if(pawnPiece.getType().equals("pawn")){
            int X = pawnPiece.x(), Y = pawnPiece.y();
            Player player = pawnPiece.getOwner();
            boolean success = true;
            Piece piece = null;
            switch (type){
                case "rook":
                    Game.eliminate(X, Y);
                    piece = new Rook("rook", X, Y);
                    break;
                case "knight":
                    Game.eliminate(X, Y);
                    piece = new Knight("knight", X, Y);
                    break;
                case "bishop":
                    Game.eliminate(X, Y);
                    piece = new Bishop("bishop", X, Y);
                    break;
                case "queen":
                    Game.eliminate(X, Y);
                    piece = new Queen("queen", X, Y);
                    break;
                default:
                    success = false;
            }
            if(success){

                player.addPiece(piece);

                Game.buffers.remove(Game.buffers.size()-1);
                Game.buffers.add(new Buffer());

                Game1.removeThreat();
                Game1.setAllThreat();

                System.out.println(Game1.during+"promoted a pawn to "+type+"\n");
                return true;
            }
            else {
                System.out.println("Promotion failed!\n");
                return false;
            }
        }else {
            System.out.println("Promotion failed!\n");
            return false;
        }
    }
}
