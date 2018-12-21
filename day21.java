import java.util.*;
import java.io.*;
class day21 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      int inc = 0;
      int reg5 = 0;
      int reg3 = 0;
      int reg2 = 0;
      int reg1 = 0;
      TreeMap<Integer, Integer> reg0valuetoiter = new TreeMap<Integer,Integer>();
      do {
         reg5 = reg3 | 65536;
         reg3 = 5557974;
         reg2 = reg5 & 255;
         reg3 += reg2;
         reg3 &= 16777215;
         reg3 *= 65899; //overflow here
         reg3 &= 16777215; //truncate
         //pw.printf("overflow/truncate is %d\n", reg3);
         while (256 <= reg5) {
            reg2 = 0;
            reg1 = reg2 + 1;
            reg1 *= 256;
            while (reg1 <= reg5) {
               reg2++;
               reg1 = reg2 + 1;
               reg1 *= 256;
            }
            reg1 = 1;
            reg5 = reg2;
            //pw.printf("reg5 is now reg2, %d iterations of loop\n", reg5);
            reg2 = reg5 & 255;
            reg3 += reg2;
            reg3 &= 16777215;
            reg3 *= 65899; //overflow here
            reg3 &= 16777215; //truncate
            //pw.printf("overflow/truncate is %d\n", reg3);
         }
         if (inc == 0) pw.printf("reg0 has to == %d to exit\n", reg3);
         if (!reg0valuetoiter.containsKey(reg3)) {
            reg0valuetoiter.put(reg3, inc);
         }
         inc++;
      } while (inc < 1000000);
      int greatestfirstappearance = 0;
      int associatedreg0value = 0;
      for (Integer I : reg0valuetoiter.keySet()) {
         if (reg0valuetoiter.get(I) > greatestfirstappearance) {
            greatestfirstappearance = reg0valuetoiter.get(I);
            associatedreg0value = I;
         }
      }
      pw.printf("reg0value %d appears latest at %d iterations\n", associatedreg0value, greatestfirstappearance);
      pw.close();
   }
}
