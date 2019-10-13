/*
 * Round Robin Queue
 */
package roundrobinscheduler_ts2;

/**
 *
 * @author bhitt
 * @param <T>
 */
public class RRQueue<T> implements IRRQueue<T>{
    //properties
    private Node<T> head=null, tail=null;
    private int numItems;
    
    //constructor
    public RRQueue(){
        numItems=0;
    }
    
    //returns true if the queue is empty
    @Override
    public boolean isEmpty() {
        return numItems==0;
    }

    //returns the number of elements in the queue
    @Override
    public int size() {
        return numItems;
    }

    //inspects the element at the front of the queue without modifying queue
    @Override
    public T front() {
        if( head==null ) return null; //return null if empty
        return head.getValue(); //return first element
    }
    
    //push a new element to the back of the queue
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

    //remove an element from the front of the queue
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
        return headDataValue;  /* returns the data value from the popped head,
                                  null if queue empty */
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
