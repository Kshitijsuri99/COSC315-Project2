public class RequestSchedular {
    public static void main(String[] args){
        int numSlaves = 2; 
        
        BoundedBuffer<int[]> sharedBuffer = new BoundedBuffer<>(10);

        MasterThread<int[]> master = new MasterThread<int[]>(sharedBuffer, 5000, 3000);

        for(int i = 0; i < numSlaves; i++){
            SlaveThread<int[]> slave = new SlaveThread<int[]>(sharedBuffer, i + 1);
            slave.start();
        }

        master.start();
    }
}
