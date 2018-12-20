import java.util.*;
import java.io.*;
class day20 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      char[] input = br.readLine().toCharArray();
      int ydim = 500, xdim = 500;
      int startx = 249, starty = 301;
      char[][] map = new char[ydim][xdim];
      for (int y = 0; y < ydim; y++) {
         for (int x = 0; x < xdim; x++) {
            map[y][x] = ' ';
         }
      }
      map[starty][startx] = 'X';
      map[starty-1][startx-1] = '#'; map[starty-1][startx+1] = '#'; map[starty+1][startx-1] = '#'; map[starty+1][startx+1] = '#';
      map[starty][startx-1] = '?'; map[starty-1][startx] = '?'; map[starty][startx+1] = '?'; map[starty+1][startx] = '?';
      coords curr = new coords(starty,startx);
      Stack<coords> forks = new Stack<coords>();
      for (int i = 1; i < input.length - 1; i++) {
         char dir = input[i];
         if (dir == '(') {
            forks.push(curr);
         } else if (dir == '|') {
            curr = forks.peek();
         } else if (dir == ')') {
            curr = forks.pop();
         } else {
            curr = move(map, dir, curr);
         }
      }
      for (int y = 0; y < ydim; y++) {
         for (int x = 0; x < xdim; x++) {
            if (map[y][x] == '?') map[y][x] = '#';
            //pw.print(map[y][x]);
         }
         //pw.println();
      }
      int mostdoors = 0;
      int[][] doors = new int[ydim][xdim];
      for (int y = 0; y < ydim; y++) {
         for (int x = 0; x < xdim; x++) {
            doors[y][x] = 99999;
         }
      }
      doors[starty][startx] = 0;
      LinkedList<coords> queue = new LinkedList<coords>();
      queue.add(new coords(starty,startx));
      while (queue.size() > 0) {
         curr = queue.poll();
         if ((map[curr.y-1][curr.x] == '-') && (doors[curr.y-2][curr.x] > doors[curr.y][curr.x] + 1)) {
            doors[curr.y-2][curr.x] = doors[curr.y][curr.x] + 1;
            queue.add(new coords(curr.y-2, curr.x));
         }
         if ((map[curr.y+1][curr.x] == '-') && (doors[curr.y+2][curr.x] > doors[curr.y][curr.x] + 1)) {
            doors[curr.y+2][curr.x] = doors[curr.y][curr.x] + 1;
            queue.add(new coords(curr.y+2, curr.x));
         }
         if ((map[curr.y][curr.x+1] == '|') && (doors[curr.y][curr.x+2] > doors[curr.y][curr.x] + 1)) {
            doors[curr.y][curr.x+2] = doors[curr.y][curr.x] + 1;
            queue.add(new coords(curr.y, curr.x+2));
         }
         if ((map[curr.y][curr.x-1] == '|') && (doors[curr.y][curr.x-2] > doors[curr.y][curr.x] + 1)) {
            doors[curr.y][curr.x-2] = doors[curr.y][curr.x] + 1;
            queue.add(new coords(curr.y, curr.x-2));
         }
         if (doors[curr.y][curr.x] > mostdoors) mostdoors = doors[curr.y][curr.x];
      }
      pw.printf("most doors is %d\n", mostdoors);
      int thousanddoors = 0;
      for (int y = 0; y < ydim; y++) {
         for (int x = 0; x < xdim; x++) {
            if ((doors[y][x] != 99999) && (doors[y][x] >= 1000)) thousanddoors++;
         }
      }
      pw.printf("%d rooms are at least 1000 doors away\n", thousanddoors);
      pw.close();
   }
   public static coords move(char[][] map, char dir, coords curr) {
      if (dir == 'N') {
         map[curr.y-1][curr.x] = '-';
         if (map[curr.y-2][curr.x] != '.') {
            map[curr.y-2][curr.x] = '.';
            map[curr.y-2][curr.x-1] = '?'; map[curr.y-2][curr.x+1] = '?'; map[curr.y-3][curr.x] = '?';
            map[curr.y-3][curr.x-1] = '#'; map[curr.y-3][curr.x+1] = '#';
         }
         return new coords(curr.y-2,curr.x);
      } else if (dir == 'S') {
         map[curr.y+1][curr.x] = '-';
         if (map[curr.y+2][curr.x] != '.') {
            map[curr.y+2][curr.x] = '.';
            map[curr.y+2][curr.x-1] = '?'; map[curr.y+2][curr.x+1] = '?'; map[curr.y+3][curr.x] = '?';
            map[curr.y+3][curr.x-1] = '#'; map[curr.y+3][curr.x+1] = '#';
         }
         return new coords(curr.y+2,curr.x);
      } else if (dir == 'E') {
         map[curr.y][curr.x+1] = '|';
         if (map[curr.y][curr.x+2] != '.') {
            map[curr.y][curr.x+2] = '.';
            map[curr.y-1][curr.x+2] = '?'; map[curr.y+1][curr.x+2] = '?'; map[curr.y][curr.x+3] = '?';
            map[curr.y-1][curr.x+3] = '#'; map[curr.y+1][curr.x+3] = '#';
         }
         return new coords(curr.y,curr.x+2);
      } else if (dir == 'W') {
         map[curr.y][curr.x-1] = '|';
         if (map[curr.y][curr.x-2] != '.') {
            map[curr.y][curr.x-2] = '.';
            map[curr.y-1][curr.x-2] = '?'; map[curr.y+1][curr.x-2] = '?'; map[curr.y][curr.x-3] = '?';
            map[curr.y-1][curr.x-3] = '#'; map[curr.y+1][curr.x-3] = '#';
         }
         return new coords(curr.y,curr.x-2);
      }
      return new coords(0,0);
   }
}
class coords {
   int y;
   int x;
   public coords(int a, int b) {
      y = a; x = b;
   }
}
