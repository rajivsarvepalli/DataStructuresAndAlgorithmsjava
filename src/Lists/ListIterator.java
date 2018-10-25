package Lists;

public class ListIterator<T> {
	
	protected ListNode<T> Node;
	
	public ListIterator(ListNode<T> Node) {
		this.Node = Node;
	}
	//check data to avoid dummy nodes
	public boolean isPastEnd() {
		return this.Node.next.getData() == null;
	}
	//check data to avoid dummy nodes
	public boolean isPastBeginning() {
		return this.Node.prev.getData() == null;
	}
	
	public T getCurData() {
		return this.Node.getData();
	}

	public void moveForward() {
		this.Node = this.Node.next;
	}
	
	public void moveBackward() {
		this.Node = this.Node.prev;

	}
}
