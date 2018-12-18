import java.util.*;
import java.io.*;
class day16 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int threeormore = 0;
      ArrayList<inst> instarr = new ArrayList<inst>();
      String input = br.readLine();
      while (input.length() > 2) {
         String[] beforeregs = input.substring(9,19).split(", ");
         int[] beforereg = new int[4];
         for (int i = 0; i < 4; i++) beforereg[i] = Integer.parseInt(beforeregs[i]);
         String[] instructions = br.readLine().split(" ");
         int[] instruction = new int[4];
         for (int i = 0; i < 4; i++) instruction[i] = Integer.parseInt(instructions[i]);
         String[] afterregs = br.readLine().substring(9,19).split(", ");
         int[] afterreg = new int[4];
         for (int i = 0; i < 4; i++) afterreg[i] = Integer.parseInt(afterregs[i]);
         inst inputinst = new inst(beforereg, instruction, afterreg);
         instarr.add(inputinst);
         br.readLine();
         input = br.readLine();
      }
      int[][] opcodetonum = new int[16][16];
      for (int i = 0; i < 16; i++) {
         for (int j = 0; j < 16; j++) {
            opcodetonum[i][j] = 1;
         }
      }
      for (inst in: instarr) {
         int like = 0;
         int[] beforereg = in.beforereg;
         int[] afterreg = in.afterreg;
         int[] instruction = in.instruction;
         if (afterreg[instruction[3]] == beforereg[instruction[1]] + beforereg[instruction[2]]) like++;
         else opcodetonum[0][instruction[0]] = 0;
         if (afterreg[instruction[3]] == beforereg[instruction[1]] + instruction[2]) like++;
         else opcodetonum[1][instruction[0]] = 0;
         if (afterreg[instruction[3]] == beforereg[instruction[1]] * beforereg[instruction[2]]) like++;
         else opcodetonum[2][instruction[0]] = 0;
         if (afterreg[instruction[3]] == beforereg[instruction[1]] * instruction[2]) like++;
         else opcodetonum[3][instruction[0]] = 0;
         if (afterreg[instruction[3]] == (beforereg[instruction[1]] & beforereg[instruction[2]])) like++;
         else opcodetonum[4][instruction[0]] = 0;
         if (afterreg[instruction[3]] == (beforereg[instruction[1]] & instruction[2])) like++;
         else opcodetonum[5][instruction[0]] = 0;
         if (afterreg[instruction[3]] == (beforereg[instruction[1]] | beforereg[instruction[2]])) like++;
         else opcodetonum[6][instruction[0]] = 0;
         if (afterreg[instruction[3]] == (beforereg[instruction[1]] | instruction[2])) like++;
         else opcodetonum[7][instruction[0]] = 0;
         if (afterreg[instruction[3]] == beforereg[instruction[1]]) like++;
         else opcodetonum[8][instruction[0]] = 0;
         if (afterreg[instruction[3]] == instruction[1]) like++;
         else opcodetonum[9][instruction[0]] = 0;
         if (afterreg[instruction[3]] == ((instruction[1] > beforereg[instruction[2]])? 1 : 0)) like++;
         else opcodetonum[10][instruction[0]] = 0;
         if (afterreg[instruction[3]] == ((beforereg[instruction[1]] > instruction[2])? 1 : 0)) like++;
         else opcodetonum[11][instruction[0]] = 0;
         if (afterreg[instruction[3]] == ((beforereg[instruction[1]] > beforereg[instruction[2]])? 1 : 0)) like++;
         else opcodetonum[12][instruction[0]] = 0;
         if (afterreg[instruction[3]] == ((instruction[1] == beforereg[instruction[2]])? 1 : 0)) like++;
         else opcodetonum[13][instruction[0]] = 0;
         if (afterreg[instruction[3]] == ((beforereg[instruction[1]] == instruction[2])? 1 : 0)) like++;
         else opcodetonum[14][instruction[0]] = 0;
         if (afterreg[instruction[3]] == ((beforereg[instruction[1]] == beforereg[instruction[2]])? 1 : 0)) like++;
         else opcodetonum[15][instruction[0]] = 0;
         if (like >= 3) threeormore++;
      }
      pw.printf("samples behaving like 3 or more: %d\n", threeormore);
      for (int i = 0; i < 16; i++) {
         for (int j = 0; j < 16; j++) {
            pw.print(opcodetonum[i][j]);
         }
         pw.println();
      }
      for (int times = 0; times < 5; times++) {
         for (int i = 0; i < 16; i++) {
            int ones = 0;
            int onenumber = 0;
            for (int j = 0; j < 16; j++) {
               if(opcodetonum[i][j] == 1) {
                  ones++;
                  onenumber = j;
               }
            }
            if (ones == 1) {
               for (int k = 0; k < 16; k++) {
                  if (k == i) continue;
                  opcodetonum[k][onenumber] = 0;
               }
            }
         }
      }
      pw.println();
      for (int i = 0; i < 16; i++) {
         for (int j = 0; j < 16; j++) {
            pw.print(opcodetonum[i][j]);
         }
         pw.println();
      }
      pw.println();
      TreeMap<Integer, Integer> numtoopcode = new TreeMap<Integer,Integer>();
      for (int i = 0; i < 16; i++) {
         for (int j = 0; j < 16; j++) {
            if (opcodetonum[i][j] == 1) numtoopcode.put(j, i);
         }
      }
      br.readLine();
      input = br.readLine();
      int[] reg = new int [4];
      while (input != null) {
         String[] instructions = input.split(" ");
         int[] instruction = new int[4];
         for (int i = 0; i < 4; i++) instruction[i] = Integer.parseInt(instructions[i]);
         int opcode = numtoopcode.get(instruction[0]);
         if (opcode == 0) reg[instruction[3]] = reg[instruction[1]] + reg[instruction[2]];
         if (opcode == 1) reg[instruction[3]] = reg[instruction[1]] + instruction[2];
         if (opcode == 2) reg[instruction[3]] = reg[instruction[1]] * reg[instruction[2]];
         if (opcode == 3) reg[instruction[3]] = reg[instruction[1]] * instruction[2];
         if (opcode == 4) reg[instruction[3]] = (reg[instruction[1]] & reg[instruction[2]]);
         if (opcode == 5) reg[instruction[3]] = (reg[instruction[1]] & instruction[2]);
         if (opcode == 6) reg[instruction[3]] = (reg[instruction[1]] | reg[instruction[2]]);
         if (opcode == 7) reg[instruction[3]] = (reg[instruction[1]] | instruction[2]);
         if (opcode == 8) reg[instruction[3]] = reg[instruction[1]];
         if (opcode == 9) reg[instruction[3]] = instruction[1];
         if (opcode == 10) reg[instruction[3]] = ((instruction[1] > reg[instruction[2]])? 1 : 0);
         if (opcode == 11) reg[instruction[3]] = ((reg[instruction[1]] > instruction[2])? 1 : 0);
         if (opcode == 12) reg[instruction[3]] = ((reg[instruction[1]] > reg[instruction[2]])? 1 : 0);
         if (opcode == 13) reg[instruction[3]] = ((instruction[1] == reg[instruction[2]])? 1 : 0);
         if (opcode == 14) reg[instruction[3]] = ((reg[instruction[1]] == instruction[2])? 1 : 0);
         if (opcode == 15) reg[instruction[3]] = ((reg[instruction[1]] == reg[instruction[2]])? 1 : 0);
         input = br.readLine();
      }
      pw.println("contents of registers at the end of sample");
      for (int i = 0; i < 4; i++) pw.printf("%d ",reg[i]);
      pw.println();
      pw.close();
   }
}
class inst {
   int[] beforereg;
   int[] instruction;
   int[] afterreg;
   public inst(int[] a, int[] b, int[] c) {
      beforereg = a;
      instruction = b;
      afterreg = c;
   }
}
