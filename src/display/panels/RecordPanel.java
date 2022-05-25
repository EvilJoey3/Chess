package display.panels;

import display.Game1;
import display.Panel;
import game_issue.Mode;

import java.awt.*;
import java.io.File;

public class RecordPanel extends Panel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Image lef = Toolkit.getDefaultToolkit().getImage("pic"+ File.separator+"left.png");
        Image righ = Toolkit.getDefaultToolkit().getImage("pic"+ File.separator+"right.png");

        if(Game1.getMode() == Mode.RECORD){
            int[] pLe = Game1.p(4.5f, -2), pRi = Game1.p(6, -2);

            g.drawImage(lef, pLe[0], pLe[1], ra(71), ra(71), this);
            g.drawImage(righ, pRi[0], pRi[1], ra(71), ra(71), this);

            Game1.last.setBounds(pLe[0], pLe[1], ra(71), ra(71));
            Game1.next.setBounds(pRi[0], pRi[1], ra(71), ra(71));

            Game1.roundLabel.setBounds(ra(15), ra(600), ra(270), ra(50));
            Game1.roundLabel.setFont(new Font("", Font.ITALIC, ra(24)));
        }
    }
}


