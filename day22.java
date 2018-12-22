import java.util.*;
import java.io.*;
class day22 {
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int depth = 8112;
      int targetx = 13, targety = 743;
      int xmargin = 100, ymargin = 100;
      int[][] geoindex = new int[targety+ymargin][targetx+xmargin];
      int[][] erolevel = new int[targety+ymargin][targetx+xmargin];
      for (int y = 0; y < targety + ymargin; y++) {
         for (int x = 0; x < targetx + xmargin; x++) {
            if ((y==0)&&(x==0)) {
               geoindex[y][x] = 0;
               erolevel[y][x] = (geoindex[y][x] + depth) % 20183;
            } else if ((y==targety)&&(x==targetx)) {
               geoindex[y][x] = 0;
               erolevel[y][x] = (geoindex[y][x] + depth) % 20183;
            } else if (y==0) {
               geoindex[y][x] = x * 16807;
               erolevel[y][x] = (geoindex[y][x] + depth) % 20183;
            } else if (x==0) {
               geoindex[y][x] = y * 48271;
               erolevel[y][x] = (geoindex[y][x] + depth) % 20183;
            } else {
               geoindex[y][x] = erolevel[y][x-1] * erolevel[y-1][x];
               erolevel[y][x] = (geoindex[y][x] + depth) % 20183;
            }
         }
      }
      int[][] regiontype = new int[targety+ymargin][targetx+xmargin];
      int risklevel = 0;
      for (int y = 0; y < targety + ymargin; y++) {
         for (int x = 0; x < targetx + xmargin; x++) {
            regiontype[y][x] = (erolevel[y][x]) % 3;
         }
      }
      for (int y = 0; y < targety + 1; y++) {
         for (int x = 0; x < targetx + 1; x++) {
            risklevel += regiontype[y][x];
         }
      }
      pw.printf("risklevel is %d\n", risklevel);
      int[][][] time = new int[targety+ymargin][targetx+xmargin][3];//one level for each tool
      for (int y = 0; y < targety + ymargin; y++) {
         for (int x = 0; x < targetx + xmargin; x++) {
            for (int z = 0; z < 3; z++) {
               time[y][x][z] = 999999;
            }
         }
      }
      PriorityQueue<squaretooltime> queue = new PriorityQueue<squaretooltime>();
      queue.add(new squaretooltime(0,0,1,0));
      while (queue.size() > 0) {//Dijkstra
         squaretooltime curr = queue.poll();
         if (time[curr.y][curr.x][curr.tool] <= curr.time) continue;
         else time[curr.y][curr.x][curr.tool] = curr.time;
         for (int z = 0; z < 3; z++) {//change in this sq to other possible tool
            if ((curr.tool != z) && (regiontype[curr.y][curr.x] != z) && (time[curr.y][curr.x][z] > curr.time + 7)) {
               queue.add(new squaretooltime(curr.y, curr.x, z, curr.time + 7));
            }
         }
         if (curr.y < targety + ymargin - 1) {
            if ((regiontype[curr.y+1][curr.x] != curr.tool) && (time[curr.y+1][curr.x][curr.tool] > curr.time + 1)) {
               queue.add(new squaretooltime(curr.y+1,curr.x,curr.tool,curr.time+1));
            }
         }
         if (curr.x < targetx + xmargin - 1) {
            if ((regiontype[curr.y][curr.x+1] != curr.tool) && (time[curr.y][curr.x+1][curr.tool] > curr.time + 1)) {
               queue.add(new squaretooltime(curr.y,curr.x+1,curr.tool,curr.time+1));
            }
         }
         if (curr.y > 0) {
            if ((regiontype[curr.y-1][curr.x] != curr.tool) && (time[curr.y-1][curr.x][curr.tool] > curr.time + 1)) {
               queue.add(new squaretooltime(curr.y-1,curr.x,curr.tool,curr.time+1));
            }
         }
         if (curr.x > 0) {
            if ((regiontype[curr.y][curr.x-1] != curr.tool) && (time[curr.y][curr.x-1][curr.tool] > curr.time + 1)) {
               queue.add(new squaretooltime(curr.y,curr.x-1,curr.tool,curr.time+1));
            }
         }
      }
      pw.println(time[targety][targetx][1]);
      pw.close();
   }
}
class squaretooltime implements Comparable<squaretooltime> {
   int y; int x;
   int tool; //0 is neither, 1 is torch, 2 is climbing gear
   int time;
   public squaretooltime(int a, int b, int c, int d) {
      y = a; x = b;
      tool = c;
      time = d;
   }
   public int compareTo(squaretooltime other) {
      return this.time - other.time;
   }
}
