/*
 * Author: Branden Hitt
 * Program Purpose: Simulate page replacement algorithms
 */
package pagereplacementmanager;

import java.util.Random;

/**
 *
 * @author bhitt
 */
public class PageReplacementManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //
        int pageFrameSize = 3;
        String temp = "70120304230321201701";
        System.out.println(temp);
        //
        System.out.println("Faults: "+lru(temp,pageFrameSize));
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
        return faultCounter;
    }
    
    
    //**********************************//
    //      Optimal                     //
    //**********************************//
    public static void optimal(String referenceStr){
        
    }
}
