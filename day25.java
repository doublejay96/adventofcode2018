import java.util.*;
import java.io.*;
class day25 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      String input = br.readLine();
      ArrayList<coords> coordlist = new ArrayList<coords>();
      ArrayList<ArrayList<coords>> constlist = new ArrayList<ArrayList<coords>>();
      while (input != null) {
         String[] numbers = input.split(",");
         int x = Integer.parseInt(numbers[0]);
         int y = Integer.parseInt(numbers[1]);
         int z = Integer.parseInt(numbers[2]);
         int t = Integer.parseInt(numbers[3]);
         coordlist.add(new coords(x, y, z, t));
         input = br.readLine();
      }
      for (coords a: coordlist) {
         ArrayList<Integer> matches = new ArrayList<Integer>();
         for (ArrayList<coords> constellation : constlist) {
            for (coords c : constellation) {
               if (a.distance(c) <= 3) {
                  //pw.printf("const a %d,%d,%d,%d matches %d,%d,%d,%d\n",a.x,a.y,a.z,a.t,c.x,c.y,c.z,c.t);
                  matches.add(constlist.indexOf(constellation));
                  break;
               }
            }
         }
         if (matches.size() == 0) {
            ArrayList<coords> newconst = new ArrayList<coords>();
            newconst.add(a);
            constlist.add(newconst);
            //pw.printf("adding new constellation %d for %d,%d,%d,%d\n", constlist.size() - 1,a.x,a.y,a.z,a.t);  
         } else {
            //if (matches.size() > 1) pw.println("merging constellations");
            //else pw.println("adding to one constellation");
            constlist.get(matches.get(0)).add(a);
            for (int i = 1; i < matches.size(); i++) {
               //pw.printf("adding constellation %d to %d\n", matches.get(i), matches.get(0));
               constlist.get(matches.get(0)).addAll(constlist.get(matches.get(i)));
            }
            for (int i = matches.size() - 1; i > 0; i--) {
               //pw.printf("removing constellation %d\n", matches.get(i));
               constlist.remove((int) matches.get(i));
            }
         }
      }
      pw.printf("number of constellations is %d\n", constlist.size());
      pw.close();
   }
}
class coords {
   int x; int y; int z; int t;
   public coords(int a, int b, int c, int d) {
      x = a; y = b; z = c; t = d;
   }
   public int distance(coords other) {
      return Math.abs(this.x-other.x) + Math.abs(this.y-other.y) + Math.abs(this.z-other.z) + Math.abs(this.t-other.t);
   }
}
