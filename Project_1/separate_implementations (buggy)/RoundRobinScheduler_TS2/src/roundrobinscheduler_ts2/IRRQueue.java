/*
 * Round Robin Queue Interface
 */
package roundrobinscheduler_ts2;

/**
 *
 * @author bhitt
 * @param <T>
 */
public interface IRRQueue<T> {
    
    //returns true if the FCFS queue is empty
    public boolean isEmpty();
	
    // return the number of items currently in the queue
    public int size();
 
    // returns the value of the item currently at front of queue
    public T front();
	
    /* places parameter newItem onto the end of the queue
       (this is synonymous to the enqueue method)*/
    public void push(T newItem);
	
    /* returns and removes the current item at the front of the queue
     the item that is in the queue behind the item becomes the new front item*/
    public T pop();
}
