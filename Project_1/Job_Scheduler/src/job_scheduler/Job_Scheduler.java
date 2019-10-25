/*
 * Branden Hitt
 * Program Purpose: simulate job scheduling algorithms and analyze performance
 */
package job_scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author bhitt
 */
public class Job_Scheduler {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Generate job files if needed
//        String fileName = "Job";
//        for(int i=1;i<11;i++){
//            generateJobs(fileName+i+".txt");
//        }
        
        //Used for output of averages
        DecimalFormat df = new DecimalFormat("###.##");
        //Average for multiple trials
        double average = 0;
        double average2= 0;
        double average3= 0;
        double average4= 0;
        //Given job size [1-30]
        int jobSize = 10;
        //How many trials
        int trials = 10;

        
        for(int i=1;i<trials+1;i++){
            average  += fcfs(jobSize,("Job"+i+".txt"));
            average2 += sjf(jobSize,("Job"+i+".txt"));
            average3 += roundRobin(jobSize,("Job"+i+".txt"),2);
            average4 += roundRobin(jobSize,("Job"+i+".txt"),5);
        }
        
        //get the final averages
        average/=trials;
        average2/=trials;
        average3/=trials;
        average4/=trials;
        
        System.out.println("Jobsize: "+jobSize);
        System.out.println("The averages over "+trials+" trial(s) are:");
        System.out.println("FCFS: "+df.format(average));
        System.out.println("SJF: "+df.format(average2));
        System.out.println("RR2: "+df.format(average3));
        System.out.println("RR5: "+df.format(average4));
    }
    
    //------------------------------------------//
    //             Test File Creation           //
    //------------------------------------------//
    public static void generateJobs(String fileName) throws FileNotFoundException{
        //Create instance of rand
        Random rand = new Random();
        
        //Create a file object
        PrintStream fout = new PrintStream(new File(fileName));
        
        //Store current system.out
        PrintStream console = System.out;
        
        //Assign fout to output stream
        System.setOut(fout);
        
        for(int i=1;i<31;i++){
            System.out.println("Job "+i);
            System.out.println(rand.nextInt(20)+1);
        }
        
        //Return output stream to console
        System.setOut(console);
        
        //Output finish
        System.out.println(fileName+" created");
    }
    //------------------------------------------//
    //         First Come First Serve           //
    //------------------------------------------//
    public static double fcfs(int jobSize, String fileName){
        //Create the fifo queues
        Queue<Job> fifo= new Queue<>();
        Queue<Job> completed = new Queue<>();
        //Enter the jobs into the queue
        readJobsQueue(fifo, fileName,jobSize);
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
//        System.out.println("FCFS average turnaround for "+jobCount+" jobs: "
//                +df.format(avgTurnAround)+" ms");
        
        //print the jobs
        while(!completed.isEmpty()){
            //completed.front().printSelf();
            completed.pop();
        }
        
        //return the simulations average turn around time
//        System.out.println("avgT:"+avgTurnAround);
        return avgTurnAround;
    }
    public static void readJobsQueue(Queue<Job> fifo, String fileName, int numJobs){
        int jobCount=0;
        //attempt to open the file
        try{
            File jobFile = new File(fileName);
            //add in each job (2 lines in file for each job)
            try (Scanner reader = new Scanner(jobFile)) {
                //add in each job (2 lines in file for each job)
                while (reader.hasNextLine() && jobCount<numJobs) {
                    //first line is just the job name  "Job 3"
                    //second line is the burst time    "12"
                    fifo.push(new Job(reader.nextLine(),Integer.parseInt(reader.nextLine())));
                    jobCount++;
                }
                //close the file reader
                //account for missing file
            }
        } catch(FileNotFoundException e){
            System.out.println("An error occurred");
        }
    }
    //------------------------------------------//
    //         Shortest Job First               //
    //------------------------------------------//
    public static double sjf(int jobSize, String fileName){
        //Initialize the queue
        PriorityQueue<Job> sjf = new PriorityQueue<>();
        PriorityQueue<Job> completed = new PriorityQueue<>();
        //Enter the jobs in the queue, sorting by shortest job first
        readJobsPQueue(sjf, fileName, jobSize);
        //create timers
        int currentTime = 0;
        double avgTurnAround=0;
        //temp variable
        int currentBurst;
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
            //System.out.println("Completed at:"+currentTime);
        }
        //compute the average turn around time after all jobs
        avgTurnAround /= jobCount;
        //round to 2 decimal places
        DecimalFormat df = new DecimalFormat("###.##");
        //System.out.println("SJF average turnaround for "+jobCount+" jobs: "
                //+df.format(avgTurnAround)+" ms");
        
        //print the jobs
        while(!completed.isEmpty()){
            //completed.peek().printSelf();
            completed.remove_shortest();
        }
        //return the average turn around time
        //System.out.println("avgT:"+avgTurnAround);
        return avgTurnAround;
    }
    public static void readJobsPQueue(PriorityQueue<Job> sjf, String fileName, int numJobs){
        int jobCount=0;
        //attempt to open the file
        try{
            File jobFile = new File(fileName);
            //add in each job (2 lines in file for each job)
            try (Scanner reader = new Scanner(jobFile)) {
                //add in each job (2 lines in file for each job)
                while (reader.hasNextLine() && jobCount<numJobs) {
                    //first line is just the job name  "Job 3"
                    //second line is the burst time    "12"
                    String name = reader.nextLine();
                    int burstTime = Integer.parseInt(reader.nextLine());
                    sjf.insert(new Job(name,burstTime),burstTime);
                    jobCount++;
                }
                //close the file reader
                //account for missing file
            }
        } catch(FileNotFoundException e){
            System.out.println("An error occurred:"+e);
        }
    }
    //------------------------------------------//
    //         Round Robin                      //
    //------------------------------------------//
    public static double roundRobin(int jobSize, String fileName, int timeSlice){
        // Create a queue
        Queue<Job> rr = new Queue<>();
        //enter the jobs into the queue
        readJobsQueue(rr, fileName, jobSize);
        //create timers
        int currentTime = 0;
        double avgTurnAround=0;
        //Scheduler gives slices of time to each until they are all done
        while(!(rr.isEmpty())){
            //give a time slice to the current process
//            System.out.println("Time:"+currentTime+"  | "+rr.front().getName()+
//                    " ("+rr.front().getBurst()+")   given slice="+timeSlice);
            rr.front().setBurst(rr.front().getBurst()-timeSlice);
            //check if process is done
            if(rr.front().getBurst()<=0){
                //update the current time
                currentTime+= rr.front().getBurst()+timeSlice;
                //System.out.println("Completed at:"+currentTime);
                //capture the turn around time
                avgTurnAround+=currentTime;
                //pop off the current process
//                System.out.println(rr.front().getName()+" completed at:"+currentTime);
                rr.pop();
            }else{
                //update the current time
                currentTime+=timeSlice;
                //pop off the current process
                Job temp = rr.pop();
                //push the current process back to the end of the queue
                rr.push(temp);
            }
        }
        //compute the average turn around time
        avgTurnAround /= jobSize;
        //round to 2 decimal places
        avgTurnAround = (double) Math.round(avgTurnAround * 100) / 100;
        //output
            //System.out.println("Round Robin average turnaround for "+jobSize+" jobs: "
            //+avgTurnAround+" ms");
        //return average turn around time
        //System.out.println("avgT:"+avgTurnAround);
        return avgTurnAround;
    }
    
}
