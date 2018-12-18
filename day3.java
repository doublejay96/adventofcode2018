import java.io.*;
import java.util.*;
class day3 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int[][] cloth = new int[1100][1100];
      String input = br.readLine();
      ArrayList<String> inputlist = new ArrayList<String>();
      while (input != null) {
         inputlist.add(input);
         String[] tokens = input.split(" ");
         String[] starts = tokens[2].split(",");
         int startx = Integer.parseInt(starts[0]);
         int starty = Integer.parseInt(starts[1].substring(0,starts[1].length()-1));
         String[] dims = tokens[3].split("x");
         int xdim = Integer.parseInt(dims[0]);
         int ydim = Integer.parseInt(dims[1]);
         for (int i = startx; i < startx + xdim; i++) {
            for (int j = starty; j < starty + ydim; j++) {
               cloth[i][j]++;
            }
         }
         input = br.readLine();
      }
      int squares = 0;
      for (int i = 0; i < 1100; i++) {
         for (int j = 0; j < 1100; j++) {
            if (cloth[i][j] >= 2) squares++;
         }
      }
      for (String s:inputlist) {
         int overlap = 0;
         String[] tokens = s.split(" ");
         String[] starts = tokens[2].split(",");
         int startx = Integer.parseInt(starts[0]);
         int starty = Integer.parseInt(starts[1].substring(0,starts[1].length()-1));
         String[] dims = tokens[3].split("x");
         int xdim = Integer.parseInt(dims[0]);
         int ydim = Integer.parseInt(dims[1]);
         for (int i = startx; i < startx + xdim; i++) {
            for (int j = starty; j < starty + ydim; j++) {
               if (cloth[i][j] > 1) overlap = 1;
            }
         }
         if (overlap == 0) {
            pw.printf("unique is %s\n", tokens[0]);
         }
      }
      pw.printf("overlap is %d\n", squares);
      pw.close();
   }
}
