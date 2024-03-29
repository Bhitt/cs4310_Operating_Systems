/*
 * Generic Queue that will hold nodes of job objects
 */
package job_scheduler;

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
