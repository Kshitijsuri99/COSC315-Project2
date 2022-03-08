class SlaveThread<T> extends Thread {
    private BoundedBuffer<int[]> sharedBuffer; 
    private int slaveId;

    // Constructor for slave thread 
    public SlaveThread(BoundedBuffer<int[]> sharedBuffer, int slaveId){
        super("CONSUMER");
        this.sharedBuffer = sharedBuffer;
        this.slaveId = slaveId;
    }

    // Init the run function for slave thread 
    public void run(){
        while(true){
            try{
                // Get the request from the bounded buffer
                int[] request = sharedBuffer.removeItem();
                
                // Print out statement and sleep for duration equal to length specified for the request 
                System.out.println("\nConsumer "+ slaveId +": assigned request ID " + request[0] +", prcessing request for the next "+ request[1] +" seconds,");
                System.out.println("Current Time is:" + java.time.LocalTime.now());
                sleep(request[1]);
                System.out.println("Consumer "+ slaveId +": completed request ID " + request[0] +", at time " + java.time.LocalTime.now()+"\n");
            } catch (Exception e){
                
                // Catch excpetions
                System.out.println(e);
            }
        }
    }

}