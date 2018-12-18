import java.io.*;
import java.util.*;
import java.text.*;
class day4 {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PrintWriter pw = new PrintWriter(System.out);
   public static void main(String[] args) throws Exception {
      String input = br.readLine();
      ArrayList<event> eventlist = new ArrayList<event>();
      while (input != null) {
         String date = input.substring(1,17);
         event newevent = new event();
         newevent.date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
         newevent.minute = Integer.parseInt(input.substring(15,17));
         char a = input.charAt(19);
         if (a == 'G') {
            String[] guard = input.substring(26,30).split(" ");
            newevent.event = Integer.parseInt(guard[0]);
         } else if (a == 'f') {
            newevent.event = -1;
         } else if (a == 'w') {
            newevent.event = -2;
         }
         newevent.datestring = date;
         //newevent.event = input.substring(18);
         eventlist.add(newevent);
         input = br.readLine();
      }
      Collections.sort(eventlist);
      ArrayList<int[]> sleeps = new ArrayList<int[]>();
      int i = -1;
      for (int x = 0; x < eventlist.size(); x++) {
         event e = eventlist.get(x);
         if (e.event > 0) {
            int[] hour = new int[61];
            hour[60] = e.event;
            i++;
            sleeps.add(hour);
         } else if (e.event == -1) {
            int ending = eventlist.get(x+1).minute - 1;
            for (int y = e.minute; y <= ending; y++) {
               sleeps.get(i)[y] = 1;
            }
         }
         //pw.print(e.datestring); pw.print(" "); pw.println(e.event);
      }
      HashMap<Integer, Integer> guardsleep = new HashMap<Integer,Integer>();
      int maxsleep = 0;
      int sleepyguard = 0;
      for (int[] hour:sleeps) {
         int key = hour[60];
         int sleeptime = 0;
         for (int x = 0; x < 60; x++) {
            if (hour[x] == 1) sleeptime++;
         }
         if (guardsleep.containsKey(key)) {
            sleeptime += guardsleep.get(key);
         }
         if (sleeptime > maxsleep) {
            sleepyguard = key;
            maxsleep = sleeptime;
         }
         guardsleep.put(key, sleeptime);
      }
      int[] naptimes = new int[60];
      for (int[] hour:sleeps) {
         if (hour[60] != sleepyguard) continue;
         for (int x = 0; x < 60; x++) {
            if (hour[x] == 1) naptimes[x]++;
         }
      }
      int sleepiesttime = 0;
      for (int x = 0; x < 60; x++) {
         if (naptimes[sleepiesttime] < naptimes[x]) sleepiesttime = x;
      }
      pw.printf("strat1: sleepiest guard is %d, most likely min is %d, product is %d\n", sleepyguard, sleepiesttime, (sleepyguard*sleepiesttime));
      HashMap<Integer, int[]> guardsleepfreqs = new HashMap<Integer, int[]>();
      for (int[] hour:sleeps) {
         int key = hour[60];
         int[] sleepfreqs = new int[60];
         if (guardsleepfreqs.containsKey(key)) {
            int[] prevfreqs = guardsleepfreqs.get(key);
            for (int x = 0; x < 60; x++) {
              sleepfreqs[x] = hour[x] + prevfreqs[x];
            }
         } else {
            for (int x = 0; x< 60; x++) {
               sleepfreqs[x] = hour[x];
            }
         }
         guardsleepfreqs.put(key, sleepfreqs);
      }
      int highestsleepfreq = 0;
      int sneak = 0;
      int sneakguard = 0;
      for (Integer k: guardsleepfreqs.keySet()) {
         int[] sleepfreqs = guardsleepfreqs.get(k);
         for (int x = 0; x < 60; x++) {
            if (sleepfreqs[x] > highestsleepfreq) {
               highestsleepfreq = sleepfreqs[x];
               sneak = x;
               sneakguard = k;
            }
         }
      }
      pw.printf("strat2: minute is %d, guard is %d, product is %d\n",sneak,sneakguard, sneakguard*sneak);
      /*for (int[] hour:sleeps) {
            for (int x = 0; x< 60; x++) {
               pw.print(hour[x]);
            }
            pw.printf(" "); pw.println(hour[60]);
      }*/
      pw.close();
   }
}
class event implements Comparable<event> {
   Date date;
   String datestring;
   int event;
   int minute;
   public int getevent() {
      return event;
   }
   @Override
   public int compareTo(event e) {
      return this.date.compareTo(e.date);
   }
}
