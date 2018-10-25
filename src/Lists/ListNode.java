package Lists;


public class ListNode<T> {
	private T data;
	
	protected ListNode<T> next;
	protected ListNode<T> prev;
	
	public ListNode(T data) {
		this.data = data;
	}
	
	public T getData() {
		return this.data;
	}
	public String toString(){
		return this.data.toString();

	}
}
