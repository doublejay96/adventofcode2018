import java.util.*;
import java.io.*;
class day19 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int IP = Integer.parseInt(br.readLine().split(" ")[1]);
      int[] reg = new int[6];
      String input = br.readLine();
      ArrayList<inst> instlist = new ArrayList<inst>();
      while (input != null) {
         String[] tokens = input.split(" ");
         inst inputinst = new inst(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
         instlist.add(inputinst);
         input = br.readLine();
      }
      int inc = 0;
      while (inc < 10000) {
         System.out.printf("reg[IP] is %d, ", reg[IP]);
         if (reg[IP] >= instlist.size()) break;
         inst curr = instlist.get(reg[IP]);
         if (curr.opcode.equals("addr")) reg[curr.c] = reg[curr.a] + reg[curr.b];
         if (curr.opcode.equals("addi")) reg[curr.c] = reg[curr.a] + curr.b;
         if (curr.opcode.equals("mulr")) reg[curr.c] = reg[curr.a] * reg[curr.b];
         if (curr.opcode.equals("muli")) reg[curr.c] = reg[curr.a] * curr.b;
         if (curr.opcode.equals("banr")) reg[curr.c] = reg[curr.a] & reg[curr.b];
         if (curr.opcode.equals("bani")) reg[curr.c] = reg[curr.a] & curr.b;
         if (curr.opcode.equals("borr")) reg[curr.c] = reg[curr.a] | reg[curr.b];
         if (curr.opcode.equals("bori")) reg[curr.c] = reg[curr.a] | curr.b;
         if (curr.opcode.equals("setr")) reg[curr.c] = reg[curr.a];
         if (curr.opcode.equals("seti")) reg[curr.c] = curr.a;
         if (curr.opcode.equals("gtir")) reg[curr.c] = (curr.a > reg[curr.b])? 1 : 0;
         if (curr.opcode.equals("gtri")) reg[curr.c] = (reg[curr.a] > curr.b)? 1 : 0;
         if (curr.opcode.equals("gtrr")) reg[curr.c] = (reg[curr.a] > reg[curr.b])? 1 : 0;
         if (curr.opcode.equals("eqir")) reg[curr.c] = (curr.a == reg[curr.b])? 1 : 0;
         if (curr.opcode.equals("eqri")) reg[curr.c] = (reg[curr.a] == curr.b)? 1 : 0;
         if (curr.opcode.equals("eqrr")) reg[curr.c] = (reg[curr.a] == reg[curr.b])? 1 : 0;
         System.out.printf("registers are now ", reg[IP]);
         for (int i = 0; i < 6; i++) System.out.printf("%d ", reg[i]);
         reg[IP]++;
         System.out.printf("reg[IP] is now %d\n", reg[IP]);
         inc++;
      }
      for (int i = 0; i < 6; i++) pw.printf("%d ", reg[i]);
      pw.println();
      pw.close();
   }
}
class inst {
   String opcode;
   int a;
   int b;
   int c;
   public inst(String opcode, int x, int y, int z) {
      this.opcode = opcode;
      a = x; b = y; c = z;
   }
}
