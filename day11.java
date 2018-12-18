import java.util.*;
import java.io.*;
class day11 {
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int[][] cellgrid = new int[301][301];
      int serial = 7672;
      for (int r = 1; r <= 300; r++) {
         for (int c = 1; c <= 300; c++) {
            int rackid = r + 10;
            cellgrid[r][c] = (((((rackid * c) + serial) * rackid)/100)%10) - 5;
         }
      }
      int[][] gridrowsum = new int[301][301];//each value is the sum of itself and all values to right
      for (int r = 1; r <= 300; r++) {
         int rowsum = 0;
         for (int c = 300; c >= 1; c--) {
            rowsum += cellgrid[r][c];
            gridrowsum[r][c] = rowsum;
         }
      }
      int[][] gridcolsum = new int[302][302];//each value is sum of itself and all values to right-below,size 302 to allow one-border value of 0 at gridcolsum[r][301]
      for (int c = 1; c <= 300; c++) {
         int colsum = 0;
         for (int r = 300; r >= 1; r--) {
            colsum += gridrowsum[r][c];
            gridcolsum[r][c] = colsum;
         }
      }
      int max = -9999; int maxx = 0, maxy = 0, maxs = 0;
      for (int r = 1; r <= 300; r++) {
         for (int c = 1; c <= 300; c++) {
            //for (int s = 3; s == 3 && r+s <= 301 && c+s <= 301; s++) {
            for (int s = 1; r+s <= 301 && c+s <= 301; s++) {
               int total = 0;
               total += gridcolsum[r][c] - gridcolsum[r][c+s] - gridcolsum[r+s][c] + gridcolsum[r+s][c+s];
               if (total > max) {
                  max = total;
                  maxx = r;
                  maxy = c;
                  maxs = s;
               }
            }
         }
      }
      pw.printf("Max is %d at %d, %d, size %d\n", max, maxx, maxy, maxs);
      pw.close();
   }
}
