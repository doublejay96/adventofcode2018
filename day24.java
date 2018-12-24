import java.util.*;
import java.io.*;
class day24 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      br.readLine();
      ArrayList<Group> immunesys = new ArrayList<Group>();
      String input = br.readLine();
      int number = 1;
      while (!input.equals("")) {
         String[] input2 = input.replaceAll("\\(.*\\) ","").split(" ");
         int units = Integer.parseInt(input2[0]);
         int hp = Integer.parseInt(input2[4]);
         int atkdmg = Integer.parseInt(input2[12]);
         String atktype = input2[13];
         int init = Integer.parseInt(input2[17]);
         String[] weak = {}; 
         String[] immune = {};
         if (input.contains("(")) {
            String[] paren = input.substring(input.indexOf("(")+1, input.indexOf(")")).split("; ");
            for (String s : paren) {
               if (s.startsWith("weak to ")) {
                  weak = s.substring(8).split(", ");
               } else if (s.startsWith("immune to ")) {
                  immune = s.substring(10).split(", ");
               }
            }
         }
         Group inputgroup = new Group(number++, 0, units, hp, atkdmg, atktype, init, weak, immune);
         immunesys.add(inputgroup);
         input = br.readLine();
      }
      br.readLine();
      ArrayList<Group> infect = new ArrayList<Group>();
      input = br.readLine();
      number = 1;
      while (input != null) {
         String[] input2 = input.replaceAll("\\(.*\\) ","").split(" ");
         int units = Integer.parseInt(input2[0]);
         int hp = Integer.parseInt(input2[4]);
         int atkdmg = Integer.parseInt(input2[12]);
         String atktype = input2[13];
         int init = Integer.parseInt(input2[17]);
         String[] weak = {}; 
         String[] immune = {};
         if (input.contains("(")) {
            String[] paren = input.substring(input.indexOf("(")+1, input.indexOf(")")).split("; ");
            for (String s : paren) {
               if (s.startsWith("weak to ")) {
                  weak = s.substring(8).split(", ");
               } else if (s.startsWith("immune to ")) {
                  immune = s.substring(10).split(", ");
               }
            }
         }
         Group inputgroup = new Group(number++, 1, units, hp, atkdmg, atktype, init, weak, immune);
         infect.add(inputgroup);
         input = br.readLine();
      }
      int boost = 0;
      boolean won = false;
      while (!won) {
         ArrayList<Group> immunesyscopy = new ArrayList<Group>();
         ArrayList<Group> infectcopy = new ArrayList<Group>();
         for (Group g: immunesys) immunesyscopy.add(new Group(g.number, g.type, g.units, g.hp, g.atkdmg, g.atktype, g.init, g.weak, g.immune));
         for (Group g: infect) infectcopy.add(new Group(g.number, g.type, g.units, g.hp, g.atkdmg, g.atktype, g.init, g.weak, g.immune));
         for (Group g: immunesyscopy) {
            g.atkdmg += boost;
         }
         int round = 1;
         boolean done = false;
         while (!done) {
            //pw.printf("round %d:\n",round++); 
            Collections.sort(infectcopy); Collections.sort(immunesyscopy);//Java TreeSets do NOT re-sort on element change!! >:(
            TreeMap<Integer, Integer> infectcopytargets = new TreeMap<Integer, Integer>();
            for (Group g: infectcopy) {
               if (g.dead) continue;
               Group isg = null;
               int maxprojdmg = 0;
               for (Group i : immunesyscopy) {
                  if ((infectcopytargets.containsValue(i.number)) || (i.dead)) continue;
                  int projdmg = g.units*g.atkdmg;
                  if (Arrays.asList(i.weak).contains(g.atktype)) projdmg *= 2;
                  else if (Arrays.asList(i.immune).contains(g.atktype)) projdmg = 0;
                  if (projdmg > maxprojdmg) {
                     maxprojdmg = projdmg;
                     isg = i;
                  } else if ((projdmg == maxprojdmg) && (isg != null)) {
                     if (i.compareTo(isg) < 0) isg = i;
                  }
               }
               if (isg != null) {
                  infectcopytargets.put(g.number, isg.number);
                  //pw.printf("infectcopy grp %d targetting immunesyscopy grp %d\n", g.number, isg.number);
               } else {
                  //pw.printf("infectcopy grp %d no targets\n", g.number);
               }
            }
            TreeMap<Integer, Integer> immunesyscopytargets = new TreeMap<Integer, Integer>();
            for (Group g: immunesyscopy) {
               if (g.dead) continue;
               Group isg = null;
               int maxprojdmg = 0;
               for (Group i : infectcopy) {
                  if ((immunesyscopytargets.containsValue(i.number)) || (i.dead)) continue;
                  int projdmg = g.units*g.atkdmg;
                  if (Arrays.asList(i.weak).contains(g.atktype)) projdmg *= 2;
                  else if (Arrays.asList(i.immune).contains(g.atktype)) projdmg = 0;
                  if (projdmg > maxprojdmg) {
                     maxprojdmg = projdmg;
                     isg = i;
                  } else if ((projdmg == maxprojdmg) && (isg != null)) {
                     if (i.compareTo(isg) < 0) isg = i;
                  }
               }
               if (isg != null) {
                  immunesyscopytargets.put(g.number, isg.number);
                  //pw.printf("immunesyscopy grp %d targetting infectcopy grp %d\n", g.number, isg.number);
               } else {
                  //pw.printf("infectcopy grp %d no targets\n", g.number);
               }
            }
            ArrayList<Group> combatorder = new ArrayList<Group>();
            if ((infectcopytargets.size() == 0) && (immunesyscopytargets.size() == 0)) {
               //System.out.println("no targets for all");
               done = true;
            }
            combatorder.addAll(immunesyscopy); combatorder.addAll(infectcopy);
            Collections.sort(combatorder, (g1, g2) -> g2.init - g1.init);
            boolean unitsdamaged = false;
            for (Group g: combatorder) {
               if (g.dead) continue;
               int targetnum = 0;
               if ((g.type == 0) && (immunesyscopytargets.containsKey(g.number))) {
                  targetnum = immunesyscopytargets.get(g.number);
               } else if ((g.type == 1) && (infectcopytargets.containsKey(g.number))) {
                  targetnum = infectcopytargets.get(g.number);
               }
               if (targetnum == 0) {
                  //pw.printf("%d group %d skips its turn\n", g.type, g.number);
                  continue;
               }
               Group target = null;
               for (Group t: combatorder) {
                  if ((t.type != g.type) && (t.number == targetnum)) {
                     int projdmg = g.units*g.atkdmg;
                     if (Arrays.asList(t.weak).contains(g.atktype)) projdmg *= 2;
                     else if (Arrays.asList(t.immune).contains(g.atktype)) projdmg = 0;
                     int unitskilled = (projdmg) / t.hp;
                     if (unitskilled > 0) unitsdamaged = true;
                     if (t.units > unitskilled) {
                        t.units -= unitskilled;
                        //pw.printf("%d group %d attacks opposing group %d, killing %d units, %d remaining\n", g.type,g.number,t.number,unitskilled,t.units);
                     } else {
                        t.dead = true;
                        //pw.printf("%d group %d attacks opposing group %d, killing all %d units\n", g.type,g.number,t.number,t.units);
                        t.units = 0;
                     }
                     break;
                  }
               }
            }
            if (!unitsdamaged) {
               //System.out.println("Stalemate");
               done = true;
            }
            boolean alldead = true;
            for (Group g: immunesyscopy) {
               if (!g.dead) {
                  alldead = false;
                  break;
               }
            }
            if (alldead) {
               if (boost == 0) pw.println("all immunesyscopy dead");
               int unitsum = 0;
               for (Group i : infectcopy) if (!i.dead) unitsum += i.units;
               if (boost == 0) pw.printf("%d units remaining\n", unitsum);
               done = true;
            }
            alldead = true;
            for (Group g: infectcopy) {
               if (!g.dead) {
                  alldead = false;
                  break;
               }
            }
            if (alldead) {
               won = true;
               pw.println("all infectcopy dead");
               int unitsum = 0;
               for (Group i : immunesyscopy) if (!i.dead) unitsum += i.units;
               pw.printf("%d units remaining\n", unitsum);
               done = true;
            }
            //pw.println();
         }
         //System.out.println(boost);
         boost++;
      }
      pw.printf("minimum boost is %d\n", boost - 1);
      pw.close();
   }
}
class Group implements Comparable<Group> {
   int number;
   int type; //0 is immunesys, 1 is infect
   int units;
   int hp;
   int atkdmg;
   String atktype;
   int init;
   String[] weak;
   String[] immune;
   boolean dead;
   public Group(int n, int t, int u, int h, int ad, String at, int i, String[] w, String[] im) {
      number = n; type = t; units = u; hp = h; atkdmg = ad; init = i; dead = false;
      atktype = at; weak = w; immune = im;
   }
   public int compareTo(Group other) {
      int dmgdiff = (other.units*other.atkdmg) - (this.units*this.atkdmg);
      if (dmgdiff != 0) return dmgdiff;
      else return other.init - this.init;
   }
}
