import java.util.*;
import java.io.*;
class day14 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int firstelfpos = 0;
      int secondelfpos = 1;
      int lookat = 503761;
      int size = 100000000;
      ArrayList<Integer> recipescore = new ArrayList<Integer>(size);
      recipescore.add(3);
      recipescore.add(7);
      while (recipescore.size() < size) {
         int firstelf = recipescore.get(firstelfpos);
         int secondelf = recipescore.get(secondelfpos);
         int result = firstelf + secondelf;
         if (result > 9) {
            recipescore.add(result/10);
            recipescore.add(result%10);
         } else {
            recipescore.add(result);
         }
         firstelfpos = (firstelf + 1 + firstelfpos) % recipescore.size();
         secondelfpos = (secondelf + 1 + secondelfpos) % recipescore.size();
      }
      for (int i = 0; i <= recipescore.size() - 6; i++) {
         int result = 0;
         for (int j = 0; j < 6; j++) {
            result = 10*result;
            result += recipescore.get(i+j);
         }
         if (result == lookat) {
            pw.println(i);
            break;
         }
      }
      pw.close();
   }
}
