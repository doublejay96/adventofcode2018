import java.util.*;
import java.io.*;
class day15 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int ydim = 32, xdim = 32;
      char[][] map = new char[ydim][xdim];
      int numgoblins = 0; int numelfs = 0;
      TreeSet<unit> unitset = new TreeSet<unit>();
      for (int y = 0; y < ydim; y++) {
         char[] inputarr = br.readLine().toCharArray();
         for (int x = 0; x < xdim; x++) {
            map[y][x] = inputarr[x];
            if (inputarr[x] == 'G') {
               numgoblins++;
               unitset.add(new unit(y, x, 1, 200, 3));
            } else if (inputarr[x] == 'E') {
               numelfs++;
               unitset.add(new unit(y, x, 0, 200, 15));
            }
         }
      }
      int originalelfs = numelfs;
      int ticks = 0;
      while ((numgoblins != 0) && (numelfs != 0)) {
         TreeSet<unit> movedunitset = new TreeSet<unit>();
         while (unitset.size() > 0) {
            unit curr = unitset.pollFirst();
            int moved = 0;
            TreeSet<unit> targetset = new TreeSet<unit>();
            for (unit u: unitset) if (u.type != curr.type) targetset.add(u);
            for (unit u: movedunitset) if (u.type != curr.type) targetset.add(u);
            TreeSet<unit> adjtargetset = new TreeSet<unit>();
            for (unit u: targetset) {//see if can attack
               if (u.unitIsNextTo(curr.y, curr.x) == 1) {
                  adjtargetset.add(u);
               }
            }
            if (adjtargetset.size() != 0) {
               int minhp = 201;
               unit target = new unit(9999,9999,0,9999,0);
               for (unit u: adjtargetset) {
                  if (u.hp < target.hp) {
                     target = u;
                  } else if (u.hp == target.hp) {
                     if (target.compareTo(u) > 0) target = u;
                  }
               }
               target.hp -= curr.attack;
               if (target.hp > 0) {
                  pw.printf("unit at %d, %d attacks enemy at %d, %d, reducing hp to %d\n", curr.y, curr.x, target.y, target.x, target.hp);
               } else {
                  map[target.y][target.x] = '.';
                  unitset.remove(target);
                  movedunitset.remove(target);
                  if (target.type == 0) {
                     numelfs--;
                  } else {
                     numgoblins--;
                  }
                  pw.printf("unit at %d, %d attacks enemy at %d, %d, killing it\n", curr.y, curr.x, target.y, target.x);
               }
               moved = 1;
            }
            if (moved == 0) {//find shortest path to squares in range
               char[][] squaresinrange = new char[ydim][xdim];
               for (int y = 0; y < ydim; y++) {
                  for (int x = 0; x < xdim; x++) {
                     squaresinrange[y][x] = map[y][x];
                  }
               }
               int numsqsinrange = 0;
               for (unit u: targetset) {
                  numsqsinrange += u.addSquaresInRange(map, squaresinrange);
               }
               if (numsqsinrange == 0) {
                  pw.printf("no squares in range for unit at %d, %d, no move\n", curr.y, curr.x);
               } else {
                  pw.printf("unit at %d, %d going to move, ", curr.y, curr.x);
                  int[][] steps = new int[ydim][xdim];
                  bfs(map, steps, curr.y, curr.x);
                  int leaststeps = 99999 - 1;
                  intpair closestsquare = new intpair(0, 0);
                  int closestsquarefound = 0;
                  /*for (int y = 0; y < ydim; y++) {
                    for (int x = 0; x < xdim; x++) {
                    pw.printf("%6d ",steps[y][x]);
                    }
                    pw.println();
                    }
                    for (int y = 0; y < ydim; y++) {
                    for (int x = 0; x < xdim; x++) {
                    pw.print(squaresinrange[y][x]);
                    }
                    pw.println();
                    }*/
                  for (int y = ydim - 1; y >= 0; y--) {
                     for (int x = xdim - 1; x >= 0; x--) {
                        if ((y == curr.y) && (x == curr.x)) continue;
                        if ((squaresinrange[y][x] == '!') && (steps[y][x] <= leaststeps)) {
                           closestsquarefound = 1;
                           leaststeps = steps[y][x];
                           closestsquare = new intpair(y, x);
                        }
                     }
                  }
                  if (closestsquarefound == 1) {
                     int[][] stepsfromtarget = new int[ydim][xdim];
                     bfs(map, stepsfromtarget, closestsquare.y, closestsquare.x);
                     int leaststepsnext = 99999;
                     intpair nextmove = new intpair(0,0);
                     if (stepsfromtarget[curr.y+1][curr.x] <= leaststepsnext) {
                        leaststepsnext = stepsfromtarget[curr.y+1][curr.x];
                        nextmove = new intpair(curr.y+1, curr.x);
                     } 
                     if (stepsfromtarget[curr.y][curr.x+1] <= leaststepsnext) {
                        leaststepsnext = stepsfromtarget[curr.y][curr.x+1];
                        nextmove = new intpair(curr.y, curr.x+1);
                     }
                     if (stepsfromtarget[curr.y][curr.x-1] <= leaststepsnext) {
                        leaststepsnext = stepsfromtarget[curr.y][curr.x-1];
                        nextmove = new intpair(curr.y, curr.x-1);
                     }
                     if (stepsfromtarget[curr.y-1][curr.x] <= leaststepsnext) {
                        leaststepsnext = stepsfromtarget[curr.y-1][curr.x];
                        nextmove = new intpair(curr.y-1, curr.x);
                     }
                     pw.printf("unit at %d, %d moves to %d, %d toward target sq at %d, %d\n", curr.y, curr.x, nextmove.y, nextmove.x, closestsquare.y, closestsquare.x);
                     map[nextmove.y][nextmove.x] = map[curr.y][curr.x];
                     map[curr.y][curr.x] = '.';
                     curr.x = nextmove.x; curr.y = nextmove.y;
                     for (unit u: targetset) {//see if can attack
                        if (u.unitIsNextTo(curr.y, curr.x) == 1) {
                           adjtargetset.add(u);
                        }
                     }
                     if (adjtargetset.size() != 0) {
                        int minhp = 201;
                        unit target = new unit(9999,9999,0,9999,0);
                        for (unit u: adjtargetset) {
                           if (u.hp < target.hp) {
                              target = u;
                           } else if (u.hp == target.hp) {
                              if (target.compareTo(u) > 0) target = u;
                           }
                        }
                        target.hp -= curr.attack;
                        if (target.hp > 0) {
                           pw.printf("unit at %d, %d attacks enemy at %d, %d, reducing hp to %d\n", curr.y, curr.x, target.y, target.x, target.hp);
                        } else {
                           map[target.y][target.x] = '.';
                           unitset.remove(target);
                           movedunitset.remove(target);
                           if (target.type == 0) {
                              numelfs--;
                           } else {
                              numgoblins--;
                           }
                           pw.printf("unit at %d, %d attacks enemy at %d, %d, killing it\n", curr.y, curr.x, target.y, target.x);
                        }
                        moved = 1;
                     }
                  } else {
                     pw.printf("closest square not found for target sq at %d, %d, no move\n", closestsquare.y, closestsquare.x);
                  }
               }
            }
            movedunitset.add(curr);
         }
         unitset.addAll(movedunitset);
         for (int y = 0; y < ydim; y++) {
            for (int x = 0; x < xdim; x++) {
               pw.print(map[y][x]);
            }
            pw.println();
         }
         for (unit u: unitset) {
            pw.printf("Unit type %d at %d, %d has hp %d\n", u.type, u.y, u.x, u.hp);
         }
         ticks++;
         pw.printf("numelfs: %d, numgobs: %d after %d rounds, original elfs was %d\n", numelfs, numgoblins, ticks, originalelfs);
      }
      int totalhp = 0;
      for (unit u: unitset) {
         totalhp += u.hp;
      }
      pw.printf("total hp remaining is %d\n", totalhp);
      pw.printf("product is %d\n", totalhp * (ticks - 1));
      pw.close();
   }
   public static void copyArray(char[][] target, char[][] destination) {
      return;
   }
   public static void bfs(char[][] map, int[][] steps, int starty, int startx) {
      for (int y = 0; y < steps.length; y++) {//'clear' the array to maximum values
         for (int x = 0; x < steps[y].length; x++) {
            steps[y][x] = 99999;
         }
      }
      LinkedList<intpair> queue = new LinkedList<intpair>();
      steps[starty][startx] = 0;
      queue.add(new intpair(starty, startx));
      while (queue.size() != 0) {
         intpair curr = queue.pollFirst();
         if ((map[curr.y+1][curr.x] == '.') && (steps[curr.y+1][curr.x] > steps[curr.y][curr.x] + 1)) {
            steps[curr.y+1][curr.x] = steps[curr.y][curr.x] + 1;
            queue.add(new intpair(curr.y + 1, curr.x));
         } 
         if ((map[curr.y-1][curr.x] == '.') && (steps[curr.y-1][curr.x] > steps[curr.y][curr.x] + 1)) {
            steps[curr.y-1][curr.x] = steps[curr.y][curr.x] + 1;
            queue.add(new intpair(curr.y - 1, curr.x));
         } 
         if ((map[curr.y][curr.x+1] == '.') && (steps[curr.y][curr.x+1] > steps[curr.y][curr.x] + 1)) {
            steps[curr.y][curr.x+1] = steps[curr.y][curr.x] + 1;
            queue.add(new intpair(curr.y, curr.x + 1));
         } 
         if ((map[curr.y][curr.x-1] == '.') && (steps[curr.y][curr.x-1] > steps[curr.y][curr.x] + 1)) {
            steps[curr.y][curr.x-1] = steps[curr.y][curr.x] + 1;
            queue.add(new intpair(curr.y, curr.x - 1));
         }
      }
      return;
   }
}
class unit implements Comparable<unit> {
   int y;
   int x;
   int type;//0 is elf, 1 is goblin
   int hp;//starts w/ 200 hp
   int attack;//3 atk for all
   public unit(int a, int b, int c, int d, int e) {
      y = a; x = b; type = c; hp = d; attack = e;
   }
   public int compareTo(unit other) {
      if (this.y != other.y) return this.y - other.y;
      else return this.x - other.x;
   }
   public int unitIsNextTo(int i, int j) {
      if ((this.y == i) && ((this.x == j + 1) || (this.x == j - 1))) {
         return 1;
      } else if ((this.x == j) && ((this.y == i + 1) || (this.y == i - 1))) {
         return 1;
      } else {
         return 0;
      }
   }
   public int addSquaresInRange(char[][] board, char[][] squaresinrange) {
      int added = 0;
      if (board[y+1][x] == '.') {
         squaresinrange[y+1][x] = '!';
         added++;
      }
      if (board[y-1][x] == '.') {
         squaresinrange[y-1][x] = '!';
         added++;
      }
      if (board[y][x+1] == '.') {
         squaresinrange[y][x+1] = '!';
         added++;
      }
      if (board[y][x-1] == '.') {
         squaresinrange[y][x-1] = '!';
         added++;
      }
      return added;
   }
}
class intpair {
   int y;
   int x;
   public intpair(int a, int b) {
      y = a; x = b;
   }
}
