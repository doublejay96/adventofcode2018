import java.io.*;
import java.util.*;
class day12 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      String[] inits = br.readLine().split(" ");
      int initlength = inits[2].length();
      char[] initstatechar = inits[2].toCharArray();
      char[] initstate = new char[initlength+200];
      for (int i = 0; i < initstate.length; i++) initstate[i] = '.';
      for (int i = 0; i < initstatechar.length; i++) {
         if (initstatechar[i] == '#') initstate[i+10] = '#';
      }
      br.readLine();
      TreeMap<String, Character> rules = new TreeMap<String, Character>();
      String input = br.readLine();
      while (input != null) {
         String[] ruleformat = input.split(" ");
         char result = ruleformat[2].charAt(0);
         rules.put(ruleformat[0], result);
         input = br.readLine();
      }
      /*for (String s:rules.keySet()) {
         pw.print(s);
         pw.print(" ");
         pw.println(rules.get(s));
      }*/
      char[] currstate = initstate;
      for (int i = 0; i < currstate.length; i++) pw.print(currstate[i]);
      pw.println();
      int prevsum = 0, constdiff = 0, lastshowngen = 190;
      for (int gen = 1; gen <= lastshowngen; gen++) {
         char[] nextstate = new char[currstate.length];
         for (int i = 0; i < currstate.length; i++) nextstate[i] = currstate[i];
         for (int i = 2; i < currstate.length-2; i++) {
            char[] surround = new char[5];
            for (int j = 0; j < 5; j++) {
               surround[j] = currstate[i-2+j];
            }
            String surroundstring = new String(surround);
            char c = (char) rules.get(surroundstring);
            nextstate[i] = c;
         }
         pw.printf("%3d", gen);
         for (int i = 0; i < currstate.length; i++) currstate[i] = nextstate[i];
         for (int i = 0; i < currstate.length; i++) pw.print(currstate[i]);
         int sum = 0;
         for (int i = 0; i < currstate.length; i++) {
            if (currstate[i] == '#') sum += (i - 10);
         }
         pw.printf("sum is %d\n", sum);
         constdiff = sum - prevsum;
         prevsum = sum;
      }
      long fiftybillionsum = (50000000000l - lastshowngen) * constdiff + prevsum;
      pw.printf("sum at 50 billion is %d\n", fiftybillionsum);
      pw.close();
   }
}
