import java.io.*;
import java.util.*;
class day10 {
   public static void main(String[] args) throws Exception {
      BufferedReader br = new BufferedReader(new FileReader("day10.in"));
      Scanner scan = new Scanner(System.in);
      ArrayList<star> starlist = new ArrayList<star>();
      String input = br.readLine();
      int starcount = 0;
      while (input != null) {
         star newstar = new star();
         newstar.x = Integer.parseInt(input.substring(10,16).trim());
         newstar.y = Integer.parseInt(input.substring(18,24).trim());
         newstar.vx = Integer.parseInt(input.substring(36,38).trim());
         newstar.vy = Integer.parseInt(input.substring(40,42).trim());
         starlist.add(newstar);
         starcount++;
         //System.out.printf("starcount: %d\n", starcount);
         input = br.readLine();
      }
      int ticks = 0;
      while (starlist.get(0).distance(starlist.get(1)) > 20) {
         //System.out.println(starlist.get(0).distance(starlist.get(1)));
         for (int i = 0; i < starcount; i++) {
            starlist.get(i).x += starlist.get(i).vx;
            starlist.get(i).y += starlist.get(i).vy;
         }
         ticks++;
         //System.out.printf("ticks: %d\n", ticks);
      }
      int minx = starlist.get(0).x;
      int miny = starlist.get(0).y;
      int maxx = starlist.get(0).x;
      int maxy = starlist.get(0).y;
      for (int i = 0; i < starcount; i++) {
         if (starlist.get(i).x < minx) minx = starlist.get(i).x;
         if (starlist.get(i).y < miny) miny = starlist.get(i).y;
         if (starlist.get(i).x > maxx) maxx = starlist.get(i).x;
         if (starlist.get(i).y > maxy) maxy = starlist.get(i).y;
      }
      System.out.printf("minx: %d, miny: %d\n", minx, miny);
      System.out.printf("maxx: %d, maxy: %d\n", maxx, maxy);
      int xrange = maxx - minx; int yrange = maxy - miny;
      for (int i = 0; i < starcount; i++) {
         starlist.get(i).x -= minx;
         starlist.get(i).y -= miny;
      }
      while (true) {
         int[][] starfield = new int[yrange+1][xrange+1];
         for (int i = 0; i < starcount; i++) {
            starfield[starlist.get(i).y][starlist.get(i).x] = 1;
         }
         for (int r = 0; r < yrange+1; r++) {
            for (int c = 0; c < xrange+1; c++) {
               if (starfield[r][c] == 1) System.out.print("X");
               else System.out.print(".");
            }
            System.out.println();
         }
         System.out.println(ticks);
         for (int i = 0; i < starcount; i++) {
            starlist.get(i).x += starlist.get(i).vx;
            starlist.get(i).y += starlist.get(i).vy;
         }
         ticks++;
         Thread.sleep(500);
      }
   }
}
class star {
   int x;
   int y;
   int vx;
   int vy;
   public int distance(star other) {
      int xdist = this.x - other.x;
      int ydist = this.y - other.y;
      return ydist;
   }
}
