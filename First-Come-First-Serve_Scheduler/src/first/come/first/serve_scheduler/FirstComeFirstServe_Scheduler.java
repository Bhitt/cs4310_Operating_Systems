/*
 * Author: Branden Hitt
 * Purpose: implement and test a first-come-first-serve
 *          scheduler.
 */
package first.come.first.serve_scheduler;

/**
 *
 * @author bhitt
 */
public class FirstComeFirstServe_Scheduler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FCFS<Integer> fifo= new FCFS<>();
        fifo.push(10);
        System.out.println(fifo.front());
        fifo.pop();
    }
    
}
