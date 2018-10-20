package trees;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Rajiv Sarvepalli
 *A basic Binary Search Tree that stores keys and data and is organized by keys <br>
 *This basic class is used to implement Avl Trees <br>
 *Implements the interface trees that defines a basis for every binary search tree including some methods<br>
 *that are used by the printing class
 * @param <Key> - the type of key that the tree will sorted by must implement Comparable
 * @param <Value> - the data each TreeNode will store
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value>{
	protected TreeNode<Key, Value> root;
	/**
	 * Inserts a key and value to the Binary Search Tree organized by the keys
	 * @param key - the key to be inserted
	 * @param data - the data to be inserted with the given key
	 * 
	 */
	public void insert(Key key, Value data) {
		this.root = insert(key,data, root);
	}
	public void insert(List<Key> keys, List<Value> values) {
		for(int i=0;i<keys.size();i++){
			this.root = insert(keys.get(i),values.get(i),root);
		}
	}
	
	/**
	 * used to help insert recursively
	 * @param data  -the data to be inserted 
	 * @param curNode - the current node
	 * @return new root of tree (goes up the tree from inserted item retuning each root of each subtree)
	 */
	protected TreeNode<Key, Value> insert(Key key,Value data, TreeNode<Key, Value> curNode) {
		//TODO: Implement this method
		if(this.root==null){
			this.root = new TreeNode<Key, Value>(key, data);
			return this.root;
		}
		if(key.compareTo(curNode.key)>0){
			if(curNode.right!=null)
				insert(key,data,curNode.right);
			else
				curNode.right = new TreeNode<Key, Value>(key,data);

		}
		else if(key.compareTo(curNode.key)<0){
			if(curNode.left!=null)
				insert(key,data,curNode.left);
			else
				curNode.left = new TreeNode<Key, Value>(key,data);
		}
		return curNode;
	}

	@Override
	public boolean contains(Key key) {
		return contains(key, root);
	}
	
	private boolean contains(Key key, TreeNode<Key, Value> curNode) {
		//TODO: Implement this method
		if(curNode==null)
			return false;
		else if(key.compareTo(curNode.key)==0)
			return true;
		else if(key.compareTo(curNode.key)>0)
			return contains(key,curNode.right);
		else
			return contains(key,curNode.left);

	}

	@Override
	public void delete(Key key) {
		this.root = delete(key, root);
	}
	
	public void delete(List<Key> keys) {
		for(int i=0;i<keys.size();i++){
			this.root = delete(keys.get(i),root);
		}
	}
	
	protected TreeNode<Key, Value> delete(Key key, TreeNode<Key, Value> curNode) {
		//TODO: Implement this method
		//fix
		//different scenarios
		//if bottom children jsut delete them
		//if root 
		//single child just remove and link parent of removed item to child of removed item
		//if double child sawp value at min of right side of removed node adn then delete swapped value
		if(curNode==null)
			return null;
		else if(key.compareTo(curNode.key)==0){
			//could omit this if statement
			if(curNode.right==null&&curNode.left==null){
				return null;
			}
			else if(curNode.left==null){
				return curNode.right;
			}
			else if(curNode.right==null)
				return curNode.left;
			else{
				//change to match key,vlaue of new tree
				TreeNode<Key, Value> temp = findMin(curNode.right);
				curNode.data = temp.data;
				curNode.key = temp.key;
				curNode.right = delete(temp.key,curNode.right);
			}
		}
		else if(key.compareTo(curNode.key)>0)
			curNode.right=  delete(key,curNode.right);
		else
			curNode.left = delete(key,curNode.left);
		return curNode;
	}
	public TreeNode<Key, Value> findMin(TreeNode<Key, Value> curNode) {
		if(curNode==null)
			return null;
		TreeNode<Key, Value> res = new TreeNode<Key, Value>(curNode.key,curNode.data);
		if(curNode.left!=null){
			res.left = curNode.left;
			TreeNode<Key, Value> datal = findMin(curNode.left);
			if(datal.key.compareTo(curNode.key)<0){
				res.key = datal.key;
				res.data = datal.data;
			}

		}
		if(curNode.right!=null){
			res.right =curNode.right;
			TreeNode<Key, Value> datar = findMin(curNode.right);
			if(datar.key.compareTo(res.key)<0){
				res.key = datar.key;
				res.data = datar.data;

			}
		}
		
		
		return res;
			
			
		
	}

	public Value get(Key key) {
		return get(key, this.root);
	}
	private Value get(Key key, TreeNode<Key, Value> curNode){
		if(curNode==null)
			return null;
		if(key.compareTo(curNode.key)>0){
			return get(key,curNode.right);
		}
		else if(key.compareTo(curNode.key)==0){
			return curNode.data;
		}
		else
			return get(key, curNode.right);
	}
	public TreeNode<Key, Value> getRoot(){
		final TreeNode<Key, Value> rootFinal =root;
		return rootFinal;
	}
	public List<TreeNode<Key,Value>> preOrder(){
		return preOrder(root);
	}
	
	private List<TreeNode<Key,Value>> preOrder(TreeNode<Key, Value> curNode){
		ArrayList<TreeNode<Key,Value>> list = new ArrayList<TreeNode<Key,Value>>();
		if(root==null) 
	            return list;
        list.add(root);
        list.addAll(preOrder(root.left));
        list.addAll(preOrder(root.right));
        return list;
	}
	public List<TreeNode<Key,Value>> inOrder(){
		return inOrder(root);
	}
	private List<TreeNode<Key,Value>> inOrder(TreeNode<Key,Value> root) {
        if(root==null) 
            return new ArrayList<TreeNode<Key,Value>>();
        List<TreeNode<Key,Value>> list = inOrder(root.left);
        list.add(root);
        list.addAll(inOrder(root.right));
        return list;
    }
	public List<TreeNode<Key,Value>> postOrder(){
		return postOrder(root);
	}
	public List<TreeNode<Key,Value>> postOrder(TreeNode<Key,Value> root){
		List<TreeNode<Key,Value>> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        list.addAll(postOrder(root.left));
        list.addAll(postOrder(root.right));
        list.add(root);

        return list;
	}
	public int height(){
		if(this.root.height!=0)
			return this.root.height;
		else
			return height(root);
	}
	private int height(TreeNode<Key, Value> curNode){
		if(curNode == null){
			return -1;
		}
		else return Math.max(height(curNode.left), height(curNode.right))+1; 
	}

	



}
