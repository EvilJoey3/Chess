package display;

import advanced.Buffer;
import advanced.Game2;
import advanced.timers.FrequentTimer;
import advanced.timers.RefreshTimer;
import advanced.timers.TurnTimer;
import display.panels.*;
import game_issue.*;
import advanced.StupidAI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

public class Game1 extends Game{
    private static Mode mode;
    private static int x = -1, y = -1;
    private static Coordinate selection = null;
    public static String during = "White: ";
    public static StupidAI stupidAI = new StupidAI();
    public static TurnTimer turnTimer = new TurnTimer();
    public static RefreshTimer refreshTimer = new RefreshTimer();
    public static FrequentTimer frequentTimer = new FrequentTimer();


    //editor friendly boolean
    public static boolean free = false;


    public static MajorFrame frame = new MajorFrame("[Chess 15.2] made by 12110809 ZengBo " +
            "& 12110810 HuangShuo");
    public static JMenuBar GmenuBar = new JMenuBar();

    public static Panel panel = new Panel();
    public static ThemePanel themePanel = new ThemePanel();
    public static RecordPanel recordPanel = new RecordPanel();

    public static JMenuItem regret = new JMenuItem(during+"Regret");
    public static JMenuItem giveUp = new JMenuItem(during+"Give up & save");
    public static JMenuItem quit = new JMenuItem("Quit");
    public static JMenuItem reglimit = new JMenuItem("Regret limitation: ON");
    public static boolean soundOn = true;
    public static boolean onWindows = true;

    public static JMenu setBackground = new JMenu("Background");
    public static JMenu setIndicator = new JMenu("Indicator");
    public static JMenu setPiecePic = new JMenu("Plate");
    public static JMenu Gmenu = new JMenu("Options");  //
    public static JMenu recordMenu = new JMenu("Record List");
    public static JMenu deleteSaveMenu = new JMenu("Delete");


    //theme components
    public static JButton playB = new JButton(/*play.getText()*/);
    public static JButton recordB = new JButton(/*record.getText()*/);
    public static JButton rankB = new JButton(/*rank.getText()*/);

    //game components
    public static JButton[][] pl = new JButton[8][8];
    public static Label roundLabel = new Label(" "+during+" Round "+(id/2+1));
    public static Label regState = new Label(" Regret Chances Remain: "
            +"W: "+white.getRegretRemain()+" "+"B: "+black.getRegretRemain());
    public static Label turnTimeLabel = new Label("T^T");

    public static boolean playWithAI = true;
    public static boolean certainlyAIStep = false;

    //record components
    public static JButton last = new JButton();
    public static JButton next = new JButton();


    //rank components

