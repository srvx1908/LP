import java.util.Scanner;

public class LRU {
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
        LRU2(page, f);
    }

    public static void LRU2(int[] page, int f) {
        int[] age = new int[f];
        int[] frame = new int[f];
        int hit = 0, fault = 0;

        for (int i = 0; i < f; i++) {
            frame[i] = -1;
            age[i] = 0;
        }

        for (int i = 0; i < page.length; i++) {
            boolean check_hit = false;

            
            for (int j = 0; j < f; j++) {
                if (frame[j] == page[i]) {
                    hit++;
                    check_hit = true;
                    age[j] = i + 1;
                    break;
                }
            }

            
            if (!check_hit) {
                fault++;
                int extraindex = -1;

                
                for (int j = 0; j < f; j++) {
                    if (frame[j] == -1) {
                        extraindex = j;
                        break;
                    }
                }

                
                if (extraindex == -1) {
                    extraindex = 0;
                    int minage = age[0];
                    for (int j = 1; j < f; j++) { 
                        if (age[j] < minage) {
                            minage = age[j];
                            extraindex = j;
                        }
                    }
                }

                frame[extraindex] = page[i];
                age[extraindex] = i + 1;
            }

            // Show frame contents each time
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
