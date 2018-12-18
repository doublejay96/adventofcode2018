import java.io.*;
import java.util.*;
class day2 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int twos = 0;
      int threes = 0;
      ArrayList<char[]> boxids = new ArrayList<char[]>();
      String input = br.readLine();
      while (input != null) {
         char[] inputs = input.toCharArray();
         boxids.add(inputs);
         input = br.readLine();
      }
      for (int i = 0; i < boxids.size(); i++) {
         boolean hastwos = false, hasthrees = false;
         int[] charfreqs = new int[26];
         for (char c: boxids.get(i)) {
            charfreqs[c - 'a']++;
         }
         for (int j = 0; j < 26; j++) {
            if (charfreqs[j] == 2) hastwos = true;
            if (charfreqs[j] == 3) hasthrees = true;
         }
         if (hastwos) twos++;
         if (hasthrees) threes++;
         for (int j = i+1; j < boxids.size(); j++) {
            int diff = 0;
            for (int k = 0; k < boxids.get(i).length; k++) {
               if (boxids.get(i)[k] != boxids.get(j)[k]) diff++;
               if (diff > 1) break;
            }
            if (diff == 1) {
               pw.print("Common characters are: ");
               for (int k = 0; k < boxids.get(i).length; k++) {
                  if (boxids.get(i)[k] == boxids.get(j)[k]) pw.print(boxids.get(i)[k]);
               }
               pw.println();
            }
         }
      }
      pw.printf("twos is %d, threes is %d\n", twos, threes);
      pw.printf("product is %d\n", (twos*threes));
      pw.close();
   }
}
