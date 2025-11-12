import java.util.Scanner;

public class RR {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        System.out.print("Enter the Time Quantum: ");
        int tq = sc.nextInt();

        int[] rem = new int[n];
        int[] AT = new int[n];
        int[] BT = new int[n];
        int[] CT = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival time of process " + (i + 1) + ": ");
            AT[i] = sc.nextInt();
            System.out.print("Enter Burst time of process " + (i + 1) + ": ");
            BT[i] = sc.nextInt();
            rem[i] = BT[i];
        }

        int time = 0, completed = 0;

        while (completed < n) {
            boolean executed = false;

            for (int i = 0; i < n; i++) {
                if (AT[i] <= time && rem[i] > 0) {
                    executed = true;

                    if (rem[i] <= tq) {
                        time += rem[i];
                        rem[i] = 0;
                        CT[i] = time;
                        completed++;
                    } else {
                        rem[i] -= tq;
                        time += tq;
                    }
                }
            }

            if (!executed) {
                time++; // CPU idle, no ready process
            }
        }

        // Calculate TAT and WT
        int[] TAT = new int[n];
        int[] WT = new int[n];

        for (int i = 0; i < n; i++) {
            TAT[i] = CT[i] - AT[i];
            WT[i] = TAT[i] - BT[i];
        }

        // Print results
        System.out.println("\npid\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t" + AT[i] + "\t" + BT[i] + "\t" + CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
        }

        sc.close();
    }
}
