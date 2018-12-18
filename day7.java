import java.util.*;
import java.io.*;
class day7 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int numtasks = 26;
      String input = br.readLine();
      TreeMap<Character, ArrayList<Character>> prereqlists = new TreeMap<Character, ArrayList<Character>>();
      for (int i = 0; i < numtasks; i++) {
         ArrayList<Character> prereqtasks = new ArrayList<Character>();
         char step = (char) ('A' + i);
         prereqlists.put(step, prereqtasks);
      }
      while (input != null) {
         String[] inputs = input.split(" ");
         char prereq = inputs[1].charAt(0);
         char step = inputs[7].charAt(0);
         prereqlists.get(step).add(prereq);
         input = br.readLine();
      }
      TreeMap<Character, ArrayList<Character>> listcopy = new TreeMap<Character, ArrayList<Character>>();
      for (Character c : prereqlists.keySet()) {
         ArrayList<Character> prereqlistcopy = new ArrayList<Character>();
         for (Character d : prereqlists.get(c)) {
            prereqlistcopy.add(d);
         }
         listcopy.put(c, prereqlistcopy);
      }
      int tasksremaining = numtasks;
      int[] tasksdone = new int[26];
      while (tasksremaining != 0) {
         for (int i = 0; i < 26; i++) {
            if (tasksdone[i] > 0) continue;
            Character curr = (char) ('A' + i);
            if (listcopy.get(curr).size() > 0) {
               continue;
            } else {
               for (int j = 0; j < 26; j++) {
                  char next = (char) ('A' + j);
                  listcopy.get(next).remove(curr);
               }
               tasksremaining--;
               tasksdone[i] = 1;
               pw.print(curr);
               break;
            }
         }
      }
      pw.println();
      int[] active = new int[numtasks];
      int numworkers = 5;
      int tasktime = 60;
      int[] workers = new int[numworkers];
      for (int i = 0; i < numworkers; i++) workers[i] = -1;
      int[] done = new int[26];
      int numdone = 0;
      int ticks = 0;
      while (numdone < numtasks) {
         for (int w = 0; w < numworkers; w++) {
            if ((workers[w] != -1) && (active[workers[w]] == workers[w] + 1 + tasktime)) {
               done[workers[w]] = 1;
               numdone++;
               for (int j = 0; j < numtasks; j++) {
                  char next = (char) ('A' + j);
                  Character donetask = (char) ('A' + workers[w]);
                  prereqlists.get(next).remove(donetask);
               }
               workers[w] = -1;
            }
         }
         for (int w = 0; w < numworkers; w++) {
            if (workers[w] == -1) {
               for (int i = 0; i < numtasks; i++) {
                  if (done[i] == 1) continue;
                  if (active[i] > 0) continue;
                  char current = (char) ('A' + i);
                  if (prereqlists.get(current).size() == 0) {
                     workers[w] = i;
                     active[workers[w]] = 1;
                     break;
                  }
               }
            } else {
               active[workers[w]]++;
            }
         }
         /*pw.printf("%3d:  ", ticks);
         for (int w = 0; w < numworkers; w++) {
            pw.printf("%c  ", (char) (workers[w] + 'A'));
         }
         pw.println();*/
         ticks++;
      }
      pw.printf("ticks at end : %d\n", ticks - 1);
      pw.close();
   }
}
