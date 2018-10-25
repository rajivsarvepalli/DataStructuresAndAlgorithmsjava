package Lists;


public class Stack<T> {
	private final boolean vectorBased;
	private Object type;
	public Stack(boolean vectorBased){
		this.vectorBased = vectorBased;
		if(vectorBased)
			type = new VectorStack();
		else
			type = new LinkedListStack();
	}
	//norammly linekd list based
	public Stack(){
		this.vectorBased = false;
		type = new LinkedListStack();
	}
	private class VectorStack{
		private T[] stack; 
		private int size;
		public VectorStack(){
			this.stack = (T[])new Object[100];
		}
		public void push(T data) {
			if(this.size==this.capacity()){
				this.resize(this.capacity()*2);
			}
			this.stack[size++] = data;
		}

		
		public T pop(){
			if(this.size<=this.capacity()/2){
				this.resize(this.capacity()/2);
			}
			this.size--;
			return this.stack[this.size];
		}
		public T peek(){
			int i =size-1;
			return this.stack[i];
		}
		
		public int capacity() {
			return this.stack.length;

		}
		public void resize(int newCapacity){
			if(size>newCapacity)
				return;
			T[] data = (T[])new Object[newCapacity];
			for(int i=0;i<this.size;i++){
				data[i]  =this.stack[i];
			}
			this.stack = data;

		}
		public int getSize(){
			return this.size;
		}
	}
	private class LinkedListStack{
		private LinkedList<T> stack;
		private int size;
		public LinkedListStack(){
			stack = new LinkedList<T>();
		}
		public void push(T data){
			stack.insertAtTail(data);
		}
		public T pop(){
			return stack.removeAtTail();
		}
		public T peek(){
			return stack.get(size-1);
		}
		public int getSize(){
			return stack.size();
		}
	}
	public void push(T data){
		if(vectorBased){
			VectorStack vectorStack = (VectorStack)type;
			vectorStack.push(data);
		}
		else{
			LinkedListStack linkedListStack = (LinkedListStack)type;
			linkedListStack.push(data);
		}
	}
	public T pop(){
		if(vectorBased){
			VectorStack vectorStack = (VectorStack)type;
			return vectorStack.pop();
		}
		else{
			LinkedListStack linkedListStack = (LinkedListStack)type;
			return linkedListStack.pop();
		}
	}
	public T peek(){
		if(vectorBased){
			VectorStack vectorStack = (VectorStack)type;
			return vectorStack.peek();
		}
		else{
			LinkedListStack linkedListStack = (LinkedListStack)type;
			return linkedListStack.peek();
		}
	}
	public int size(){
		if(vectorBased){
			VectorStack vectorStack = (VectorStack)type;
			return vectorStack.getSize();
		}
		else{
			LinkedListStack linkedListStack = (LinkedListStack)type;
			return linkedListStack.getSize();
		}
	}
}
