import java.util.*;
import java.io.*;
class day5 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      LinkedList<Integer> polymer = new LinkedList<Integer>();
      int input = br.read();
      while ((char) input != '\n') {
         polymer.addLast(input);
         input = br.read();
      }
      int shortestpolymer = polymer.size();
      pw.println(polymer.size());
      Stack<Integer> after = new Stack<Integer>();
      Stack<Integer> before = new Stack<Integer>();
      Iterator it = polymer.descendingIterator();
      while (it.hasNext()) {
         after.push((int) it.next());
      }
      while (!after.empty()) {
         if (before.empty()) {
            before.push(after.pop());
         } else if ((after.peek() == before.peek() + 32) || (after.peek() == before.peek() - 32)) {
            after.pop();
            before.pop();
         } else {
            before.push(after.pop());
         }
      }
      pw.printf("size after rxn is %d\n",before.size());
      for (int i = 0; i < 26; i++) {
         after = new Stack<Integer>();
         before = new Stack<Integer>();
         it = polymer.descendingIterator();
         while (it.hasNext()) {
            after.push((int) it.next());
         }
         while (!after.empty()) {
            if ((after.peek() == 'A' + i) || (after.peek() == 'A' + i + 32)) {
               after.pop();
            } else if (before.empty()) {
               before.push(after.pop());
            } else if ((after.peek() == before.peek() + 32) || (after.peek() == before.peek() - 32)) {
               after.pop();
               before.pop();
            } else {
               before.push(after.pop());
            }
         }
         if (before.size() < shortestpolymer) {
            shortestpolymer = before.size();
            pw.println((char) ('a'+ i));
         }
      }
      pw.printf("shortest polymer is %d\n",shortestpolymer);
      pw.close();
   }
}
