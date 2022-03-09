import java.util.Random;

class MasterThread<T> extends Thread {
    private BoundedBuffer<int[]> sharedBuffer; 
    private int sleepDuration;
    private static int requestId;
    private int maxRequestLength;

    // Constructor for master thread 
    public MasterThread(BoundedBuffer<int[]> sharedBuffer, int sleepDuration, int maxRequestLength){
        super("PRODUCER");
        this.sharedBuffer = sharedBuffer;
        this.sleepDuration = sleepDuration;
        this.maxRequestLength = maxRequestLength;
    }

    public void run(){
        while(true){
            try{
                // Create a random number between 1 to M, specifying the length of request
                Random random = new Random();
                int requestLength = random.nextInt(maxRequestLength) + 1;
                
                // Create a request array with request ID and length 
                int[] request = {requestId++, requestLength};
                System.out.println("\nProducer: produced request ID " + requestId +",length " + requestLength + " seconds at time " + java.time.LocalTime.now());

                // Add the request to the shared buffer
                sharedBuffer.addItem(request);

                // Sleep for a user defined time
                System.out.println("Sleeping for " + sleepDuration/1000 + " seconds\n");
                sleep(sleepDuration);

            } catch (Exception e){

                // Catch excpetions
                System.out.println(e);
            }
        }
    }
}