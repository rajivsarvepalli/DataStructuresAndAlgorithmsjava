package trees;

import java.util.List;

public interface Tree<Key extends Comparable<Key>, Value> {
	public abstract void insert(Key key, Value data);
	public abstract void delete(Key key);
	public abstract Value get(Key key); 
	public abstract boolean contains(Key key);
	public abstract TreeNode<Key, Value> findMin(TreeNode<Key, Value> curRoot);
	public abstract void insert(List<Key> keys, List<Value> values);
	public abstract void delete(List<Key> keys);
	public abstract TreeNode<Key, Value> getRoot();
	//traversals
	public abstract List<TreeNode<Key,Value>> preOrder();
	public abstract List<TreeNode<Key,Value>> inOrder();
	public abstract List<TreeNode<Key,Value>> postOrder();
	public abstract int height();


}
