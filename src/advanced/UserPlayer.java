package advanced;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class UserPlayer {
    public static ArrayList<UserPlayer> userList = new ArrayList<>();
    String name;
    int score;

     public UserPlayer(String name, int score){
         this.name = name;
         this.score = score;
     }

     //winnerCode 0, 1, 2 are respectively lose, tie, win
     public static void updateRank(String namme, int winnerCode){
         if(namme != null){
             String[] li = readRank();
             File fi = new File("Saves"+File.separator+"Rank.txt");
             try{
                 FileWriter fw = new FileWriter(fi);
                 BufferedWriter bw = new BufferedWriter(fw);
                 ArrayList<UserPlayer> tempUsers = new ArrayList<>();

                 if(li != null){
                     for (String str:
                             li) {
                         String[] s = str.split(" --> ");
                         tempUsers.add(new UserPlayer(s[0], Integer.parseInt(s[1])));
                     }
                     boolean newGuy = true;
                     UserPlayer upOri = null;
                     for (UserPlayer up:
                             tempUsers) {
                         if(up.name.equals(namme)){
                             upOri = up;
                             newGuy = false;
                             break;
                         }
                     }
                     if(newGuy){
                         tempUsers.add(new UserPlayer(namme, winnerCode));
                     }else {
                         int preScore = upOri.getScore();
                         upOri.setScore(preScore+winnerCode);
                     }
                 }else {
                     tempUsers.add(new UserPlayer(namme, winnerCode));
                 }


                 userList = new ArrayList<>();
                 userList.addAll(tempUsers);
                 userList.sort(new Comparator<UserPlayer>() {
                     @Override
                     public int compare(UserPlayer o1, UserPlayer o2) {
                         return o2.score-o1.score;
                     }
                 });

                 for (UserPlayer up:
                         userList) {
                     bw.write(up.toString());
                     bw.newLine();
                 }
                 bw.close();
                 fw.close();
             }catch (IOException e){
                 e.printStackTrace();
             }
         }
     }

     public static String[] readRank(){
         File fi = new File("Saves"+File.separator+"Rank.txt");
//         if(!fi.exists()) return null;
         try{
             FileReader fr = new FileReader(fi);
             BufferedReader br = new BufferedReader(fr);

             ArrayList<String> strList = new ArrayList<>();
             String str = br.readLine();
             while(str != null){
                 strList.add(str);
                 str = br.readLine();
             }
             br.close();
             fr.close();
             if(strList.isEmpty()) return null;
             else{
                 int l = strList.size();
                 String[] s = new String[l];
                 for (int i = 0; i < l; i++) {
                     s[i] = strList.get(i);
                 }
                 return s;
             }
         }catch (IOException e){
             e.printStackTrace();
             return null;
         }
     }

    @Override
    public String toString() {
        return name+" --> "+score;
    }

    //GS
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
