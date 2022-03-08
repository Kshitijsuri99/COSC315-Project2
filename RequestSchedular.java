import java.util.Scanner;

public class RequestSchedular {
    public static void main(String[] args){

        Scanner sc=new Scanner(System.in); 
        
        System.out.println("Enter the number of slave threads to employ:"); 
        int numSlaves = sc.nextInt(); 

        System.out.println("Enter the maxiumum duration of sleep before next request(in milliseconds):"); 
        int sleepDur = sc.nextInt(); 

        System.out.println("Enter the maxiumum duration of a request(in milliseconds):"); 
        int maxDur = sc.nextInt(); 
        
        BoundedBuffer<int[]> sharedBuffer = new BoundedBuffer<>(2);

        MasterThread<int[]> master = new MasterThread<int[]>(sharedBuffer, sleepDur, maxDur);

        for(int i = 0; i < numSlaves; i++){
            SlaveThread<int[]> slave = new SlaveThread<int[]>(sharedBuffer, i + 1);
            slave.start();
        }

        master.start();
    }
}
