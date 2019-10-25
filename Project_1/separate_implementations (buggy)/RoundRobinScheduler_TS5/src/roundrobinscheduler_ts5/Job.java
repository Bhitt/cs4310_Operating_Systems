/*
 * Job Class:
 * implementation of a job given to a scheduler
 * contains a start time, running time, and end time
 */
package roundrobinscheduler_ts5;

/**
 *
 * @author bhitt
 */
public class Job {
    //properties
    private String name;
    private int startTime;
    private int burstTime;
    private int endTime;
    //constructor
    public Job(String n, int b){
        name = n;
        startTime = 0;
        burstTime = b;
        endTime = 0;
    }
    
    //setters
    void setName(String n){
        name = n;
    }
    void setStart(int s){
        startTime = s;
    }
    void setBurst(int b){
        burstTime = b;
    }
    void setEnd(int e){
        endTime = e;
    }
    //getters
    String getName(){
        return name;
    }
    int getStart(){
        return startTime;
    }
    int getBurst(){
        return burstTime;
    }
    int getEnd(){
        return endTime;
    }
    //output
    void printSelf(){
        System.out.println(name+": start("+startTime+") end("+endTime+
                ") burst("+burstTime+")");
    }
}
