package display.panels;

import advanced.UserPlayer;
import display.Game1;
import game_issue.*;

import javax.swing.*;
import java.awt.*;

public class RankFrame extends JFrame {
    JList list;

    JButton quit = new JButton("Quit");
    JButton delete = new JButton("Delete");

    JScrollPane listPane = new JScrollPane(list);
    JPanel pan = new JPanel();
    JPanel quitPanel = new JPanel();
    JPanel deletePanel = new JPanel();

    Box boxH = Box.createHorizontalBox();
    Box boxV = Box.createVerticalBox();

    public RankFrame(){
        init();
    }

    void init(){
        quitPanel.add(quit);
        deletePanel.add(delete);
        boxH.add(deletePanel);
        boxH.add(quitPanel);

        String[] s0 = UserPlayer.readRank();
        if(s0 != null){
            int l = s0.length;
            String[] s = new String[l];
            for (int i = 0; i < l; i++) {
                s[i] = (i+1)+".  "+s0[i];
            }
            list = new JList<>(s);
        }else {
            String[] s = new String[]{""};
            list = new JList<>(s);
        }

        list.setVisibleRowCount(20);
        list.setFont(new Font("", Font.BOLD, 16));
        list.setPreferredSize(new Dimension(650, 500));
        pan.setPreferredSize(new Dimension(650, 500));
        pan.add(list);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);

        boxV.add(pan);

        boxH.setPreferredSize(new Dimension(650, 50));
        boxV.add(boxH);
        add(boxV);
        boxV.add(Box.createGlue());

        delete.addActionListener((e)->{
            new DeleteRankFrame();
        });

        quit.addActionListener((e)->{
            Game1.setMode(Mode.THEME);
            setVisible(false);
            System.out.println("* Quit the rank list! *\n");
        });

        setSize(700, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Rank List");
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

