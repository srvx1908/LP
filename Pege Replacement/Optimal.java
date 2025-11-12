import java.util.Scanner;

public class Optimal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the no. frames: ");
        int f = sc.nextInt();
        System.out.print("Enter the no. pages: ");
        int n = sc.nextInt();
        int[] page = new int[n];
        System.out.println("Enter the string:");
        for (int i = 0; i < n; i++) {
            page[i] = sc.nextInt();
        }
        optimal(page, f);
    }

    public static void optimal(int[] page, int f) {
        int[] frame = new int[f];
        int hit = 0, fault = 0;

        for (int i = 0; i < f; i++) {
            frame[i] = -1;
        }

        for (int i = 0; i < page.length; i++) {
            boolean check_hit = false;

            // Check for page hit
            for (int j = 0; j < f; j++) {
                if (frame[j] == page[i]) {
                    hit++;
                    check_hit = true;
                    break;
                }
            }

            // On miss
            if (!check_hit) {
                fault++;
                int extraindex = -1;

                // Find empty frame
                for (int j = 0; j < f; j++) {
                    if (frame[j] == -1) {
                        extraindex = j;
                        break;
                    }
                }

                // If no empty frame, apply optimal logic
                if (extraindex == -1) {
                    int farthest = -1;
                    int replaceIndex = -1;

                    for (int j = 0; j < f; j++) {
                        int k;
                        for (k = i + 1; k < page.length; k++) {
                            if (frame[j] == page[k]) {
                                break;
                            }
                        }

                        // Page not used again
                        if (k == page.length) {
                            replaceIndex = j;
                            break;
                        }

                        // Find farthest future use
                        if (k > farthest) {
                            farthest = k;
                            replaceIndex = j;
                        }
                    }
                    extraindex = replaceIndex;
                }

                frame[extraindex] = page[i];
            }

            // Show frames after each reference
            System.out.print("After page " + page[i] + " → ");
            for (int j = 0; j < f; j++) {
                if (frame[j] != -1)
                    System.out.print(frame[j] + " ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }

        System.out.println("\nPage Hit: " + hit);
        System.out.println("Page Fault: " + fault);
    }
}
