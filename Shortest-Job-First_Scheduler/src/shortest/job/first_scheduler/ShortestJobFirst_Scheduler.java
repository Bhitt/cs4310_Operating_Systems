/*
 * Author: Branden Hitt
 * Purpose: implement shortest job first scheduling algorithm
 */
package shortest.job.first_scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author bhitt
 */
public class ShortestJobFirst_Scheduler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Initialize the queue
        SJFQueue<Job> sjf = new SJFQueue<>();
        SJFQueue<Job> completed = new SJFQueue<>();
        //Determine test size
        int jobSize = 8;
        //Enter the jobs in the queue, sorting by shortest job first
        readJobs(sjf, "job.txt", jobSize);
        //create timers
        int currentTime = 0;
        double avgTurnAround=0;
        //temp variable
        int currentBurst=0;
        //find the amount of jobs
        int jobCount = sjf.size();
        //Scheduler chooses a process from the head of queue
        for(int i=0;i<jobCount;i++){
            //get the current burst time
            currentBurst = sjf.peek().getBurst();
            //change the current time
            currentTime += currentBurst;
            avgTurnAround += currentTime;
            //pop the front off the queue and send it to the completed queue
            sjf.peek().setStart(currentTime-currentBurst);
            sjf.peek().setEnd(currentTime);
            completed.insert(sjf.remove_shortest(),currentBurst);
        }
        //compute the average turn around time after all jobs
        avgTurnAround /= jobCount;
        //round to 2 decimal places
        DecimalFormat df = new DecimalFormat("###.##");
        System.out.println("SJF average turnaround for "+jobCount+" jobs: "
                +df.format(avgTurnAround)+" ms");
        
        //print the jobs
        while(!completed.isEmpty()){
            completed.peek().printSelf();
            completed.remove_shortest();
        }
    }
    
    public static void readJobs(SJFQueue<Job> sjf, String fileName, int numJobs){
        int jobCount=0;
        //attempt to open the file
        try{
            File jobFile = new File(fileName);
            Scanner reader = new Scanner(jobFile);
            //add in each job (2 lines in file for each job)
            while(reader.hasNextLine() && jobCount<numJobs){
                //first line is just the job name  "Job 3"
                //second line is the burst time    "12"
                String name = reader.nextLine();
                int burstTime = Integer.parseInt(reader.nextLine());
                sjf.insert(new Job(name,burstTime),burstTime);
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
