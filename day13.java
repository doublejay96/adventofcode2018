import java.io.*;
import java.util.*;
class day13 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int ylength = 150, xlength = 150;
      char[][] board = new char[ylength][xlength];
      for (int y = 0; y < ylength; y++) {
         board[y] = br.readLine().toCharArray();
      }
      TreeMap<coords, minecart> cartlist = new TreeMap<coords, minecart>();
      for (int y = 0; y < ylength; y++) {
         for (int x = 0; x < xlength; x++) {
            if (board[y][x] == '^') {
               board[y][x] = '|';
               cartlist.put(new coords(x ,y),new minecart(0, 0));
            } else if (board[y][x] == '>') {
               board[y][x] = '-';
               cartlist.put(new coords(x ,y),new minecart(1, 0));
            } else if (board[y][x] == 'v') {
               board[y][x] = '|';
               cartlist.put(new coords(x ,y),new minecart(2, 0));
            } else if (board[y][x] == '<') {
               board[y][x] = '-';
               cartlist.put(new coords(x ,y),new minecart(3, 0));
            }
         }
      }
      int cartnumbers = cartlist.size();
      int collision = 0;
      int ticks = 0;
      while (cartlist.size() > 1) {
         TreeMap<coords, minecart> listcopy = new TreeMap<coords, minecart>();
         
         while (cartlist.size() > 0) {
            Map.Entry<coords,minecart> entry = cartlist.pollFirstEntry();
            coords c = entry.getKey();
            minecart m = entry.getValue();
            coords next = new coords(c.x, c.y);
            pw.printf("cart at %d, %d ", c.x, c.y);
            if (m.orient == 0) {
               next.y = c.y-1;
               if (board[c.y-1][c.x] == '/') m.orient = 1;
               else if (board[c.y-1][c.x] == '\\') m.orient = 3;
               else if (board[c.y-1][c.x] == '+') m.orient = ((m.turning++) + 3 )%4;
            } else if (m.orient == 1) {
               next.x = c.x+1;
               if (board[c.y][c.x+1] == '/') m.orient = 0;
               else if (board[c.y][c.x+1] == '\\') m.orient = 2;
               else if (board[c.y][c.x+1] == '+') m.orient = ((m.turning++) + 0 )%4;
            } else if (m.orient == 2) {
               next.y = c.y+1;
               if (board[c.y+1][c.x] == '/') m.orient = 3;
               else if (board[c.y+1][c.x] == '\\') m.orient = 1;
               else if (board[c.y+1][c.x] == '+') m.orient = ((m.turning++) + 1 )%4;
            } else if (m.orient == 3) {
               next.x = c.x-1;
               if (board[c.y][c.x-1] == '/') m.orient = 2;
               else if (board[c.y][c.x-1] == '\\') m.orient = 0;
               else if (board[c.y][c.x-1] == '+') m.orient = ((m.turning++) + 2 )%4;
            }
            m.turning = m.turning % 3;
            if (listcopy.containsKey(next)) {
               collision = 1;
               pw.printf("collides at %d, %d\n", next.x, next.y);
               listcopy.remove(next);
            } else if (cartlist.containsKey(next)) {
               pw.printf("collides at %d, %d\n", next.x, next.y);
               cartlist.remove(next);
            } else {
               pw.printf("moves to %d, %d\n", next.x, next.y);
               listcopy.put(next, m);
            }
         }
         cartlist.clear();
         cartlist.putAll(listcopy);
         ticks++;
         pw.printf("ticks: %d\n", ticks);
      }
      pw.printf("total initial carts was %d\n", cartnumbers);
      for (coords c: cartlist.keySet()) {
         pw.printf("last cart at %d, %d\n", c.x, c.y);
      }

      pw.close();
   }
}
class minecart {
   int orient;//0, 1, 2, 3 clockwise from up
   int turning;//0 left 1 str 2 right
   public minecart(int c, int d) {
      orient = c;
      turning = d;
   }
}
class coords implements Comparable<coords> {
   int x;
   int y;
   public coords(int a, int b) {
      x = a;
      y = b;
   }
   @Override
      public int compareTo(coords other) {
         if (other.y != this.y) return (this.y - other.y);
         else return (this.x - other.x);
      }
}
