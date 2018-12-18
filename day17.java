import java.util.*;
import java.io.*;
class day17 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static int maxx = 735+1, minx = 438-1, maxy = 1845;
   //public static int maxx = 507, minx = 494, maxy = 13;
   public static int xdim = maxx-minx+1, ydim = maxy+1;
   public static void main(String[] args) throws Exception {
      char[][] underground = new char[ydim][xdim];
      String input = br.readLine();
      for (int y = 0; y < ydim; y++) {
         for (int x = 0; x < xdim; x++) {
            underground[y][x] = '.';
         }
      }
      int miny = ydim;
      underground[0][500-minx] = '+';
      while (input != null) {
         if (input.substring(0, 2).equals("x=")) {
            String[] inputs = input.split(", y=");
            int x = Integer.parseInt(inputs[0].substring(2));
            String[] inputys = inputs[1].split("\\.\\.");
            int y1 = Integer.parseInt(inputys[0]);
            if (y1 < miny) miny = y1;
            int y2 = Integer.parseInt(inputys[1]);
            if (y2 < miny) miny = y2;
            for (int y = y1; y <= y2; y++) {
               underground[y][x-minx] = '#';
            }
         } else {
            String[] inputs = input.split(", x=");
            int y = Integer.parseInt(inputs[0].substring(2));
            if (y < miny) miny = y;
            String[] inputxs = inputs[1].split("\\.\\.");
            int x1 = Integer.parseInt(inputxs[0]);
            int x2 = Integer.parseInt(inputxs[1]);
            for (int x = x1; x <= x2; x++) {
               underground[y][x-minx] = '#';
            }
         }
         input = br.readLine();
      }
      char[][] copy = new char[ydim][xdim];
      for (int y = 0; y < ydim; y++) {
         for (int x = 0; x < xdim; x++) {
            copy[y][x] = underground[y][x];
         }
      }
      LinkedList<coords> verticalflow = new LinkedList<coords>();
      verticalflow.add(new coords(1, 500-minx));
      underground[1][500-minx] = '|';
      int done = 0;
      //for (int i = 0; i < 800; i++) {
      while (verticalflow.size() > 0) {
         coords curr = verticalflow.pollFirst();
         //pw.printf("square considered is %d, %d\n", curr.y, curr.x);
         if (curr.y == maxy) {
            continue;
         } else if (underground[curr.y+1][curr.x] == '.') {
            underground[curr.y+1][curr.x] = '|';
            //pw.printf("adding down %d, %d\n", curr.y+1, curr.x);
            verticalflow.add(new coords(curr.y+1, curr.x));
         } else if ((underground[curr.y+1][curr.x] == '#') || (underground[curr.y+1][curr.x] == '~')){
            underground[curr.y][curr.x] = '|';
            coords nextleft = flowLeft(underground, curr);
            coords nextright = flowRight(underground, curr);
            //pw.printf("nextleft is %d, %d\n", nextleft.y, nextright.x);
            //pw.printf("nextright is %d, %d\n", nextright.y, nextright.x);
            if ((nextright.equals(curr) == 1) && (nextleft.equals(curr) == 1)) {
               underground[curr.y][curr.x] = '~';
               fillLeft(underground, curr);
               fillRight(underground, curr);
               //pw.println("filling left and right");
               verticalflow.add(new coords(curr.y - 1, curr.x));
            } else {
               if (nextright.equals(curr) != 1) {
                  //pw.println("adding nextright");
                  verticalflow.add(nextright);
               } 
               if (nextleft.equals(curr) != 1) {
                  //pw.println("adding nextleft");
                  verticalflow.add(nextleft);
               }
            }
         } else if (underground[curr.y+1][curr.x] == '|') {
            continue;
         }
         /*int watersqs = 0;
         for (int y = 210; y < 240; y++) {
            for (int x = 0; x < xdim; x++) {
               pw.print(underground[y][x]);
               if (underground[y][x] == '|') watersqs++;
               else if (underground[y][x] == '~') watersqs++;
            }
            pw.println();
         }
         pw.printf("Water sq no is %d\n", watersqs);*/
      }
      int watersqs = 0;
      for (int y = miny; y < ydim; y++) {
         for (int x = 0; x < xdim; x++) {
            pw.print(underground[y][x]);
            if (underground[y][x] == '~') watersqs++;
         }
         pw.println();
      }
      pw.printf("Water sq no is %d\n", watersqs);
      pw.close();
   }
   public static coords flowRight(char[][] underground, coords curr) {
      for (int x = curr.x; (x+1 < xdim) && ((underground[curr.y][x+1] == '|') || (underground[curr.y][x+1] == '.')); x++) {
         if ((underground[curr.y+1][x+1] == '#') || (underground[curr.y+1][x+1] == '~')) {
            underground[curr.y][x+1] = '|';
         } else if (underground[curr.y+1][x+1] == '.') {
            underground[curr.y][x+1] = '|';
            return new coords(curr.y, x+1);
         } else if (underground[curr.y+1][x+1] == '|') {
            return new coords(curr.y+1, x+1);
         }
      }
      return curr;
   }
   public static coords flowLeft(char[][] underground, coords curr) {
      for (int x = curr.x; (x-1 >= 0) && ((underground[curr.y][x-1] == '|') || (underground[curr.y][x-1] == '.')); x--) {
         if ((underground[curr.y+1][x-1] == '#') || (underground[curr.y+1][x-1] == '~')) {
            underground[curr.y][x-1] = '|';
         } else if (underground[curr.y+1][x-1] == '.') {
            underground[curr.y][x-1] = '|';
            return new coords(curr.y, x-1);
         } else if (underground[curr.y+1][x-1] == '|') {
            return new coords(curr.y+1, x-1);
         }
      }
      return curr;
   }
   public static void fillRight(char[][] underground, coords curr) {
      for (int x = curr.x; (x+1<xdim) && (underground[curr.y][x+1] == '|'); x++) {
         underground[curr.y][x+1] = '~';
      }
      return;
   }
   public static void fillLeft(char[][] underground, coords curr) {
      for (int x = curr.x; (x-1>=0) && (underground[curr.y][x-1] == '|'); x--) {
         underground[curr.y][x-1] = '~';
      }
      return;
   }
   }
   class coords {
      int y;
      int x;
      public coords(int a, int b) {
         y = a; x = b;
      }
      public int equals(coords other) {
         if ((this.y == other.y) && (this.x == other.x)) return 1;
         else return 0;
      }
   }
