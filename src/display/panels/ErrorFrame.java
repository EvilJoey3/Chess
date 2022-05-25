package display.panels;

import display.Game1;

import javax.swing.*;
import java.awt.*;

public class ErrorFrame extends JFrame {

    JLabel label = new JLabel();
    JButton ok = new JButton("OK");

    JPanel labelPanel = new JPanel();
    JPanel okPanel = new JPanel();

    Box boxV = Box.createVerticalBox();

    public ErrorFrame(String text){
        label.setFont(new Font("", Font.BOLD, 16));
        label.setText(text);
        init();
    }

    void init(){
        labelPanel.add(label);
        okPanel.add(ok);

        boxV.add(labelPanel);
        boxV.add(okPanel);

        add(boxV);
        ok.addActionListener(e->{
            Game1.turnTimer.setBreakPoint(true);
            setVisible(false);
        });

        setAlwaysOnTop(true);
        setSize(550, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Error");
        setVisible(true);
    }
}
