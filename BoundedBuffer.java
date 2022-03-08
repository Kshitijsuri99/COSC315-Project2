public class BoundedBuffer<T>{

    // Create an object buffer
    private final Object[] buffer; 
    private int insertIndex, removeIndex, itemCount;

    public BoundedBuffer(int bound){
        buffer = new Object[bound];
    }

    // Monitor addItem fn to add items to buffer
    public synchronized void addItem(T item){
        try{
            while(isFull()){
                wait();
            }
        } catch (Exception e){
            System.out.println(e);
        }
        add(item);
        notify();
    }

    // Helper fn to add items and increase item count
    private synchronized void add(T item){
        buffer[insertIndex] = item;
        if(++insertIndex == buffer.length){
            insertIndex = 0;
        }
        ++itemCount;
    }

    // Monitor removeItem fn to remove items from buffer
    public synchronized T removeItem(){
        try{
            while(isEmpty()){
                wait();
            }
        } catch (Exception e){
            System.out.println(e);
        }
        T element = remove();
        notify();
        return element;
    }

    // Helper fn to remove items and decrease item count
    private synchronized T remove(){
        T element = (T) buffer[removeIndex];
        if (++removeIndex == buffer.length) { 
            removeIndex = 0; 
        } 
        --itemCount;
        return element;

    }

    public synchronized boolean isFull() {
        return itemCount == buffer.length;
    }

    public synchronized boolean isEmpty() {
        return itemCount == 0;
    }
}