package display.panels;

import display.Game1;
import advanced.Buffer;
import game_issue.Mode;

import javax.swing.*;

public class DeleteSaveFrame extends JFrame{
    JLabel label = new JLabel();
    JButton confirm = new JButton("Confirm");
    JButton cancel = new JButton("Cancel");

    JPanel labelPanel = new JPanel();
    JPanel confirmPanel = new JPanel();
    JPanel cancelPanel = new JPanel();

    Box boxH = Box.createHorizontalBox();
    Box boxV = Box.createVerticalBox();

    public DeleteSaveFrame(String fileName){
        init(fileName);
    }

    void init(String fileName){
        String suffix = fileName.equals("ALL") ? "?" : ".txt?";
        label.setText("Are you sure to delete "+fileName+suffix);

        labelPanel.add(label);
        confirmPanel.add(confirm);
        cancelPanel.add(cancel);

        boxH.add(confirmPanel);
        boxH.add(cancelPanel);
        boxV.add(labelPanel);
        boxV.add(boxH);
        add(boxV);

        confirm.addActionListener((e)->{
            Buffer.delete(fileName);
            setVisible(false);

            Game1.frame.remove(Game1.last);
            Game1.frame.remove(Game1.next);
            Game1.frame.remove(Game1.roundLabel);
            Game1.frame.remove(Game1.recordPanel);
            Game1.GmenuBar.removeAll();
            Mode.THEME.action();

            Game1.frame.remove(Game1.themePanel);
            Game1.GmenuBar.removeAll();
            Game1.frame.remove(Game1.playB);
            Game1.frame.remove(Game1.recordB);
            Game1.frame.remove(Game1.rankB);
            Mode.RECORD.action();

            if(!fileName.equals("ALL"))System.out.println("* The former "+fileName+".txt is deleted! *\n");
            else System.out.println("* All records are deleted! *\n");
        });

        cancel.addActionListener((e)->{
            setVisible(false);
            System.out.println("* Delete canceled! *\n");
        });

        setSize(300, 150);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Delete Confirmation");
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
