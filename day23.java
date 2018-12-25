import java.util.*;
import java.io.*;
class day23 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      String input = br.readLine();
      ArrayList<nanobot> botlist = new ArrayList<nanobot>();
      int maxx = 0, maxy = 0, maxz = 0, minx = 0, miny = 0, minz = 0;
      while (input != null) {
         String[] tokens = input.substring(5).split(",");
         int x = Integer.parseInt(tokens[0]);
         int y = Integer.parseInt(tokens[1]);
         int z = Integer.parseInt(tokens[2].substring(0, tokens[2].length()-1));
         if (x > maxx) maxx = x;
         if (y > maxy) maxy = y;
         if (z > maxz) maxz = z;
         if (x < minx) minx = x;
         if (y < miny) miny = y;
         if (z < minz) minz = z;
         int radius = Integer.parseInt(tokens[3].substring(3));
         //pw.printf("%d, %d, %d, %d\n", x, y, z, radius);
         botlist.add(new nanobot(x, y, z, radius));
         input = br.readLine();
      }
      //pw.printf("max %d, %d, %d, min %d, %d, %d\n", maxx, maxy, maxz, minx, miny, minz);
      nanobot strongestbot = botlist.get(0);
      for (nanobot n : botlist) {
         if (n.radius > strongestbot.radius) strongestbot = n;
      }
      int numinrange = 0;
      for (nanobot n : botlist) {
         if (n.inRange(strongestbot)) numinrange++;
      }
      pw.printf("number in range of the strongest nanobot %d\n",numinrange);
      PriorityQueue<volumepart> queue = new PriorityQueue<volumepart>();
      int span = Math.max((maxz-minz), Math.max((maxx-minx), (maxy-miny)));
      int largestsidelength = 1;
      while (largestsidelength < span) largestsidelength *= 2; //must be a power of 2 so divisible all the way down
      volumepart whole = new volumepart(new coords(minx, miny, minz), largestsidelength, botlist);
      queue.add(whole);
      while (queue.size() > 0) {//binary search
         volumepart curr = queue.poll();
         int x = curr.corner.x, y = curr.corner.y, z = curr.corner.z;
         //pw.printf("%d, %d, %d with sidelength %d and numbots %d\n", x, y, z, curr.sidelength, curr.numbots);
         if (curr.sidelength <= 1) {
            pw.printf("%d, %d, %d is smallest maximum with %d bots in range\n", x, y, z, curr.numbots);
            pw.printf("distance from origin is %d\n", curr.origindist);
            break;
         }
         int newsidelength = curr.sidelength / 2;
         queue.add(new volumepart(new coords(x, y, z), newsidelength, botlist));
         queue.add(new volumepart(new coords(x + newsidelength, y, z), newsidelength, botlist));
         queue.add(new volumepart(new coords(x, y + newsidelength, z), newsidelength, botlist));
         queue.add(new volumepart(new coords(x, y, z + newsidelength), newsidelength, botlist));
         queue.add(new volumepart(new coords(x + newsidelength, y + newsidelength, z), newsidelength, botlist));
         queue.add(new volumepart(new coords(x + newsidelength, y, z + newsidelength), newsidelength, botlist));
         queue.add(new volumepart(new coords(x, y + newsidelength, z + newsidelength), newsidelength, botlist));
         queue.add(new volumepart(new coords(x + newsidelength, y + newsidelength, z + newsidelength), newsidelength, botlist));
      }
      pw.close();
   }
}
class nanobot {
   int x;
   int y;
   int z;
   int radius;
   public nanobot(int a, int b, int c, int d) {
      x = a; y = b; z = c;
      radius = d;
   }
   public boolean inRange(nanobot other) {
      int distance = Math.abs(this.x - other.x) + Math.abs(this.y - other.y) + Math.abs(this.z - other.z); 
      if (distance <= other.radius) return true;
      else return false;
   }
   public boolean inRange(int a, int b, int c) {
      int distance = Math.abs(this.x - a) + Math.abs(this.y - b) + Math.abs(this.z - c);
      if (distance <= this.radius) return true;
      else return false;
   }
}
class coords implements Comparable<coords> {
   int x; int y; int z;
   public coords(int a, int b, int c) {
      x = a; y = b; z = c;
   }
   public int origindist() {
      return Math.abs(x) + Math.abs(y) + Math.abs(z);
   }
   public int compareTo(coords other) {
      int distown = Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z);
      int distother = Math.abs(other.x) + Math.abs(other.y) + Math.abs(other.z);
      return distown - distother;
   }
}
class volumepart implements Comparable<volumepart> {
   coords corner;//minimum corner, closest to -infinity
   int sidelength;
   int numbots;
   int origindist;
   public volumepart (coords a, int b, ArrayList<nanobot> botlist) {
      this.corner = a;
      this.sidelength = b;
      int botsinrange = 0;
      for (nanobot n : botlist) {
         int xinterval, yinterval, zinterval;
         if ((corner.x <= n.x) && (corner.x + sidelength - 1 >= n.x)) xinterval = 0;
         else xinterval = Math.min(Math.abs(n.x - corner.x - (sidelength - 1)), Math.abs(corner.x - n.x));
         if ((corner.y <= n.y) && (corner.y + sidelength - 1 >= n.y)) yinterval = 0;
         else yinterval = Math.min(Math.abs(n.y - corner.y - (sidelength - 1)), Math.abs(corner.y - n.y));
         if ((corner.z <= n.z) && (corner.z + sidelength - 1 >= n.z)) zinterval = 0;
         else zinterval = Math.min(Math.abs(n.z - corner.z - (sidelength - 1)), Math.abs(corner.z - n.z));
         if (xinterval+yinterval+zinterval <= n.radius) botsinrange++;
      }
      this.numbots = botsinrange;
      int xdist, ydist, zdist;
      if ((corner.x <= 0) && (corner.x + sidelength - 1 >= 0)) xdist = 0;
      else xdist = Math.min(Math.abs(corner.x + sidelength - 1), Math.abs(corner.x));
      if ((corner.y <= 0) && (corner.y + sidelength - 1 >= 0)) ydist = 0;
      else ydist = Math.min(Math.abs(corner.y + sidelength - 1), Math.abs(corner.y));
      if ((corner.z <= 0) && (corner.z + sidelength - 1 >= 0)) zdist = 0;
      else zdist = Math.min(Math.abs(corner.z + sidelength - 1), Math.abs(corner.z));
      this.origindist = xdist+ydist+zdist;
   }
   public int compareTo(volumepart other) {//comparator for PQ sorts by number of bots in range, then distance, then sidelength to terminate asap
      if (this.numbots != other.numbots) return other.numbots - this.numbots;
      else if (this.origindist != other.origindist) return this.origindist - other.origindist;
      else return this.sidelength - other.sidelength;
   }
}
