/*
 * Author: Branden Hitt
 * Purpose: implement and test a first-come-first-serve
 *          scheduler.
 */
package first.come.first.serve_scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author bhitt
 */
public class FirstComeFirstServe_Scheduler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Create the fifo queues
        FCFS<Job> fifo= new FCFS<>();
        FCFS<Job> completed = new FCFS<>();
        //Determine test size
        int jobSize = 5;
        //Enter the jobs into the queue
        readJobs(fifo, "job.txt",jobSize);
        //create timers
        int currentTime = 0;
        double avgTurnAround=0;
        //find the amount of jobs
        int jobCount = fifo.size();
        //Scheduler chooses a process from the head of queue
        for(int i=0;i<jobCount;i++){
            //change the current time
            currentTime += fifo.front().getBurst();
            avgTurnAround += currentTime;
            //pop the front off the queue and send it to the completed queue
            completed.push(fifo.pop());
            completed.back().setStart(currentTime-completed.back().getBurst());
            completed.back().setEnd(currentTime);
        }
        //compute the average turn around time after all jobs
        avgTurnAround /= jobCount;
        //round to 2 decimal places
        DecimalFormat df = new DecimalFormat("###.##");
        System.out.println("FCFS average turnaround for "+jobCount+" jobs: "
                +df.format(avgTurnAround)+" ms");
        
        //print the jobs
        while(!completed.isEmpty()){
            completed.front().printSelf();
            completed.pop();
        }
    }
    
    public static void readJobs(FCFS<Job> fifo, String fileName, int numJobs){
        int jobCount=0;
        //attempt to open the file
        try{
            File jobFile = new File(fileName);
            Scanner reader = new Scanner(jobFile);
            //add in each job (2 lines in file for each job)
            while(reader.hasNextLine() && jobCount<numJobs){
                //first line is just the job name  "Job 3"
                //second line is the burst time    "12"
                fifo.push(new Job(reader.nextLine(),Integer.parseInt(reader.nextLine())));
                jobCount++;
            }
            //close the file reader
            reader.close();
        //account for missing file
        } catch(FileNotFoundException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
}
