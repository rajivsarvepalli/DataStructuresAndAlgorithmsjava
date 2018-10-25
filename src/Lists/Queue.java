package Lists;


public class Queue<T>{
	private final boolean vectorBased;
	private Object type;
	public Queue(boolean vectorBased){
		this.vectorBased = vectorBased;
		if(vectorBased)
			type = new VectorQueue();
		else
			type = new LinkedListQueue();
	}
	//norammly linekd list based
	public Queue(){
		this.vectorBased = false;
		type = new LinkedListQueue();
	}
	//implement later
	private class VectorQueue{
		private T[] queue; 
		private int size;
		private int head = -1;
		private int tail =-1;
		public void enqueue(T data){
			if(size==queue.length)
				resize(queue.length*2);
			head = (head+1)%queue.length;//mod to loop it
			size++;
			if(tail==-1)
				tail = head;
			queue[head] = data;
		}
		public T dequeue(){
			if(this.size<=queue.length/2){
				this.resize(queue.length/2);
			}
			T res = queue[tail];
			tail = (tail+1)%queue.length;//mod to loop it
			size--;
			if(size==0){
				tail = -1;
				head = tail; //both -1
			}
			return res;
		}
		public T peek(){
			if(size!=0)
				return queue[tail];
			else
				return null;
		}
		public void resize(int capacicity){
			if(size>capacicity)
				return;
			T[] data = (T[])new Object[capacicity];
			for(int i=0;i<this.size;i++){
				data[i]  =this.queue[i];
			}
			this.queue = data;
		}
		public int getSize(){
			return this.size;
		}
	}
	private class LinkedListQueue{
		private LinkedList<T> queue;
		public LinkedListQueue(){
			queue = new LinkedList<T>();
		}
		public void enqueue(T data){
			queue.insertAtTail(data);
		}
		public T dequeue(){
			return queue.removeAtHead();
		}
		public T peek(){
			return queue.get(0);
		}
		public int getSize(){
			return queue.size();
		}
	}
	public void enqueue(T data){
		if(vectorBased){
			VectorQueue vectorQueue = (VectorQueue)type;
			vectorQueue.enqueue(data);
		}
			
		else{
			LinkedListQueue queuList = (LinkedListQueue)type;
			queuList.enqueue(data);
		}
	}
	public T dequeue(){
		if(vectorBased){
			VectorQueue vectorQueue = (VectorQueue)type;
			return vectorQueue.dequeue();
		}
			
		else{
			LinkedListQueue queuList = (LinkedListQueue)type;
			return queuList.dequeue();
		}
	}
	public T peek(){
		if(vectorBased){
			VectorQueue vectorQueue = (VectorQueue)type;
			return vectorQueue.peek();
		}
			
		else{
			LinkedListQueue queuList = (LinkedListQueue)type;
			return queuList.peek();
		}
	}
	public int size(){
		if(vectorBased){
			VectorQueue vectorQueue = (VectorQueue)type;
			return vectorQueue.getSize();
		}
			
		else{
			LinkedListQueue queuList = (LinkedListQueue)type;
			return queuList.getSize();
		}
	}
}
