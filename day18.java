import java.util.*;
import java.io.*;
class day18 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int ydim = 50, xdim = 50;
      char[][] area = new char[ydim][xdim];
      for (int y = 0; y < ydim; y++) {
         char[] input = br.readLine().toCharArray();
         area[y] = input;
      }
      int minutes = 0;
      int startofrepeat = 0;
      int numofvaluesrepeated = 0;
      ArrayList<Integer> resourcevalues = new ArrayList<Integer>();
      ArrayList<Integer> repeatedvalues = new ArrayList<Integer>();
      while (minutes < 1000) {
         char[][] areacopy = new char[ydim][xdim];
         for (int y = 0; y < ydim; y++) {
            for (int x = 0; x < xdim; x++) {
               int[] adj = new int[3];
               adj = adjacent(area, y, x);
               if ((area[y][x] == '.') && (adj[1] >= 3)) areacopy[y][x] = '|';
               else if ((area[y][x] == '|') && (adj[2] >= 3)) areacopy[y][x] = '#';
               else if ((area[y][x] == '#') && ((adj[2] == 0) || (adj[1] == 0))) areacopy[y][x] = '.';
               else areacopy[y][x] = area[y][x];
            }
         }
         for (int y = 0; y < ydim; y++) {
            for (int x = 0; x < xdim; x++) {
               area[y][x] = areacopy[y][x];
            }
         }
         minutes++;
         int woods = 0, yards = 0;
         for (int y = 0; y < ydim; y++) {
            for (int x = 0; x < xdim; x++) {
               if (area[y][x] == '|') woods++;
               else if (area[y][x] == '#') yards++;
            }
         }
         int resourcevalue = woods * yards;
         if (repeatedvalues.size() > 0) {
            if (!resourcevalues.contains(resourcevalue)) {
               pw.printf("resource value %d after %d mins rejects repeatlist\n", resourcevalue, minutes);
               startofrepeat = 0;
               repeatedvalues.clear();
            } else if (repeatedvalues.get((minutes-startofrepeat) % repeatedvalues.size()) == resourcevalue) {
               pw.printf("resource value %d after %d mins is repeated\n", resourcevalue, minutes);
            } else {
               repeatedvalues.add(resourcevalue);
               pw.printf("resource value %d after %d mins added to repeatlist\n", resourcevalue, minutes);
            }
         } else if (repeatedvalues.size() == 0) {
            startofrepeat = minutes;
            repeatedvalues.add(resourcevalue);
            pw.printf("resource value %d after %d mins added to repeatlist as start\n", resourcevalue, minutes);
         }
         resourcevalues.add(resourcevalue);
      }
      pw.printf("repeat interval is %d\n", repeatedvalues.size());
      pw.printf("resource value at 1000000000 minutes will be %d\n", repeatedvalues.get((1000000000-startofrepeat) % repeatedvalues.size()));
      pw.close();
   }
   public static int[] adjacent(char[][] area, int y, int x) {
      int[] adj = new int[3];//open, woods, lumberyard
      for (int i = y - 1; i <= y + 1; i++) {
         if ((i < 0) || (i >= area.length)) continue;
         for (int j = x - 1; j <= x + 1; j++) {
            if ((j < 0) || (j >= area.length)) continue;
            if ((i == y ) && (j == x)) continue;
            if (area[i][j] == '.') adj[0]++;
            else if (area[i][j] == '|') adj[1]++;
            else if (area[i][j] == '#') adj[2]++;
         }
      }
      return adj;
   }
}
