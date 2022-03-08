class SlaveThread<T> extends Thread {
    private BoundedBuffer<int[]> sharedBuffer; 
    private int slaveId;

    public SlaveThread(BoundedBuffer<int[]> sharedBuffer, int slaveId){
        super("CONSUMER");
        this.sharedBuffer = sharedBuffer;
        this.slaveId = slaveId;
    }

    public void run(){
        while(true){
            try{
                int[] request = sharedBuffer.removeItem();
                
                System.out.println("\nConsumer "+ slaveId +": assigned request ID " + request[0] +", prcessing request for the next "+ request[1] +" seconds,");
                System.out.println("Current Time is:" + java.time.LocalTime.now());
                sleep(request[1]);
                System.out.println("Consumer "+ slaveId +": completed request ID " + request[0] +", at time " + java.time.LocalTime.now()+"\n");
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

}