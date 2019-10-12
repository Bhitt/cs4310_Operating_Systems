/*
 * Purpose: implement a queue that prioritizes shortest jobs first
 */
package shortest.job.first_scheduler;

/**
 *
 * @author bhitt
 */
public class SJFQueue<T> implements ISJFQueue<T> {
    //Properties
    private Node<T> head=null;
    private int numItems;
    
    //constructor
    public SJFQueue(){
        numItems=0;
    }
    
    //return true if queue is empty
    @Override
    public boolean isEmpty() {
        return numItems==0;
    }
    
    //return the number of elements in the queue
    @Override
    public int size(){
        return numItems;
    }

    //returns the shortest job element but does not modify the queue
    @Override
    public T peek() {
        if(isEmpty()){
            return null;
        }
        return head.getValue();
    }

    /*insert an element in the queue, (the shorter the batch time, the closer
      it is to the front */
    @Override
    public void insert(T element, int burstTime) {
        Node<T> newNode = new Node<T>(element,burstTime);
        //if there are no elements in the queue
        if(head == null){
            head = newNode;
            numItems++;
            return;
        }
        //if there are existing elements
        Node<T> current = head;
        for(int i=0;i<numItems;i++){
            //if the new job is shorter
            if(newNode.getBurstTime() < current.getBurstTime() ){
                //place new node before current
                newNode.setNext(current);
                //check for first
                if(current.getPrevious() == null) head = newNode;
                else{
                    current.getPrevious().setNext(newNode);
                }
                current.setPrevious(newNode);
                break;
            }else{ //else, continue through to next element
                if(current.getNext() == null){
                    current.setNext(newNode);
                    newNode.setPrevious(current);
                    break;
                }
                current=current.getNext();
                
            }
        }
        numItems++;
    }

    //remove the shortest batch time element from the queue and return it
    @Override
    public T remove_shortest() {
        T headDataValue = null;
        if(numItems > 0){
            headDataValue = head.getValue();
            Node<T> oldHead = head;
            head = head.getNext();
            oldHead.setNext(null);
            oldHead.setPrevious(null);
            numItems--;
        }
        return headDataValue;
    }

    //display elements in the queue in order from shortest to longest batch time
    @Override
    public void display() {
        Node current;
        current = head;
        System.out.println("---Queue---");
        System.out.println("Items: "+numItems);
        for(int i=0;i<numItems;i++){
            System.out.println(current.getValue()+" ("+current.getBurstTime()+")");
            current=current.getNext();
            if(current==null) break;
        }
        System.out.println("------------");
    }
    
    // Node Inner Class
    private class Node<T> {
        private T value;
        private int burstTime;
        private Node _previous, _next;
    
        public Node(T data, int b){
            value = data;
            _previous = null;
            _next = null;
            burstTime = b;
        }
    
        protected Node(T data, int b, Node previousNode, Node nextNode){
            value = data;
            _previous = previousNode;
            _next = nextNode;
            burstTime = b;
        }
		
        public Node getNext(){
            return _next;
        }
		
	public Node getPrevious(){
            return _previous;
        }
        
	public void setValue(T newValue){
            value=newValue;
	}
        
        public void setBurstTime(int newBurstTime){
            burstTime = newBurstTime;
        }

        public T getValue(){
            return value;
        }
        
        public int getBurstTime(){
            return burstTime;
        }
    
        public void setNext(Node newNextNode){
            _next = newNextNode;
        }
		
	public void setPrevious(Node newPreviousNode){
            _previous = newPreviousNode;
        }
    }
}