    public static void display(){
//        frame = new JFrame();
        frame.setSize(600, 825);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Preparations
//        JMenuBar GmenuBar = new JMenuBar();
        setBackground = new JMenu("Background");
        setIndicator = new JMenu("Indicator");
        setPiecePic = new JMenu("Plate");
        Gmenu = new JMenu("Options");  //
        recordMenu = new JMenu("Record List");
        deleteSaveMenu = new JMenu("Delete");

        //Skins!!
        ButtonGroup sbg = new ButtonGroup();
        ButtonGroup sid = new ButtonGroup();
        ButtonGroup spp = new ButtonGroup();

        JRadioButtonMenuItem yBG = new JRadioButtonMenuItem("YellowBG");
        JRadioButtonMenuItem bBG = new JRadioButtonMenuItem("BlueBG");
        JRadioButtonMenuItem gBG = new JRadioButtonMenuItem("GreenBG");
        JRadioButtonMenuItem crs = new JRadioButtonMenuItem("Cross");
        JRadioButtonMenuItem dot = new JRadioButtonMenuItem("Dot");
        JRadioButtonMenuItem arw = new JRadioButtonMenuItem("Arrow");
        JRadioButtonMenuItem mono = new JRadioButtonMenuItem("Monotonous");
        JRadioButtonMenuItem fan = new JRadioButtonMenuItem("Fancy");

        sbg.add(yBG);
        sbg.add(bBG);
        sbg.add(gBG);
        sid.add(crs);
        sid.add(dot);
        sid.add(arw);
        spp.add(mono);
        spp.add(fan);

        setBackground.add(yBG);
        setBackground.add(bBG);
        setBackground.add(gBG);
        setIndicator.add(crs);
        setIndicator.add(dot);
        setIndicator.add(arw);
        setPiecePic.add(mono);
        setPiecePic.add(fan);

        GmenuBar.add(Gmenu);
        GmenuBar.add(setBackground);
        GmenuBar.add(setIndicator);
        GmenuBar.add(setPiecePic);

        frame.setJMenuBar(GmenuBar);

//        yBG.setSelected(true);
//        crs.setSelected(true);
//        mono.setSelected(true);

        //Bar Style
        JMenu themeStyle = new JMenu("Bar Style");
        ButtonGroup radioStyles = new ButtonGroup();
        JRadioButtonMenuItem metal = new JRadioButtonMenuItem("Metal"),
                nimbus = new JRadioButtonMenuItem("Nimbus"),
                windows = new JRadioButtonMenuItem("Windows"),
                windowsClassic = new JRadioButtonMenuItem("Windows Classic"),
                motif = new JRadioButtonMenuItem("Motif");
        radioStyles.add(metal);
        radioStyles.add(nimbus);
        radioStyles.add(windows);
        radioStyles.add(windowsClassic);
        radioStyles.add(motif);
        themeStyle.add(metal);
        themeStyle.add(nimbus);
        themeStyle.add(windows);
        themeStyle.add(windowsClassic);
        themeStyle.add(motif);
        GmenuBar.add(themeStyle);
        frame.setJMenuBar(GmenuBar);

        metal.setSelected(true);

        //Labels
        roundLabel = new Label(" "+during+" Round "+(id/2+1));
        roundLabel.setBounds(15, 600, 270, 50);
        roundLabel.setFont(new Font("", Font.ITALIC, 24));

        regState.setFont(new Font("", Font.ITALIC, 24));
        regState.setBounds(15, 675, 550, 50);

        turnTimeLabel.setFont(new Font("", Font.ITALIC, 24));
        turnTimeLabel.setBounds(300 ,600 ,150 ,50);

        //Menu bar
        ActionListener listener0 = e -> {
            String command = e.getActionCommand();
            try {
                fun(command, frame, GmenuBar, setBackground, setIndicator);
                roundLabel.setText(" "+Game1.during+" Round "+(id/2+1));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        };
        metal.addActionListener(listener0);
        windows.addActionListener(listener0);
        windowsClassic.addActionListener(listener0);
        nimbus.addActionListener(listener0);
        motif.addActionListener(listener0);
        yBG.addActionListener(listener0);
        bBG.addActionListener(listener0);
        gBG.addActionListener(listener0);
        crs.addActionListener(listener0);
        dot.addActionListener(listener0);
        arw.addActionListener(listener0);
        mono.addActionListener(listener0);
        fan.addActionListener(listener0);

//        Panel panel = new Panel();
//        ThemePanel themePanel = new ThemePanel();


        //Main part of the theme
        if(mode == Mode.THEME){
            //buttons
            playB = new JButton(/*play.getText()*/);
            recordB = new JButton(/*record.getText()*/);
            rankB = new JButton(/*rank.getText()*/);

            playB.setContentAreaFilled(false);
            recordB.setContentAreaFilled(false);
            rankB.setContentAreaFilled(false);
            if(!onWindows){
                playB.setOpaque(false);
                recordB.setOpaque(false);
                rankB.setOpaque(false);
                playB.setBorderPainted(false);
                recordB.setBorderPainted(false);
                rankB.setBorderPainted(false);
            }


            playB.addActionListener((e)->{
                frame.remove(themePanel);
                GmenuBar.removeAll();
//                frame.remove(GmenuBar);
                frame.remove(playB);
                frame.remove(recordB);
                frame.remove(rankB);
                Mode.GAME.action();
                System.out.println("* Start the game! *\n");
            });

            recordB.addActionListener((e)->{
                frame.remove(themePanel);
                GmenuBar.removeAll();
                frame.remove(playB);
                frame.remove(recordB);
                frame.remove(rankB);
                Mode.RECORD.action();
                System.out.println("* Check the record List! *\n");
            });

            rankB.addActionListener((e)->{
                Mode.RANK.action();
                System.out.println("* Check the rank List! *\n");
            });

            //to be continued

            frame.add(playB);
            frame.add(recordB);
            frame.add(rankB);
            frame.add(themePanel);
        }

        //Main part of the game
        if(mode == Mode.GAME){
            Game1.refreshTimer.setBreakpoint(true);
            Game1.refreshTimer = new RefreshTimer();
            Game1.refreshTimer.start();

            if(!playWithAI){
                turnTimer = new TurnTimer();
                turnTimer.start();
            }


            if(during.equals("Black: ")) switchDuring();

            //options menu

            regret = new JMenuItem(during+"Regret");
            giveUp = new JMenuItem(during+"Give up & save");
            quit = new JMenuItem("Quit");
            reglimit = new JMenuItem("Regret limitation: ON");

            pl = new JButton[8][8];

            quit.addActionListener((e)->{
                turnTimer.setBreakPoint(true);

                GmenuBar.removeAll();
                Gmenu.remove(regret);
                Gmenu.remove(giveUp);
                Gmenu.remove(quit);
                Gmenu.remove(reglimit);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        frame.remove(pl[i][j]);
                    }
                }
                frame.remove(turnTimeLabel);
                frame.remove(regState);
                frame.remove(roundLabel);
                frame.remove(panel);
                Mode.THEME.action();
                System.out.println("* Quit the game! *\n");
            });

            reglimit.addActionListener((e)->{
                if(e.getActionCommand().endsWith("N")){
                    white.setRegretRemain(10000);
                    black.setRegretRemain(10000);
                    reglimit.setText("Regret limitation: OFF");
                }else {
                    white.setRegretRemain(3);
                    black.setRegretRemain(3);
                    reglimit.setText("Regret limitation: ON");
                }
                String regW = white.getRegretRemain() > 100 ? "many" : String.valueOf(white.getRegretRemain());
                String regB = black.getRegretRemain() > 100 ? "many" : String.valueOf(black.getRegretRemain());
                regState.setText(" Regret Chances Remain: "
                        +"W: "+regW+" "+"B: "+regB);
            });

            Gmenu.add(regret);
            Gmenu.add(reglimit);
            Gmenu.add(giveUp);
            Gmenu.add(quit);
//            Gmenu.add(restart);
//            Gmenu.add(sav);

            regret.addActionListener(listener0);
            giveUp.addActionListener(listener0);

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    pl[i][j] = new JButton(/*i+String.valueOf(j)*/);
                    int[] p = p(i, j);
                    Game1.pl[i][j].setBounds(p[0], p[1], 71, 71);
                    int I = i;
                    int J = j;

                    pl[i][j].addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            panel.repaint();
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            frequentTimer = new FrequentTimer(){
                                @Override
                                public void event() {
                                    placeOn(I, J, "blueBox", panel.getGraphics(), panel);
                                }
                            };

                            frequentTimer.start();
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            frequentTimer.setBreakpoint(true);
                            panel.repaint();
                        }
                    });

                    pl[i][j].addActionListener((e) -> {
                        Game1.refreshTimer.setBreakpoint(true);
                        Game1.refreshTimer = new RefreshTimer();
                        Game1.refreshTimer.start();

                        System.out.printf("Your click: (%d, %d)\n", I, J);
                        Coordinate during = Game.plate[I][J];
                        if(selection == null){
                            selectCoo(during);
                            getAvail();
                        }else {
                            boolean condition = free ? !(I == x && J == y) : plate[I][J].isAvailable();
                            if(condition) {
                                selection.to(I, J);
                                changePrevious(x, y, I, J);
                                switchDuring();
                                regret.setText(Game1.during+"Regret");
                                giveUp.setText(Game1.during+"Give up & save");
                                buffers.add(new Buffer());

                                id++;

                                boolean determined = Game2.test();

//                                removeSelection();
//                                removeAvail();

//                                removeThreat();
//                                setAllThreat();

                                if(!playWithAI){
                                    turnTimer.reset();
                                }

                                if(soundOn){
                                    new Thread(){
                                        @Override
                                        public void run() {
                                            frame.decode();
                                            Game2.playSound(MajorFrame.pieDown);
                                        }
                                    }.start();
                                }

                                certainlyAIStep = false;
                                if(playWithAI) certainlyAIStep = true;

                                if(certainlyAIStep){
                                    System.out.println();
                                    stupidAI.move();
                                    panel.repaint();
                                    certainlyAIStep = false;

                                    if(!determined){
                                        Game2.test();
                                    }
                                }
                            }else {
                                removeSelection();
                                removeAvail();
                            }
//                        removeThreat();
                        }
                        x = I; y = J;
                        for (int k = 0; k < 8; k++) {
                            for (int l = 0; l < 8; l++) {
                                SwingUtilities.updateComponentTreeUI(pl[k][l]);
                            }
                        }

                        System.out.println();

                        roundLabel.setText(" "+Game1.during+" Round "+(id/2+1));

                        panel.repaint();
                    });
                    pl[i][j].setOpaque(false);
                    pl[i][j].setContentAreaFilled(false);
                    if(!onWindows) pl[i][j].setBorderPainted(false);
                    frame.add(pl[i][j]);
                }
            } //

            if(playWithAI && stupidAI.getIam().equals(Game.white)){
                stupidAI.move();
                panel.repaint();

                Game2.test();
            }

            frame.add(turnTimeLabel);
            frame.add(regState);
            frame.add(roundLabel);
            frame.add(panel);
        }

        //Main part of the record
        if(mode == Mode.RECORD){
            Game1.refreshTimer.setBreakpoint(true);
            Game1.refreshTimer = new RefreshTimer();
            Game1.refreshTimer.start();

            JMenuItem changePath = new JMenuItem("Save Path...");

            changePath.addActionListener((e)->{
                Buffer.changeSavePath();

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
            });

            if(during.equals("White: ")) switchDuring();
            roundLabel.setText(" Nothing");
//            recordPanel.setBounds(0, 0, 600, 600);

            JMenuItem quitRE = new JMenuItem("Quit");
            recordMenu.removeAll();

            //last & next
            int[] pLe = Game1.p(4.5f, -2), pRi = Game1.p(6, -2);
            last = new JButton();  //
            next = new JButton();  //
            last.setContentAreaFilled(false);
            next.setContentAreaFilled(false);
            if(!onWindows){
                last.setBorderPainted(false);
                last.setOpaque(false);
                next.setBorderPainted(false);
                next.setOpaque(false);
            }

            last.setBounds(pLe[0], pLe[1], 71, 71);
            next.setBounds(pRi[0], pRi[1], 71, 71);
            last.addActionListener((e)->{
                boolean kidding = Game.buffers.isEmpty();
                if(kidding || tempID == 0){
                    System.out.println("Last: failed\n");
                }else {
                    toBuffer(tempID-1);
                    switchDuring();
                    roundLabel.setText(" last is "+Game1.during+" Round "+(tempID/2+1));
                    recordPanel.repaint();
                    System.out.println("Last\n");
                }
                if(tempID == 0 && !kidding) roundLabel.setText(" Initial state: "+" Round 1");
            });
            next.addActionListener((e)->{
                boolean kidding = Game.buffers.isEmpty();
                int lastInd = Game.buffers.size()-1;
                if(kidding || tempID == lastInd){
                    System.out.println("Next: failed\n");
                }else {
                    toBuffer(tempID+1);
                    switchDuring();
                    roundLabel.setText(" last is "+Game1.during+" Round "+(tempID/2+1));
                    recordPanel.repaint();
                    System.out.println("Next\n");
                }
                if(tempID == 0 && !kidding) roundLabel.setText(" Initial state: "+" Round 1");
            });

            //prepare

            //record list
            int i = 0;
            JMenuItem tempItem = new JMenuItem();
            ArrayList<JMenuItem> safs = new ArrayList<>();

            File fi = new File(Buffer.getSavePath()+File.separator+"Save_0.txt");
            while(fi.exists()){
                tempItem = new JMenuItem("Save_"+i);
                int finalI = i;
                tempItem.addActionListener((e)->{
                    int I = finalI;
                    Boolean b = Buffer.reappear("Save_"+I);
                    if(b){
                        toBuffer(0);
                        roundLabel.setText(" Initial state: "+" Round 1");
                        recordPanel.repaint();
                        System.out.println("Show record Save_"+I+"\n");
                    }else {
                        System.out.println("Fail to show record\n");
                    }
                });
                safs.add(tempItem);
                i++;
                fi = new File(Buffer.getSavePath()+File.separator+"Save_"+i+".txt");
            }

            for (JMenuItem item:
                 safs) {
                recordMenu.add(item);
            }

            //delete save
            JMenuItem allD = new JMenuItem("ALL");
            allD.addActionListener((e)->{
                new DeleteSaveFrame("ALL");
            });
            deleteSaveMenu.add(allD);

            i = 0;
            tempItem = new JMenuItem();
            safs = new ArrayList<>();

            fi = new File(Buffer.getSavePath()+File.separator+"Save_0.txt");
            while(fi.exists()){
                String fileName = "Save_"+i;
                tempItem = new JMenuItem(fileName);
                int finalI = i;
                tempItem.addActionListener((e)->{
                    new DeleteSaveFrame(fileName);
                });
                safs.add(tempItem);
                i++;
                fi = new File(Buffer.getSavePath()+File.separator+"Save_"+i+".txt");
            }

            for (JMenuItem item:
                 safs) {
                deleteSaveMenu.add(item);
            }


            //quitRE
            quitRE.addActionListener((e)->{
                frame.remove(last);
                frame.remove(next);
                frame.remove(roundLabel);
                frame.remove(recordPanel);
                GmenuBar.removeAll();
                Mode.THEME.action();
                System.out.println("* Quit the record list! *\n");
            });

            //???????
            GmenuBar.add(recordMenu);
            GmenuBar.add(deleteSaveMenu);
            Gmenu.add(changePath);
            Gmenu.add(quitRE);

            frame.add(roundLabel);
            frame.add(last);
            frame.add(next);
            frame.add(recordPanel);
        }

        //Final settings
