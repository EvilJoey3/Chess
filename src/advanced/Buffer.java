package advanced;

import display.panels.ErrorFrame;
import game_issue.*;
import game_issue.Game;
import game_issue.Piece;
import game_issue.piece_type.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Iterator;

public class Buffer {

    public boolean danger = false;

    private final int id;
    private final boolean isWhite;
    private final int roundNum;

    public final ArrayList<Piece> allPieces;
    public final ArrayList<Piece> whiteList;
    public final ArrayList<Piece> blackList;
    public final HashMap map;
    public final Coordinate[] previous;

    private boolean castlingW;
    private boolean caLW;
    private boolean caRW;
    private boolean castlingB;
    private boolean caLB;
    private boolean caRB;

    private static String savePath = "Saves";



    public Buffer(){
        castlingW = Game.white.isCastling();
        caLW = Game.white.isCaL();
        caRW = Game.white.isCaR();

        castlingB = Game.black.isCastling();
        caLB = Game.black.isCaL();
        caRB = Game.black.isCaR();

        this.id = Game.id;
        isWhite = id%2 == 0;
        roundNum = id/2+1;
        allPieces = (ArrayList<Piece>) Game.allPieces.clone();
        whiteList = (ArrayList<Piece>) Game.white.pieces.clone();
        blackList = (ArrayList<Piece>) Game.black.pieces.clone();
        previous = Game.previous.clone();
        map = (HashMap) Game.map.clone();
    }

    //constructor for reappearance
    public Buffer(int id, HashMap map, Coordinate[] previous){
        castlingW = true;
        caLW = true;
        caRW = true;

        castlingB = true;
        caLB = true;
        caRB = true;

        this.id = id;
        isWhite = id%2 == 0;
        roundNum = id/2+1;
        allPieces = new ArrayList<>();
        blackList = new ArrayList<>();
        whiteList = new ArrayList<>();
        this.map = map;
        this.previous = previous;
    }

