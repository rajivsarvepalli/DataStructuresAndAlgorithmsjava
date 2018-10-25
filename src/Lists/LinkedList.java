package Lists;

import java.util.Iterator;

public class LinkedList<T> implements Lists<T>, Iterable<T> {
	private ListNode<T> head, tail;
	private int size;
	public LinkedList() {
		this.head = new ListNode<T>(null);
		this.tail =new ListNode<T>(null);
		this.head.next = this.tail;
		this.tail.prev = this.head;
		this.size = 0;
	}
	
	public int size() {
		return this.size;
	}
	
	public void clear() {
		this.head = new ListNode<T>(null);
		this.tail =new ListNode<T>(null);
		this.head.next = this.tail;
		this.tail.prev = this.head;
		this.size = 0;
	}
	public void insertAtTail(T data) {
		ListNode<T> tempNode = new ListNode<T>(data);
		tempNode.prev = this.tail.prev;
		this.tail.prev.next = tempNode;
		this.tail.prev = tempNode;
		tempNode.next = this.tail;
		this.size++;

	}
	
	public void insertAtHead(T data) {
		//TODO: Write this method
		ListNode<T> tempNode = new ListNode<T>(data);
		tempNode.next = this.head.next;
		this.head.next.prev = tempNode;
		this.head.next = tempNode;
		tempNode.prev = this.head;
		this.size++;
	}
	
	public void insertAt(int index, T data) {
		ListNode<T> tempNode = new ListNode<T>(data);
		ListNode<T> node = this.head;
		for(int i=0;i<=index;i++){
			if(index==i){
				node.next.prev = tempNode;
				tempNode.next = node.next;
				node.next = tempNode;
				tempNode.prev = node;
			}
			node = node.next;
		}
			
		
		this.size++;
	}
	public void insert(ListIterator<T> it, T data) {
		ListNode<T> newNode = new ListNode<T>(data);
		newNode.next = it.Node.next;
		it.Node.next = newNode;
		newNode.prev = it.Node;
		this.size++;
		
	}
	
	
	
	public T removeAtTail(){
		if(size==0)
			return null;
		ListNode<T> lastNode = this.tail.prev;
		lastNode.prev.next = this.tail;
		this.tail.prev = lastNode.prev;
		this.size--;
		return lastNode.getData();
	}
	
	public T removeAtHead(){
		if(size==0)
			return null;
		ListNode<T> firstNode = this.head.next;
		firstNode.next.prev = this.head;
		this.head.next = firstNode.next;
		this.size--;
		return firstNode.getData();
	}
	
	public T remove(ListIterator<T> it) {
		ListNode<T> curNode = it.Node;
		if(!it.isPastEnd())
			it.moveForward();
		curNode.prev.next = curNode.next;
		curNode.next.prev = curNode.prev;
		size--;
		
		return curNode.getData();
	}
	//O(n) of time
	public int find(T data) {
		if(size==0)
			return -1;
		int i =0;
		ListIterator<T> listIterator = new ListIterator<T>(this.head.next);
		while(!listIterator.isPastEnd()){
			if(listIterator.getCurData().equals(data)){
				return i;
			}
			listIterator.moveForward();
			i++;
		}
		return -1;
	}
	/**
	 * Splits the current list by returning one half and keeping the other half in this
	 * @return the split list
	 */
	public LinkedList<T> split(){
		LinkedList<T> secondHalf = new LinkedList<T>();
		int half = this.size/2;
		while(half>0){
			secondHalf.insertAtHead(this.removeAtTail());
			half--;
		}
		return secondHalf;
	}
	public T get(int index) { 
		//TODO: Write this method
		if(index == this.size-1){
			return this.tail.prev.getData();
		}
		else if(index <=this.size/2){
			int i =0;
			ListIterator<T> listIterator = new ListIterator<T>(this.head.next);
			while(!listIterator.isPastEnd()){
				if(i==index){
					return listIterator.getCurData();
				}
				listIterator.moveForward();
				i++;
			}
		}
		else if(index >this.size/2){
			int i =this.size-1;
			ListIterator<T> listIterator = new ListIterator<T>(this.tail.prev);
			while(!listIterator.isPastBeginning()){
				if(i==index){
					return listIterator.getCurData();
				}
				listIterator.moveBackward();
				i--;
			}
		}
		return null;
	}
	
	public String toString() {
		String s = "[";
		ListIterator<T> iter = this.front();
		while(!iter.isPastEnd()){
			s+=iter.Node.toString();
			iter.moveForward();
		}
		s+="]";
		return s;
	}
	//doesnt work
	public void reverse(){
		
		ListIterator<T> iter= new ListIterator<T>(this.head.next.next);
		while(!iter.isPastEnd()){
			ListNode<T> node = (iter.Node.prev);
			ListNode<T> prev = new ListNode<T>(node.prev.getData());
			node.prev = node.next;
			node.next = prev;
			iter.moveForward();
		}
		ListNode<T> newTail = this.head;
		ListNode<T> newHead = this.tail;
		newTail.prev = this.head.next;
		newTail.next = null;
		newHead.next = this.tail.prev;
		newHead.prev = null;
		this.head = newHead;
		this.tail = newTail;
	}
	
	public ListIterator<T> front(){
		if(size==0)
			return new ListIterator<T>(this.head);
		return new ListIterator<T>(this.head.next);
	}
	public ListIterator<T> back(){
		if(size==0)
			return new ListIterator<T>(this.tail);
		return new ListIterator<T>(this.tail.prev);
	}
	public Iterator<T> iterator() {
		return new LinkedListIter();
	}
	private class LinkedListIter implements Iterator<T> {
		private ListNode<T> curNode;
		public LinkedListIter(){
			this.curNode = head;
		}
		public boolean hasNext() {
			return curNode.next.getData()!=null;
		}

		public T next() {
			return curNode.next.getData();
		}
		
	}
	

	public void remove(int i) {
		ListIterator<T> iter;
		int index;
		if(i/2 >size){
			index = this.size-1;
			iter =this.back();
			while(!iter.isPastBeginning()){
				if(i==index){
					this.remove(iter);
					break;
				}
				iter.moveBackward();
				size--;
			}
		}
		else{
			index = 0;
			iter = this.front();
			while(!iter.isPastEnd()){
				if(i==index){
					this.remove(iter);
					break;
				}
				iter.moveForward();
				size++;
			}
		}
		
	}
	public LinkedList<T> clone(){
		if(size>0&&this.get(0) instanceof Cloneable ){
			try{
				ListIterator<T> iter = this.front();
				LinkedList<T> newList = new LinkedList<T>();
				//Note: if linked list of objects then not properly cloned
				while(!iter.isPastEnd()){
					T data = iter.getCurData();
				    T dataCloned = (T) data.getClass().getMethod("clone").invoke(data);
					newList.insertAtHead(dataCloned);
					iter.moveForward();
			}
			return newList;
			}
			catch (Exception e) {
			       throw new RuntimeException("Clone not supported", e);
			   }
		}
		ListIterator<T> iter = this.front();
		LinkedList<T> newList = new LinkedList<T>();
		//Note: if linked list of objects then not properly cloned
		while(!iter.isPastEnd()){
			newList.insertAtHead(iter.getCurData());
			iter.moveForward();
		}
		return newList;
	}
}
