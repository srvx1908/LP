import java.util.Scanner;
public class Priority {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] pid = new int[n];
        int[] AT = new int[n];
        int[] BT = new int[n];
        int[] PR = new int[n];
        int[] CT = new int[n];
        int[] TAT = new int[n];
        int[] WT = new int[n];
        boolean[] done = new boolean[n];

        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.print("Enter Arrival Time of Process " + pid[i] + ": ");
            AT[i] = sc.nextInt();
            System.out.print("Enter Burst Time of Process " + pid[i] + ": ");
            BT[i] = sc.nextInt();
            System.out.print("Enter Priority of Process " + pid[i] + ": ");
            PR[i] = sc.nextInt();
        }

        int time = 0, completed = 0;

        while (completed < n) {
            int idx = -1;
            int highest = Integer.MAX_VALUE; // lower = higher priority

            // Find highest priority process among arrived
            for (int i = 0; i < n; i++) {
                if (AT[i] <= time && !done[i]) {
                    if (PR[i] < highest) {
                        highest = PR[i];
                        idx = i;
                    }
                }
            }

            if (idx == -1) {
                time++; // No process has arrived yet
            } else {
                time += BT[idx];
                CT[idx] = time;
                done[idx] = true;
                completed++;
            }
        }

        // Calculate TAT and WT
        for (int i = 0; i < n; i++) {
            TAT[i] = CT[i] - AT[i];
            WT[i] = TAT[i] - BT[i];
        }

        // Print table
        System.out.println("\nPID\tAT\tBT\tPR\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println(pid[i] + "\t" + AT[i] + "\t" + BT[i] + "\t" + PR[i] +
                               "\t" + CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
        }

        sc.close();
    }
}

