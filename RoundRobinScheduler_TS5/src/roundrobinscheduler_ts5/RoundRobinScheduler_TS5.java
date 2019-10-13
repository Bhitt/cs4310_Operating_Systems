/*
 * Author: Branden Hitt
 * Purpose: implement a round robin scheduler with time slice 5
 */
package roundrobinscheduler_ts5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author bhitt
 */
public class RoundRobinScheduler_TS5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create a queue
        RRQueue<Job> rr5 = new RRQueue<>();
        //Determine test size (1-30)
        int jobSize = 10;
        //enter the jobs into the queue
        readJobs(rr5, "job.txt", jobSize);
        //create timers
        int currentTime = 0;
        double avgTurnAround=0;
        //Scheduler gives slices of time to each until they are all done
        while(!(rr5.isEmpty())){
            //give a time slice of 2 to the current process
            rr5.front().setBurst(rr5.front().getBurst()-5);
            //check if process is done
            if(rr5.front().getBurst()<=0){
                //update the current time
                currentTime+= rr5.front().getBurst()+5;
                //capture the turn around time
                avgTurnAround+=currentTime;
                //pop off the current process
                rr5.pop();
            }else{
                //update the current time
                currentTime+=5;
                //pop off the current process
                Job temp = rr5.pop();
                //push the current process back to the end of the queue
                rr5.push(temp);
            }
        }
        //compute the average turn around time
        avgTurnAround /= jobSize;
        //round to 2 decimal places
        avgTurnAround = (double) Math.round(avgTurnAround * 100) / 100;
        //output
        System.out.println("RR average turnaround for "+jobSize+" jobs: "
            +avgTurnAround+" ms");
    }
    
    private static void readJobs(RRQueue<Job> rr5, String fileName, int numJobs){
        int jobCount=0;
        //attempt to open the file
        try{
            File jobFile = new File(fileName);
            Scanner reader = new Scanner(jobFile);
            //add in each job (2 lines in file for each job)
            while(reader.hasNextLine() && jobCount<numJobs){
                //first line is just the job name  "Job 3"
                //second line is the burst time    "12"
      rr5.push(new Job(reader.nextLine(),Integer.parseInt(reader.nextLine())));
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
