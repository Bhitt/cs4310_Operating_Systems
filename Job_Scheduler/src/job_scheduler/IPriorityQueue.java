/*
 * Priority Queue interface
 */
package job_scheduler;

/**
 *
 * @author bhitt
 * @param <T>
 */
public interface IPriorityQueue<T> {
    /* returns true if the queue is empty (no items in queue) 
        false if queue is (has at least one or more items in queue)*/
    public boolean isEmpty();
    
    //returns the amount of elements in the queue
    public int size();

    // returns the shortest job element but does not modify the queue
    public T peek();

    /*insert –  insert an element into the queue with a given running time 
    (shorter burst time value,the higher the priority is for the data element).
    */
    public void insert(T element,int burstTime);

    //remove_maximum – removes the shortest job from the queue and returns it
    public T remove_shortest();


    /*display – display the elements in the queue in order from shortest 
        job to longest job */
    public void display();
}
