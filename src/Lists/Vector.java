package Lists;

public class Vector<T> implements Lists<T>{
	private T [] array;
	private int size;
	public Vector(int intialCapacity){
		this.array = (T[])new Object[intialCapacity];
		this.size = 0;
	}
	public Vector(){
		this.array = (T[])new Object[100];
		this.size = 0;

	}
	public void insert(T data){
		array[size++] = data;
	}
	public T get(int i){
		return this.array[i];
	}
	public void remove(int i){
		for(int index=i;index<this.size-1;index++){
			this.array[i] = array[i+1];
		}
		this.size--;
	}
	public void insertAt(int i, T data) {
		T temp = this.array[i];
		this.array[i] = data;
		this.array[i+1] = temp;
		for(int j=i+2;j<this.array.length-1;j++){
			array[j] = array[j+1];
		}
		
	}
}
