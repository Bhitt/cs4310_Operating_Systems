/*
 * Queue: first in first out queue structure that can also push a current
 *        queue item to the back of the queue
 */
package pagereplacementmanager;

/**
 *
 * @author bhitt
 * @param <T>
 */
public class Queue<T> implements IQueue<T> {
    //linked list containing the items in the queue
    private Node<T> head=null, tail=null;
    //current numbers of items in the queue
    private int numItems;
    
    //default constructor
    public Queue(){
        numItems=0;
    }
    
    //returns true if the queue is empty
    @Override
    public boolean isEmpty() {
       return numItems==0;
    }

    
    @Override
    public int size() {
      return numItems;
    }

    @Override
    public T front() {
      if ( head==null ) return null;
      return head.getValue(); // return item stored at head node
    }

    @Override
    public T back() {
      if ( tail==null ) return null;
      return tail.getValue(); // return item stored at tail node
    }

    @Override
    public void push(T newItem) {
      // if head node is null, make head and tail node contain the first node
      if ( head == null){
        head = new Node(newItem);
        tail=head; // when first item is enqueued, head and tail are the same
        numItems++; // increment the number of items in the queue
      }else{
        Node<T> newNode = new Node(newItem);
        tail.setNext(newNode);
        newNode.setPrevious(tail);
        tail=newNode;
        numItems++;
      }
    }

    @Override
    public T pop() {
      T headDataValue = null;
      if ( numItems > 0 ){
        headDataValue = head.getValue();
        Node<T> oldHead=head;
        head=head.getNext();
        oldHead.setNext(null);
        oldHead.setPrevious(null);
        numItems--;
      }
      return headDataValue;  // returns the data value from the popped head, null if queue empty
    }
    
    public Node<T> getNode(T val){
        //if there is nothing in the queue, return null
        if(head == null) return null;
        //start at the head
        Node<T> current = head;
        while(current != null){
            //return true if the queue contains the value
            if(current.getValue() == val){
                return current;
            }
            //traverse forward into the queue
            current = current.getNext();
        }
        //otherwise return false (val not in queue)
        return null;
    }

    @Override
    public boolean contains(T val) {
        //if there is nothing in the queue, return false
        if(head == null) return false;
        //start at the head
        Node<T> current = head;
        while(current != null){
            //return true if the queue contains the value
            if(current.getValue() == val){
                return true;
            }
            //traverse forward into the queue
            current = current.getNext();
        }
        //otherwise return false (val not in queue)
        return false;
    }

    @Override
    public void print() {
        if(!this.isEmpty()){
           Node<T> current = head;
           while(current != null){
               System.out.print(current.getValue()+" ");
               current = current.getNext();
           }
           System.out.println();
        }
    }
    
    public void pushNodeBackwards(Node<T> queueItem) {
        //check if queueItem is at the front of queue
        if(head == queueItem){
            if(tail == head){ //if only one item in the queue
                return;
            }
            //pop off the front
            T oldHead = this.pop();
            //push to back
            this.push(oldHead);
        } else if(tail == queueItem){   //check if queueItem is at the back
            //do nothing
        } else{ //otherwise queueItem is in the middle somewhere
            //point before item to after item
            queueItem.getPrevious().setNext( queueItem.getNext() );
            //point after item to before item
            queueItem.getNext().setPrevious( queueItem.getPrevious() );
            //set after of queueItem to null
            queueItem.setNext(null);
            //put the queueItem to the end
            tail.setNext(queueItem);
            queueItem.setPrevious(tail);
            tail=queueItem;
        }
    }
    
    public void removeOptimal(String lookahead){
        //if no items then just return
        if(numItems == 0 ) return;
        //variables for comparison
        int distance;
        int maxDistance=0;
        Node<T> deadNode = head;
        Character c;
        
        //start at the head
        Node<T> current = head;
        
        //look through all items in queue
        while(current != null){
            distance=0;
            //loop through lookahead to find the distance
            for(int i=0;i<lookahead.length();i++){
                c = lookahead.charAt(i);
                if( c.equals(current.getValue()) ){
                    distance = i+1;
                    break;
                }
            }
            //check if max distance
            if(distance == 0){  //node not used at all later -> optimal
                deadNode = current; // keep track of node
                break;
            } else if( distance > maxDistance){
                maxDistance = distance; //new max
                deadNode = current; //keep track of node
            }
            //grab the next node
            current = current.getNext();
        }
        //remove the optimal node
        removeNode(deadNode);
    }
    
    public void removeNode(Node <T> node){
        //check if queueItem is at the front of queue
        if(head == node){
            //pop off the top
            this.pop();
        } else if(tail == node){   //check if queueItem is at the back
            //remove reference from penultimate node
            tail.getPrevious().setNext(null);
            //set the new tail
            tail = tail.getPrevious();
            //delete the node passed in
            node.setNext(null);
            node.setPrevious(null);
            numItems--;
        } else{ //otherwise queueItem is in the middle somewhere
            //point before item to after item
            node.getPrevious().setNext( node.getNext() );
            //point after item to before item
            node.getNext().setPrevious( node.getPrevious() );
            //delete the node passed in
            node.setNext(null);
            node.setPrevious(null);
            numItems--;
        }
    }
    
     // Node Inner Class
    private class Node<T> {
        private T value;
        private Node _previous, _next;
    
        public Node(T data) {
            value = data;
            _previous = null;
            _next = null;
        }
    
        protected Node(T data, Node previousNode, Node nextNode) {
            value = data;
            _previous = previousNode;
	    _next = nextNode;
        }
		
        public Node getNext() {
            return _next;
        }
		
		public Node getPrevious() {
            return _previous;
        }
        
	public void setValue(T newValue){
            value=newValue;
	}

        public T getValue() {
            return value;
        }
    
        public void setNext(Node newNextNode) {
            _next = newNextNode;
        }
		
        public void setPrevious(Node newPreviousNode) {
            _previous = newPreviousNode;
        }
    }
}
