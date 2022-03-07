class SlaveThread<T> extends Thread {
    private BoundedBuffer<T> sharedBuffer; 
    private int sleepDuration;

    public SlaveThread(BoundedBuffer<T> sharedBuffer, int sleepDuration){
        super("CONSUMER");
        this.sharedBuffer = sharedBuffer;
        this.sleepDuration = sleepDuration;
    }

    public void run(){
        while(true){
            try{
                sharedBuffer.removeItem();
                sleep(sleepDuration);
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

}