//        frame.setResizable(false);
        frame.setVisible(true);
    }

    //Get image easier (.png is need in a folder named pic)
    public static Image getImage(String imageName){
        String imgPath = "pic"+ File.separator+imageName+".png";
        return Toolkit.getDefaultToolkit().getImage(imgPath);
    }

    //Put image on the plate easier
    //width = height = 71; run: 14+x*139/2, rise = 14+(7-y)*139/2
    public static void placeOn(float x, float y, String imageName, Graphics g, JPanel observer){
        int[] p = p(x, y);
        double ratio = Game2.ratio();
        g.drawImage(getImage(imageName), p[0], p[1], (int) (71*ratio), (int) (71*ratio), observer);
    }

    public static int[] p(float x, float y){
        double ratio = Game2.ratio();
        int[] p = new int[2];
        p[0] = (int) ((14+x*139/2)*ratio);
        p[1] = (int) ((14+(7-y)*139/2)*ratio);
        return p;
    }

    public static int[] reverseP(int x, int y){
        int[] p = new int[2];
        p[0] = (int)(/*(double)*/((x-14)*2)/139);
        p[1] = (int)(/*(double)*/(7-(y-14)*2)/139);
        return p;
    }

    protected static void fun(String command, Object o1, Object o2, Object o3, Object o4) throws Exception{
        String bgName = Panel.getBgName(), indicName = Panel.getIndicName();
        switch (command){
            case "YellowBG":
                bgName = "bgY";
                System.out.println("* Function application! *\n");
                break;
            case "BlueBG":
                bgName = "bgB";
                System.out.println("* Function application! *\n");
                break;
            case "GreenBG":
                bgName = "bgG";
                System.out.println("* Function application! *\n");
                break;
            case "Cross":
                indicName = "cross";
                System.out.println("* Function application! *\n");
                break;
            case "Dot":
                indicName = "dot";
                System.out.println("* Function application! *\n");
                break;
            case "Arrow":
                indicName = "arrow";
                System.out.println("* Function application! *\n");
                break;
            case "Monotonous":
                Coordinate.setIdenticalSuffix("1");
                System.out.println("* Function application! *\n");
                break;
            case "Fancy":
                Coordinate.setIdenticalSuffix("");
                System.out.println("* Function application! *\n");
                break;
            case "Metal":
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                System.out.println("* Function application! *\n");
                break;
            case "Nimbus":
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                System.out.println("* Function application! *\n");
                break;
            case "Windows":
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                System.out.println("* Function application! *\n");
                break;
            case "Windows Classic":
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                System.out.println("* Function application! *\n");
                break;
            case "Motif":
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                System.out.println("* Function application! *\n");
                break;
            case "White: Regret":
            case "Black: Regret":
                turnTimer.reset();

                boolean b = Game.regret();
                if(b){
                    int remain = (Game.id%2 == 0 ? white : black).getRegretRemain();
                    (Game.id%2 == 0 ? white : black).setRegretRemain(remain-1);
                    removeThreat();
                    setAllThreat();
                    System.out.println(during+"regret\n");
                }else {
                    System.out.println(during+"fails to regret\n");
                }
                String regW = white.getRegretRemain() > 100 ? "many" : String.valueOf(white.getRegretRemain());
                String regB = black.getRegretRemain() > 100 ? "many" : String.valueOf(black.getRegretRemain());
                regState.setText(" Regret Chances Remain: "
                        +"W: "+regW+" "+"B: "+regB);
                break;
            case "White: Give up & save":
            case "Black: Give up & save":
                turnTimer.setBreakPoint(true);

                /*for (int i = 1; i < 8; i++) {
                    for (int j = 1; j < 8; j++) {
                        frame.remove(pl[i][j]);
                    }
                }*/

                String regW1 = white.getRegretRemain() > 100 ? "many" : String.valueOf(white.getRegretRemain());
                String regB1 = black.getRegretRemain() > 100 ? "many" : String.valueOf(black.getRegretRemain());
                int timmes = reglimit.getText().endsWith("N") ? 3 : 10000;
                black.setRegretRemain(timmes); white.setRegretRemain(timmes);
                regState.setText(" Regret Chances Remain: "
                        +"W: "+regW1+" "+"B: "+regB1);
                System.out.println((Game.id%2 == 0 ? black : white) + " wins!\n");
                Game.winner = Game.id%2 == 0 ? black : white;

                Buffer.save();
                new SaveFrame();
                break;
        }
        Panel.setBgName(bgName);
        Panel.setIndicName(indicName);
        SwingUtilities.updateComponentTreeUI(((JFrame) o1).getContentPane());
        SwingUtilities.updateComponentTreeUI((Component) o2);
        SwingUtilities.updateComponentTreeUI((Component) o3);
        SwingUtilities.updateComponentTreeUI((Component) o4);
    }

    //Easier select a coordinate
    public static boolean selectCoo(Coordinate coordinate){
        if(selection != null) removeSelection();
        Piece piece = coordinate.getPiece();
        if(piece == null) return false;
        Boolean isWhyte = id%2 == 0;
        boolean condition = free ? true : (piece.isWhite() == isWhyte);
        if(condition){
            if(soundOn){
                new Thread(){
                    @Override
                    public void run() {
                        frame.decode();
                        Game2.playSound(MajorFrame.pieUp);
                    }
                }.start();
            }
            selection = coordinate;
            coordinate.setSelected(true);
            System.out.printf("%s at (%d, %d) is selected\n", coordinate.getPiece(), coordinate.x(), coordinate.y());
        }
        return true;
    }

    //Get reasonable avail steps
    public static void getAvail(){
        Coordinate s = Game1.selection;
        if(s != null) s.getPiece().getAvail();
    }

    public static void removeAvail(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Coordinate c = Game.plate[i][j];
                if(c.isAvailable()) c.setAvailable(false);
            }
        }
    }

    //Make all coordinates out of threat
    public static void removeThreat(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Game.plate[i][j].setThreatW(false);
                Game.plate[i][j].setThreatB(false);
            }
        }
        for (Piece p : allPieces) {
            p.threats = new ArrayList<>();
        }
    }

    //Get reasonable threat events
    public static void setAllThreat(){
        for (Piece p : Game.allPieces) {
            p.setThreat();
        }
    }

    public static void removeSelection(){
        if(selection != null){
            selection.setSelected(false);
            selection = null;
        }
    }

    public static void changePrevious(int x1, int y1, int x2, int y2){
        if(previous[0] != null){
            previous[0].setPrevious(false);
            previous[1].setPrevious(false);
        }
        previous[0] = plate[x1][y1];
        previous[1] = plate[x2][y2];
        previous[0].setPrevious(true);
        previous[1].setPrevious(true);
    }

    public static void switchDuring(){
        String result = during.equals("White: ") ? "Black: " : "White: ";
        during = result;
    }

    static int ra(int i){ return Game2.ra(i); }
    //GS

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        Game1.mode = mode;
    }

    public static Coordinate getSelection() {
        return selection;
    }

    public static void setSelection(Coordinate selection) {
        Game1.selection = selection;
    }

    public static String getDuring() {
        return during;
    }

    public static void setDuring(String during) {
        Game1.during = during;
    }
}
