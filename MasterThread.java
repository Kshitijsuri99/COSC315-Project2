class MasterThread<T> extends Thread {
    private BoundedBuffer<T> sharedBuffer; 
    private int sleepDuration;
    private T item;

    public MasterThread(BoundedBuffer<T> sharedBuffer, T item, int sleepDuration){
        super("PRODUCER");
        this.sharedBuffer = sharedBuffer;
        this.item = item;
        this.sleepDuration = sleepDuration;
    }

    public void run(){
        while(true){
            try{
                sharedBuffer.addItem(item);
                sleep(sleepDuration);
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

}