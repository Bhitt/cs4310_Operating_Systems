/*
 * Author: Branden Hitt
 * Program Purpose: Simulate page replacement algorithms
 */
package pagereplacementmanager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author bhitt
 */
public class PageReplacementManager {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        String currentRef;
        int trials = 50;
        double[][] avgTimes = new double[4][3];
        int pageFrameSize;
        //open file writer
        FileWriter fw = new FileWriter("referenceStrings.txt");
        //loop through all the trials
        for(int i=0;i<trials;i++){
            //generate the reference string for the current trial
            currentRef = generateRef();
            fw.write(currentRef+"\n");
            //run all three algorthims at page frame size 3
            pageFrameSize = 3;
            avgTimes[0][0] += fifo(currentRef, pageFrameSize);
            avgTimes[0][1] += lru(currentRef, pageFrameSize);
            avgTimes[0][2] += optimal(currentRef, pageFrameSize);
            //page frame size 4
            pageFrameSize = 4;
            avgTimes[1][0] += fifo(currentRef, pageFrameSize);
            avgTimes[1][1] += lru(currentRef, pageFrameSize);
            avgTimes[1][2] += optimal(currentRef, pageFrameSize);
            //page frame size 5
            pageFrameSize = 5;
            avgTimes[2][0] += fifo(currentRef, pageFrameSize);
            avgTimes[2][1] += lru(currentRef, pageFrameSize);
            avgTimes[2][2] += optimal(currentRef, pageFrameSize);
            //page frame size 6
            pageFrameSize = 6;
            avgTimes[3][0] += fifo(currentRef, pageFrameSize);
            avgTimes[3][1] += lru(currentRef, pageFrameSize);
            avgTimes[3][2] += optimal(currentRef, pageFrameSize);
        }
        //close the file stream
        fw.close();
        
        //get the average by dividing by the trial size
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                avgTimes[i][j] /= trials;
            }
        }
        //output the avgerage times of the 50 trials
        print(avgTimes);
    }
    
    
    //**********************************//
    // Page Reference String Generator  //
    //**********************************//
    public static String generateRef(){
        //Create instance of rand
        Random rand = new Random();
        // create a StringBuilder object 
        StringBuilder str = new StringBuilder();
        for(int i=0;i<30;i++){
            //add 30 random page references to the 8 pages (0-7)
            str.append(rand.nextInt(8));  
        }
        return str.toString();
    }
    
    //**********************************//
    //      First In First Out          //
    //**********************************//
    public static int fifo(String referenceStr, int maxFrameSize){
        //create the page frame (queue)
        Queue pageFrame = new Queue();
        //create a counter for the page faults
        int faultCounter=0;
        char page=0;
        //process the reference string
        for(int i=0;i<referenceStr.length();i++){
            //find the current page reference
            page = referenceStr.charAt(i);
            //check if the frame is not yet full
            if(pageFrame.size() < maxFrameSize){
                //check if the page is already in the frame
                if(pageFrame.contains(page)){
                    //Do Nothing, no fault
                }else{
                    //add a page to the frame and increment page fault counter
                    pageFrame.push(page);
                    faultCounter++;
                }
            }else{
                //check if the page is already in the frame
                if(pageFrame.contains(page)){
                    //Do nothing, no fault
                }else{
                    //pop off the top of the pageFrame queue and push a page
                    pageFrame.pop();
                    pageFrame.push(page);
                    //incremenet fault
                    faultCounter++;
                }
            }
        }
        //return the total amount of faults
        //System.out.println(faultCounter);
        return faultCounter;
    }
    
    //**********************************//
    //      Least Recently Used         //
    //**********************************//
    public static int lru(String referenceStr, int maxFrameSize){
        //create the page frame (queue)
        Queue pageFrame = new Queue();
        //create a counter for the page faults
        int faultCounter=0;
        char page=0;
        //process the reference string
        for(int i=0;i<referenceStr.length();i++){
            //find the current page reference
            page = referenceStr.charAt(i);
            //check if the frame is not yet full
            if(pageFrame.size() < maxFrameSize){
                //check if the page is already in the frame
                if(pageFrame.contains(page)){
                    //push that node to the back
                    pageFrame.pushNodeBackwards(pageFrame.getNode(page));
                }else{
                    //add a page to the frame and increment page fault counter
                    pageFrame.push(page);
                    faultCounter++;
                }
            }else{
                //check if the page is already in the frame
                if(pageFrame.contains(page)){
                    //push that node to the back
                    pageFrame.pushNodeBackwards(pageFrame.getNode(page));
                }else{
                    //pop off the top of the pageFrame queue and push a page
                    pageFrame.pop();
                    pageFrame.push(page);
                    //incremenet fault
                    faultCounter++;
                }
            }
        }
        //return the total amount of faults
//        System.out.println(faultCounter);
        return faultCounter;
    }
    
    
    //**********************************//
    //      Optimal                     //
    //**********************************//
    public static int optimal(String referenceStr, int maxFrameSize){
        //create the page frame (queue)
        Queue pageFrame = new Queue();
        //create a counter for the page faults
        int faultCounter=0;
        char page=0;
        String lookahead;
        //process the reference string
        for(int i=0;i<referenceStr.length();i++){
            //find the lookahead string
            lookahead  = referenceStr.substring(i, referenceStr.length() -1);
            //find the current page reference
            page = referenceStr.charAt(i);
            //check if the frame is not yet full
            if(pageFrame.size() < maxFrameSize){
                //check if the page is already in the frame
                if(pageFrame.contains(page)){
                    //push that node to the back
                    pageFrame.pushNodeBackwards(pageFrame.getNode(page));
                }else{
                    //add a page to the frame and increment page fault counter
                    pageFrame.push(page);
                    faultCounter++;
                }
            }else{
                //check if the page is already in the frame
                if(pageFrame.contains(page)){
                    //push that node to the back
                    pageFrame.pushNodeBackwards(pageFrame.getNode(page));
                }else{
                    //pop off pages optimally based on lookahead
                    pageFrame.removeOptimal(lookahead);
                    pageFrame.push(page);
                    //incremenet fault
                    faultCounter++;
                }
            }
        }
        //return the total amount of faults
        //System.out.println(faultCounter);
        return faultCounter;   
    }
    
    //**********************************//
    //      Results Output              //
    //**********************************//
    static void print(double[][] a){
//        System.out.println("Average of 50 Trials");
        System.out.println("Page Frame Size 3");
        System.out.println("FIFO: "+a[0][0]);
        System.out.println("LRU: "+a[0][1]);
        System.out.println("Optimal: "+a[0][2]);
        System.out.println("Page Frame Size 4");
        System.out.println("FIFO: "+a[1][0]);
        System.out.println("LRU: "+a[1][1]);
        System.out.println("Optimal: "+a[1][2]);
        System.out.println("Page Frame Size 5");
        System.out.println("FIFO: "+a[2][0]);
        System.out.println("LRU: "+a[2][1]);
        System.out.println("Optimal: "+a[2][2]);
        System.out.println("Page Frame Size 6");
        System.out.println("FIFO: "+a[3][0]);
        System.out.println("LRU: "+a[3][1]);
        System.out.println("Optimal: "+a[3][2]);
    }
}
