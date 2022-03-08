import java.util.Random;

class MasterThread<T> extends Thread {
    private BoundedBuffer<int[]> sharedBuffer; 
    private int sleepDuration;
    private static int requestId;
    private int maxRequestLength;

    public MasterThread(BoundedBuffer<int[]> sharedBuffer, int sleepDuration, int maxRequestLength){
        super("PRODUCER");
        this.sharedBuffer = sharedBuffer;
        this.sleepDuration = sleepDuration;
        this.maxRequestLength = maxRequestLength;
    }

    public void run(){
        while(true){
            try{
                Random random = new Random();
                int requestLength = random.nextInt(maxRequestLength) + 1;
                
                int[] item = {requestId++, requestLength};
                System.out.println("Producer: produced request ID " + requestId +",length " + requestLength + " seconds at time " + java.time.LocalTime.now());

                sharedBuffer.addItem(item);

                System.out.println("Sleeping for " + sleepDuration/1000 + " seconds");
                sleep(sleepDuration);

            } catch (Exception e){
                System.out.println(e);
            }
        }
    }
}