import java.io.*;
import java.util.*;
class day9 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      String[] tokens = br.readLine().split(" ");
      int players = Integer.parseInt(tokens[0]);
      int lastmarble = Integer.parseInt(tokens[6]);
      marblecircle circle = new marblecircle();
      long[] scores = new long[players];
      long highscore = 0;
      for (int value = 1; value <= lastmarble; value++) {
         if (value % 23 == 0) {
            int player = value % players;
            scores[player] += circle.remove() + value;
            if (scores[player] > highscore) highscore = scores[player];
         } else {
            circle.place(value);
         }
      }
      pw.printf("highscore is %d\n", highscore);
      pw.close();
   }
}

class marble {
   int value;
   marble clockwise;
   marble counterclock;
}
class marblecircle {
   private marble current;
   public marblecircle() {
      marble insert = new marble();
      insert.value = 0;
      insert.clockwise = insert;
      insert.counterclock = insert;
      current = insert;
   }
   public void place(int value) {
      marble insert = new marble();
      insert.value = value;
      insert.clockwise = current.clockwise.clockwise;
      insert.counterclock = current.clockwise;
      current.clockwise.clockwise.counterclock = insert;
      current.clockwise.clockwise = insert;
      current = insert;
   }
   public int remove() {
      marble removed = current;
      for (int i = 0; i < 7; i++) {
         removed = removed.counterclock;
      }
      removed.counterclock.clockwise = removed.clockwise;
      removed.clockwise.counterclock = removed.counterclock;
      current = removed.clockwise;
      return removed.value;
   }
}
