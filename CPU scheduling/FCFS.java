import java.util.Scanner;
public class FCFS {
    public static void main(String[]args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of processes:");
        int n=sc.nextInt();
        int[]pid=new int[n];
        int[]BT=new int[n];
        int[]AT=new int[n];
        System.out.println("----------Enter the information of process----------");
        for(int i=0;i<n;i++){
            pid[i]=i+1;
            System.out.print("Enter the Arrival time of process "+pid[i]);
            AT[i]=sc.nextInt();
            System.out.print("Enter the Burst time of process "+pid[i]);
            BT[i]=sc.nextInt();
        }
        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
                if(AT[i]>AT[j]){
                    int temp=AT[i];
                    AT[i]=AT[j];
                    AT[j]=temp;
                    int temp1=pid[i];
                    pid[i]=pid[j];
                    pid[j]=temp1;
                    int temp2=BT[i];
                    BT[i]=BT[j];
                    BT[j]=temp2;
                }
            }
        }
        //Completion time
        int[]CT=new int[n];
        CT[0]=AT[0]+BT[0];
        for(int i=1;i<n;i++){
            if(AT[i]>=CT[i-1]){
                CT[i]=AT[i]+BT[i];
            }
            else{
                CT[i]=CT[i-1]+BT[i];
            }
        }
        int[]TAT=new int[n];
        int[]WT=new int[n];
        for(int i=0;i<n;i++){
            TAT[i]=CT[i]-AT[i];
            WT[i]=TAT[i]-BT[i];
        }

        //printint allll
        System.out.println("pid\tAT\tBT\tCT\tTAT\tWT");
        for(int i=0;i<n;i++){
            System.out.print(pid[i]+"\t"+AT[i]+"\t"+BT[i]+"\t"+CT[i]+"\t"+TAT[i]+"\t"+WT[i]);
            System.out.println();
        }
    }
}
