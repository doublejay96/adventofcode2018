import java.io.*;
import java.util.*;
class day1 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int frequency = 0;
      TreeSet<Integer> prevfreq = new TreeSet<Integer>();
      ArrayList<Integer> list = new ArrayList<Integer>();
      int repeated = 0;
      Boolean repeat = false;
      String input = br.readLine();
      while (input != null) {
         list.add(Integer.parseInt(input));
         input = br.readLine();
      }
      int size = list.size();
      prevfreq.add(0);
      for (int i = 0; !repeat; i++) {
         frequency += list.get(i%size);
         if (!prevfreq.add(frequency)) {
            repeated = frequency;
            repeat = true;
         }
      }
      pw.printf("repeated frequency is %d\n", repeated);
      pw.close();
   }
}
