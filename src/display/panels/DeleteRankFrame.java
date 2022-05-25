package display.panels;

import display.Game1;
import game_issue.Mode;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeleteRankFrame extends JFrame {
    JButton confirm = new JButton("Confirm");
    JButton cancel = new JButton("Cancel");
    JLabel label = new JLabel("Are you sure to delete the rank list?");

    JPanel confirmPanel = new JPanel();
    JPanel cancelPanel = new JPanel();
    JPanel labelPanel = new JPanel();

    Box boxH = Box.createHorizontalBox();
    Box boxV = Box.createVerticalBox();

    public DeleteRankFrame() {
        init();
    }

    void init() {
        confirmPanel.add(confirm);
        cancelPanel.add(cancel);
        labelPanel.add(label);
        boxH.add(confirmPanel);
        boxH.add(cancelPanel);
        boxV.add(labelPanel);
        boxV.add(boxH);
        add(boxV);

        confirm.addActionListener((e) -> {
            File fi = new File("Saves" + File.separator + "Rank.txt");
            try {
                FileWriter fw = new FileWriter(fi);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("");
                bw.close();
                fw.close();
            } catch (IOException ee) {
                ee.printStackTrace();
            }

            Mode.rankFrame.setVisible(false);
            Mode.rankFrame = new RankFrame();

            setVisible(false);
            SwingUtilities.updateComponentTreeUI(this.getContentPane());
            System.out.println("* The rank list is deleted! *\n");
        });

        cancel.addActionListener((e) -> {
            setVisible(false);
            SwingUtilities.updateComponentTreeUI(Game1.frame.getContentPane());
            System.out.println("* Delete canceled! *\n");
        });

        setSize(300, 150);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Delete Confirmation");
        setVisible(true);
    }
}