    //Save a game
    public static boolean save(){
        String bb = getSavePath();
        if(bb == null){
//                System.out.println("* Delete failed! *\n");
            return false;
        }

        int i = 0;
        File fi = new File(savePath+File.separator+"Save_0.txt");
        while(fi.exists()){
            i++;
            fi = new File(savePath+File.separator+"Save_"+i+".txt");
        }
        try{
            FileWriter fw = new FileWriter(fi);
            BufferedWriter bw = new BufferedWriter(fw);
            int k = 0;
            for (Buffer buffer:
                 Game.buffers) {
                bw.write(String.valueOf(k));
                bw.newLine();
                for (Piece piece:
                     buffer.allPieces) {
                    Coordinate coo = (Coordinate) buffer.map.get(piece);
                    String str = String.format("%s %s %d %d", piece.getOwner(),
                            piece.getType(), coo.x(), coo.y());
                    bw.write(str);
                    bw.newLine();
                }
                if(buffer.previous[0] != null){
                    Coordinate[] pr = buffer.previous;
                    String str = String.format("%d %d %d %d pre", pr[0].x(), pr[0].y(),
                            pr[1].x(), pr[1].y());
                    bw.write(str);
                    bw.newLine();
                }else {
                    bw.write("-1 -1 -1 -1 pre");
                    bw.newLine();
                }
                k++;
            }
            bw.close();
            fw.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //get the game state from a save
    public static boolean reappear(File fi){
        if(!fi.exists()) return false;
        if(ohNoBaby(fi)){
            return false;
        }

        try{
            FileReader fr = new FileReader(fi);
            BufferedReader br = new BufferedReader(fr);
            String str = null;

            int id = 0;
            Buffer buff = null;
            HashMap ma = null;
            Coordinate[] prev = null;

            int lyne = 0;

            while ((str = br.readLine()) != null){
                lyne++;
                String[] st = str.split(" ");
                /*if(grammarError(lyne, st) || plateError(lyne, st)){
                    return false;
                }*/
                switch (st.length){
                    case 1:
                        /*int testt = Integer.parseInt(st[0]);
                        if(id != testt){
                            new ErrorFrame(String.format("TURN 103: wrong turn (game id) at line %d", lyne));
                            return false;
                        }*/

                        ma = new HashMap();
                        prev = new Coordinate[2];
                        prev[0] = null; prev[1] = null;
                        buff = new Buffer(id, ma, prev);
                        break;
                    case 5:
                        if(!st[0].equals("-1")){
                            int[] p = new int[4];
                            for (int i = 0; i < 4; i++) {
                                p[i] = Integer.parseInt(st[i]);
                            }
                            prev[0] = Game.plate[p[0]][p[1]];
                            prev[1] = Game.plate[p[2]][p[3]];
                        }
                        Game.buffers.add(buff);
                        id++;
                        break;
                    default:
                        int[] p = new int[2];
                        p[0] = Integer.parseInt(st[2]);
                        p[1] = Integer.parseInt(st[3]);
                        Coordinate coo = Game.plate[p[0]][p[1]];
                        Piece pie = new Piece("pawn", 0, 0);
                        for (Piece pe :
                                Game.allPieces) {
                            if(pe.getType().equals(st[1])){
                                if(pe.getOwner().toString().equals(st[0])){
                                    Piece peee;
                                    switch (pe.getType()){
                                        case "pawn":
                                            peee = new Pawn(pe.getType(), 0, 0);
                                            break;
                                        case "rook":
                                            peee = new Rook(pe.getType(), 0, 0);
                                            break;
                                        case "knight":
                                            peee = new Knight(pe.getType(), 0, 0);
                                            break;
                                        case "bishop":
                                            peee = new Bishop(pe.getType(), 0, 0);
                                            break;
                                        case "queen":
                                            peee = new Queen(pe.getType(), 0, 0);
                                            break;
                                        default:
                                            peee = new King(pe.getType(), 0, 0);
                                    }
                                    pe.getOwner().addPiece(peee);
                                    pie = peee;
                                    break;
                                }
                            }
                        }
                        ma.put(pie, coo);
                        ma.put(coo, pie);
                }
                //to be continued
            }
            br.close();
            fr.close();
            System.out.println("* Showing record! *\n");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("* Showing record failed! *\n");
            return false;
        }
    }

    public static boolean reappear(String fName){
        String bb = getSavePath();
        if(bb == null){
            System.out.println("* Showing record failed! *\n");
            return false;
        }

        Game.initialize();
        Game.buffers.remove(0);
        File fi = new File(savePath+File.separator+fName+".txt");
        return reappear(fi);
    }

    //save path operations
    public static String getSavePath(){
        try{
            File pathF = new File("Saves"+File.separator+"PATH.txt");
            if(!pathF.exists()) return null;

            FileReader fr = new FileReader(pathF);
            BufferedReader br = new BufferedReader(fr);

            savePath = br.readLine();

            br.close();
            fr.close();
            return savePath;
        }catch (Exception ee){
            ee.printStackTrace();
            return null;
        }
    }

    public static boolean changeSavePath(){
        try {
            String b = getSavePath();
            if(b == null){
                System.out.println("* Failed to change the path! *\n");
                return false;
            }

            File pathF = new File("Saves"+File.separator+"PATH.txt");
            FileWriter fw = new FileWriter(pathF);
            BufferedWriter bw = new BufferedWriter(fw);

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showDialog(null, "Change Saving Path");
            File fileChosen = chooser.getSelectedFile();

            String pathh;
            if(fileChosen == null || !fileChosen.exists()){
                pathh = "Saves";
                bw.write(pathh);
            }else {
                pathh = fileChosen.getAbsolutePath();
                bw.write(pathh);
            }

            bw.close();
            fw.close();
            System.out.printf("* Save path is changed to --> %s *\n\n", pathh);
            return true;
        }catch (Exception ee){
            System.out.println("* Failed to change the path! *\n");
            ee.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String fileName){
        File fi = null;

        String bb = getSavePath();
        if(bb == null){
            System.out.println("* Delete failed! *\n");
            return false;
        }

        if(!fileName.equals("ALL")){
            if(fileName.equals("Rank") || fileName.equals("PATH")){
                System.out.println("* Delete failed! *\n");
                return false;
            }

            String pathName = savePath+File.separator+fileName+".txt";
            fi = new File(pathName);

            if(!fi.exists()){
                System.out.println("* Delete failed! *\n");
                return false;
            }
            fi = null;
        }

        int cou = 0;
        ArrayList<File> tempList = new ArrayList<>();
        File tempFi = null;
        while(tempFi == null || tempFi.exists()){
            if(tempFi != null) tempList.add(tempFi);

            String tempPath = savePath+File.separator+"Save_"+cou+".txt";
            tempFi = new File(tempPath);
            cou++;
        }

        ArrayList<File> tempList1 = new ArrayList<>();
        for (int i = 0; i < tempList.size(); i++) {
            String tempPath = savePath+File.separator+"Sa"+i+".txt";
            File desk = new File(tempPath);
            tempList.get(i).renameTo(desk);
            tempList1.add(desk);
            if(fileName.substring(fileName.length()-1).equals(String.valueOf(i))){
                fi = desk;
            }
        }

        if(fileName.equals("ALL")){
            for(Iterator<File> it = tempList1.iterator(); it.hasNext();){
                it.next().delete();
            }
            return true;
        }

        int coun = 0;
        for(; coun < tempList1.size();){
            File tF = tempList1.get(coun);
            if(fi != null && fi.getAbsolutePath().equals(tF.getAbsolutePath())){
                tempList1.remove(tF);
                boolean b = fi.delete();
                if(!b){
                    System.out.println("* Delete failed! *\n");
                    return false;
                } else fi = null;
            }else {
                String tempPath = savePath+File.separator+"Save_"+coun+".txt";
                File desk = new File(tempPath);
                tF.renameTo(desk);
                coun++;
            }
        }

        System.out.println("* The former "+fileName+".txt "+"is deleted! *\n");
        return true;
    }

    //Errors
    static boolean ohNoBaby(File file){
        try{
            int liine = 0;
            int id = 0;
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String str;
            while ((str = br.readLine()) != null){
                liine++;
                String[] stt = str.split(" ");
                if(grammarError(liine, stt) || plateError(liine, stt)){
                    return true;
                }
                if(stt.length == 1){
                    if(Integer.parseInt(stt[0]) != id){
                        new ErrorFrame(String.format("TURN 103: wrong turn (game id) at line %d", liine));
                        return true;
                    }
                    id++;
                }
            }
            return false;
        }catch (IOException eee){
            eee.printStackTrace();
            return true;
        }
    }

    static boolean grammarError(int line, String[] str){
        int l = str.length;
        if(l == 1){
            try{
                int tes = Integer.parseInt(str[0]);
            }catch (NumberFormatException ee){
                new ErrorFrame(String.format("GRAMMAR 102: game id is not an integer at line %d", line));
                return true;
            }
        }else if (l == 4){
            if(!(str[0].equals("WHITE") || str[0].equals("BLACK"))){
                new ErrorFrame(String.format("GRAMMAR 102: invalid player at line %d", line));
                return true;
            }
            switch (str[1]){
                case "pawn":
                case "rook":
                case "queen":
                case "bishop":
                case "knight":
                case "king":
                    break;
                default:
                    new ErrorFrame(String.format("GRAMMAR 102: invalid piece type at line %d", line));
                    return true;
            }
            try{
                int tes = Integer.parseInt(str[2]);
                int tes1 = Integer.parseInt(str[3]);
            }catch (NumberFormatException ee){
                new ErrorFrame(String.format("GRAMMAR 102: coordinate is not an integer at line %d", line));
                return true;
            }
        }else if (l == 5){
            try{
                int tes = Integer.parseInt(str[0]);
                int tes1 = Integer.parseInt(str[1]);
                int tes2 = Integer.parseInt(str[2]);
                int tes3 = Integer.parseInt(str[3]);
            }catch (NumberFormatException ee){
                new ErrorFrame(String.format("GRAMMAR 102: coordinate is not an integer at line %d", line));
                return true;
            }
            if(!str[4].equals("pre")){
                new ErrorFrame(String.format("GRAMMAR 102: wrong previous mark at line %d", line));
                return true;
            }
        }else {
            new ErrorFrame(String.format("GRAMMAR 102: unable to identify at line %d", line));
            return true;
        }
        return false;
    }

    static boolean plateError(int line, String[] str){
        int l = str.length;
        if(l == 1){
            return false;
        }else if(l == 4){
            int x = Integer.parseInt(str[2]), y = Integer.parseInt(str[3]);
            if(x < 0 || x > 7
                    || y < 0 || y > 7){
                new ErrorFrame(String.format("PLATE 101: number out of bound at line %d", line));
                return true;
            }else return false;
        }else if(l == 5){
            int x0 = Integer.parseInt(str[0]), y0 = Integer.parseInt(str[1]),
                    x1 = Integer.parseInt(str[2]), y1 = Integer.parseInt(str[3]);
            if(x0 == -1 && y0 == -1 && x1 == -1 && y1 == -1) return false;
            if(x0 < 0 || x0 > 7
                    ||y0 < 0 || y0 > 7
                    ||x1 < 0 || x1 > 7
                    ||y1 < 0 || y1 > 7){
                new ErrorFrame(String.format("PLATE: number out of bound at line %d", line));
                return true;
            }else return false;
        } else return true;
    }

    //GS
    public int getId() {
        return id;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public boolean isCastlingW() {
        return castlingW;
    }

    public boolean isCaLW() {
        return caLW;
    }

    public boolean isCaRW() {
        return caRW;
    }

    public boolean isCastlingB() {
        return castlingB;
    }

    public boolean isCaLB() {
        return caLB;
    }

    public boolean isCaRB() {
        return caRB;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Buffer){

            for (Object o:
                 ((Buffer) obj).map.keySet()) {
                if(!map.keySet().contains(o)) return false;
            }

            for (Object o:
                 map.keySet()) {
                if(o instanceof Coordinate && !map.get(o).equals(((Buffer) obj).map.get(o))) return false;
            }

            return true;
        }else return false;
    }
}
