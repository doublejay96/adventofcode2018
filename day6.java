import java.util.*;
import java.io.*;
import java.lang.*;
class day6 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int gridsize = 800;
      int[][] grid = new int[gridsize][gridsize];
      String input = br.readLine();
      TreeMap<Integer,coords> starts = new TreeMap<Integer,coords>();
      int coordnumber = 1;
      while (input != null) {
         String[] incoords = input.split(", ");
         int x = Integer.parseInt(incoords[0])+200;
         int y = Integer.parseInt(incoords[1])+200;
         coords inputcoords = new coords(x, y);
         starts.put(coordnumber, inputcoords);
         coordnumber++;
         input = br.readLine();
      }
      for (int i = 0; i < gridsize; i++) {
         for (int j = 0; j < gridsize; j++) {
            int totaldist = 0;
            for (int k = 1; k <= starts.size(); k++) {
               coords current = starts.get(k);
               int distance = Math.abs(i - current.x) + Math.abs(j - current.y);
               totaldist += distance;
            }
            grid[i][j] = totaldist;
         }
      }
      int minregionsqs = 0;
      for (int i = 0; i < gridsize; i++) {
         for (int j = 0; j < gridsize; j++) {
            //pw.print(grid[j][i]);
            if (grid[i][j] < 10000) minregionsqs++;
         }
         //pw.println();
      }
      /*int maxarea = 0;
      for (int i = 0; i < starts.size() + 1; i++) {
         if (closesquares[i] > maxarea) maxarea = closesquares[i];
         pw.printf("%d ", closesquares[i]);
      }*/
      pw.println();
      pw.println(minregionsqs);
      pw.close();
   }
}
class coords {
   int x;
   int y;
   public coords(int a, int b) {
      x = a;
      y = b;
   }
}
