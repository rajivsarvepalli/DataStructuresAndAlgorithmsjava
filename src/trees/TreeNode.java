package trees;

/**
 * 
 * @author Rajiv Sarvepalli
 * Basic TreeNode class to store a key and value for each node of any type of Binary Tree
 * @param <Key> - the type of key that will provided to order to tree (must implement comparable interface)
 * @param <Value> - the data that the TreeNode actually stores
 */
public class TreeNode<Key extends Comparable<Key>,Value>{
	protected TreeNode<Key, Value> left;
	protected TreeNode<Key, Value> right;
	protected Value data;
	protected int height;
	protected Key key;
	//small waste of space but for ease of use for RedBlackBSTs
	protected boolean color; 
	public TreeNode(){
		
	}
	public TreeNode(Key key, Value data){
		this.key = key;
		this.data = data;
	}
	public TreeNode(Key key, Value data, boolean color){
		this.key = key;
		this.data = data;
		this.color = color;
	}
	public TreeNode(Key key, Value data, int height){
		this.key = key;
		this.data = data;
		this.height = height;
	}
	public Key getKey(){
		return this.key;
	}
	public Value getData(){
		return this.data;
	}
}
