package display.panels;

import advanced.Game2;
import display.*;
import display.Panel;

import java.awt.*;

public class ThemePanel extends Panel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Game1.placeOn(1, 6, "kingB", g, this);
        Game1.placeOn(6, 6, "queenW", g, this);
        Game1.placeOn(1, 3, "knightW", g, this);
        Game1.placeOn(6, 3, "rookB", g, this);
        Game1.placeOn(6, 4.5F, "arrow", g, this);
        Game1.placeOn(1, 4.5F, "dot", g, this);
        Game1.placeOn(1.5f, -2, "pawnW", g, this);
        Game1.placeOn(5.5f, -2, "pawnB", g, this);

        double ratio = Game2.ratio();
        g.drawImage(Game1.getImage("redBox"), (int) (150*ratio), (int) (80*ratio),
                (int) (284*ratio),(int) (76*ratio), this);
        g.drawImage(Game1.getImage("yellowBox"), ra(150), ra(185), ra(284), ra(76), this);
        g.drawImage(Game1.getImage("blueBox"), ra(150), ra(289), ra(284), ra(76), this);

        //bottons
        g.drawImage(Game1.getImage("play"), ra(153), ra(83), ra(278), ra(70), this);
        g.drawImage(Game1.getImage("record"), ra(153), ra(188), ra(278), ra(70), this);
        g.drawImage(Game1.getImage("rank"), ra(153), ra(292), ra(278), ra(70), this);

        Game1.playB.setBounds(ra(153), ra(83), ra(278), ra(70));
        Game1.recordB.setBounds(ra(153), ra(188), ra(278), ra(70));
        Game1.rankB.setBounds(ra(153), ra(292), ra(278), ra(70));
    }
}
