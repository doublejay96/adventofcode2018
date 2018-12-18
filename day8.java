import java.util.*;
import java.io.*;
class day8 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      StringTokenizer st = new StringTokenizer(br.readLine(), " ");
      Stack<Integer> childnumbers = new Stack<Integer>();
      Stack<Integer> metadatanumbers = new Stack<Integer>();
      Stack<ArrayList<Integer>> childnodevalues = new Stack<ArrayList<Integer>>();
      Stack<Integer> originalchildnos = new Stack<Integer>();
      int metadatasum = 0;
      int childremaining = Integer.parseInt(st.nextToken());
      int originalchildno = childremaining;
      int metadataremaining = Integer.parseInt(st.nextToken());
      ArrayList<Integer> subvalues = new ArrayList<Integer>();
      do {
         while (childremaining != 0) {
            childnumbers.push(childremaining);
            metadatanumbers.push(metadataremaining);
            originalchildnos.push(originalchildno);
            childnodevalues.push(subvalues);
            childremaining = Integer.parseInt(st.nextToken());
            originalchildno = childremaining;
            metadataremaining = Integer.parseInt(st.nextToken());
            subvalues = new ArrayList<Integer>();
            //pw.printf("pushing childno %d and MDno %d, new childno is %d, new MDno is %d, original childno is %d\n", childnumbers.peek(), metadatanumbers.peek(), childremaining, metadataremaining, originalchildno);
         }
         int nodevalue = 0;
         while (metadataremaining != 0) {
            int metadata = Integer.parseInt(st.nextToken());
            metadatasum += metadata;
            if (originalchildno == 0) {
               nodevalue += metadata;
            } else {
               //pw.printf("metadata is %d\n", metadata);
               if ((metadata != 0) && (metadata - 1 < subvalues.size())) {
                  //pw.printf("addding child value %d\n", subvalues.get(metadata - 1));
                  nodevalue += subvalues.get(metadata - 1);
               }
            }
            metadataremaining--;
            //pw.printf("sum is now %d, remaining MD is %d, nodevalue is %d\n", metadatasum, metadataremaining, nodevalue);
         }
         childremaining = childnumbers.pop();
         metadataremaining = metadatanumbers.pop();
         originalchildno = originalchildnos.pop();
         subvalues = childnodevalues.pop();
         subvalues.add(nodevalue);
         //pw.printf("popping new childnos %d and MDno %d, new originalchildno %d, added subnode value %d\n", childremaining, metadataremaining, originalchildno, nodevalue);
         //for (Integer i : subvalues) pw.printf("%d ", i);
         //pw.println();
         childremaining--;
      } while (!childnumbers.empty() || childremaining != 0);
      int nodevalue = 0;//root node calc
      while (metadataremaining != 0) {
         int metadata = Integer.parseInt(st.nextToken());
         metadatasum += metadata;
         if (originalchildno == 0) {
            nodevalue += metadata;
         } else {
            //pw.printf("metadata is %d\n", metadata);
            if ((metadata != 0) && (metadata - 1 < subvalues.size())) {
               //pw.printf("addding child value %d\n", subvalues.get(metadata - 1));
               nodevalue += subvalues.get(metadata - 1);
            }
         }
         metadataremaining--;
         //pw.printf("sum is now %d, remaining MD is %d, nodevalue is %d\n", metadatasum, metadataremaining, nodevalue);
      }
      pw.printf("metadatasum is %d\n", metadatasum);
      pw.printf("root node value is %d\n", nodevalue);
      pw.close();
   }
}
