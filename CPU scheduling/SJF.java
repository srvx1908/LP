import java.util.Scanner;
public class SJF {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the no of processes::");
        int n=sc.nextInt();
        int[] AT=new int[n];
        int[] BT=new int[n];
        int[] CT=new int[n];
        boolean[]done=new boolean[n];
        for(int i=0;i<n;i++){
            System.out.print("Enter the Arrival time of process "+(i+1)+"::");
            AT[i]=sc.nextInt();
            System.out.print("Enter the Burst time of process "+(i+1)+"::");
            BT[i]=sc.nextInt();
        }
        int time=0;
        int completed=0;
        while(completed<n){
            int minBT=Integer.MAX_VALUE;
            int index=-1;
            
            for(int i=0;i<n;i++){
                if(!done[i] & AT[i]<=time &BT[i]<minBT){
                    minBT=BT[i];
                    index=i;
                }
            }
            if(index!=-1){
                time=time+BT[index];
                CT[index]=time;
                completed++;
                done[index]=true;
            }
            else{
                time++;
            }
        }
        int[] TAT=new int[n];
        int[] WT=new int[n];
        for(int i=0;i<n;i++){
            TAT[i]=CT[i]-AT[i];
            WT[i]=TAT[i]-BT[i];
        }
        System.out.println("\npid\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t" + AT[i] + "\t" + BT[i] + "\t" + CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
        }
    }
}
