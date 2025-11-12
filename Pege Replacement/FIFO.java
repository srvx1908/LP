import java.util.Scanner;
public class FIFO {
   public static void main(String[]args)
   {
        int hit=0;
        int index=0;
        int fault=0;
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the no. of frames:"); 
        int f=sc.nextInt();
        System.out.print("Enter the size of string:"); 
        int n=sc.nextInt();
        int[]pages=new int[n];
        int[]frame=new int[f];
        System.out.println("Enter the string");
        for (int i=0;i<n;i++){
            pages[i]=sc.nextInt();
        }
        for(int i=0;i<f;i++){
            frame[i]=-1;
        }
        for(int i=0;i<n;i++){
            boolean check_hit=false;
            for(int j=0;j<f;j++){
                if(frame[j]==pages[i]){
                    hit++;
                    check_hit=true;
                    break;
                }
            }
            if(!check_hit){
                    fault++;
                    boolean check=false;
                    for(int j=0;j<f;j++){
                        if(frame[j]==-1){
                            frame[j]=pages[i];
                            check=true;
                            break;
                        }
                    }
                    if(!check){
                        
                            frame[index]=pages[i];
                            index = (index + 1) % f;
                        
                    }
                }
            for(int j=0;j<f;j++){
                System.out.print(frame[j]+" ");
            }
            System.out.println();
        }
        System.out.println("page hit"+hit);
        System.out.println("page fault"+fault);
   }
}
