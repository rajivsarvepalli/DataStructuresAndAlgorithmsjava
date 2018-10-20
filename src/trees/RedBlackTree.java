package trees;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {
	protected TreeNode<Key, Value> root;
	/**
	 * A multi-insert for ease of use that use inserts each key of the list keys <br>
	 * and each corresponding value of the list values at the given key
	 * @param	keys - list of keys to be inserted
	 * @param values - list of values to be inserted at each key
	 */
	public void insert(List<Key> keys, List<Value> values) {
		for(int i=0;i<keys.size();i++){
			insert(keys.get(i),values.get(i));
		}
		
	}
	/**
	 * Inserts the given data at the given key
	 * @param key - the key to be inserted 
	 * @param data - the data to be stored at the given key
	 */
	public void insert(Key key, Value data) {
		this.root = insert(key, data, this.root);
		this.root.color = false;
	}
	protected TreeNode<Key, Value> insert(Key key, Value data, TreeNode<Key, Value> curNode) {
		//shoretr and simpler way of writing insert
		if(curNode==null)
			return new TreeNode<Key, Value>(key,data, true);
		else if(key.compareTo(curNode.key)>0)
			curNode.right = insert(key, data, curNode.right);
		else if(key.compareTo(curNode.key)<0)
			curNode.left = insert(key, data, curNode.left);
		else
			curNode.data =data;

        if(isRed(curNode.right)&&!isRed(curNode.left))
        	curNode = this.rotateLeft(curNode);
        if(isRed(curNode.left)&&(isRed(curNode.left.left)))
        	curNode = this.rotateRight(curNode);
        if(isRed(curNode.left)&&isRed(curNode.right))
        	this.flipColors(curNode);

        curNode.height = Math.max(height(curNode.right), height(curNode.left))+1;

		return (curNode);
	}
	/**
	 * A multi-deletion for ease of use that deletes each key of the list keys <br>
	 * and each corresponding value of the key
	 * @param	keys - list of keys to be deleted
	 */
	public void delete(List<Key> keys) {
		for(int i=0;i<keys.size();i++){
			delete(keys.get(i));
		}
		
	}
	/** Deletes the node that has the given key
	 * @param key -the key to be deleted 
	 * @throws key must not be null
	 */
	public void delete(Key key) {
		if(key==null)
			throw new IllegalArgumentException("key cannot be null");
		if (!isRed(root.left) && !isRed(root.right))
	            root.color = true;
		this.root = delete(key, this.root);
		if(root!=null)
			root.color = false;
	}
	/**
	 * Deletes the node by the key
	 * @param key - the key of the 
	 * @param curNode
	 * @return
	 */
	protected TreeNode<Key, Value> delete(Key key, TreeNode<Key, Value> curNode) {
		/* Call BST remove before balancing */
		if(key.compareTo(curNode.key)<0){
			if(!(isRed(curNode.left))&&!(isRed(curNode.left.left))){
				curNode = shiftRedLeft(curNode);
			}
			curNode.left = delete(key, curNode.left);
			
		}
			
		else{
			//could omit this if statement
			//right child null case
            if (key.compareTo(curNode.key) == 0 && (curNode.right == null))
                return null;
            //child both null then key not in tree and return null
            if(curNode.right==null&&curNode.left==null)
            	return curNode;
            if (isRed(curNode.left))
                curNode = rotateRight(curNode);
            if (!isRed(curNode.right) && !isRed(curNode.right.left))
            	curNode = shiftRedRight(curNode);
            if (key.compareTo(curNode.key) == 0) {
            	TreeNode<Key, Value> temp = findMin(curNode.right);
				curNode.data = temp.data;
				curNode.key = temp.key;
				//kinda of slow for simple process lots of if statements
				curNode.right = delete(temp.key,curNode.right);
            }
				//change to match key, value of new tree
				
            else{
    			curNode.right = delete(key,curNode.right);
            }
		}
		//restore red black tree properties after deletion
		curNode = restore(curNode);
		return curNode;
	}

	//a private rotate function that rotates right on the given node returning the new subroot
	private TreeNode<Key, Value> rotateRight(TreeNode<Key, Value> curNode) {
		TreeNode<Key, Value> tempL = curNode.left;
		curNode.left =tempL.right;
		tempL.right = curNode;
		if (this.root==curNode) {
            this.root = curNode.left;
        }
		tempL.color = tempL.right.color;
		tempL.right.color = true;
        
        curNode.height = Math.max(height(curNode.right), height(curNode.left))+1;
        tempL.height = Math.max(height(tempL.right), height(tempL.left))+1;
        //should return curNode or root? change later could be tempL
		return tempL;
		
	}
	//a private rotate function that rotates left on the given node returning the new subroot

	private TreeNode<Key, Value> rotateLeft(TreeNode<Key, Value> curNode){
		TreeNode<Key, Value> tempR = curNode.right;
		curNode.right =tempR.left;
		tempR.left = curNode;
        if (this.root==curNode) {
            this.root = curNode.right;
        }
        tempR.color = tempR.left.color;
        tempR.left.color = true;
        curNode.height = Math.max(height(curNode.right), height(curNode.left))+1;
        tempR.height = Math.max(height(tempR.right), height(tempR.left))+1;
        //should return curNode or root? change later could be tempR
		return tempR;
	}
	private TreeNode<Key, Value> restore(TreeNode<Key, Value> curNode) {
		//right side red rotate about
        if (isRed(curNode.right)){  
        	curNode = rotateLeft(curNode);
        }
        //if children both red flip em
        if (isRed(curNode.left) && isRed(curNode.right)){
        	flipColors(curNode);
        }
        //if the left side is double red rotate it about
        if (isRed(curNode.left) && isRed(curNode.left.left)){
        	curNode = rotateRight(curNode);
        }
        //making curNode red
        curNode.height = height(curNode.left) + height(curNode.right) + 1;
        return curNode;
    }

	//flips the color of the given node 
	private void flipColors(TreeNode<Key, Value> curNode){
		curNode.color = !curNode.color;
		if(curNode.left!=null)
			curNode.left.color = !curNode.left.color;
		if(curNode.right!=null)
			curNode.right.color = !curNode.right.color;

	}
	//shift the red of the given node down its left children (used for deletion)
	private TreeNode<Key, Value> shiftRedLeft(TreeNode<Key, Value> curNode) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);
		//flip colors of both it and children
        this.flipColors(curNode);
        if (isRed(curNode.right.left)) { 
        	curNode.right = rotateRight(curNode.right);
        	curNode = rotateLeft(curNode);
            flipColors(curNode);
        }
        return curNode;
    }
	//shift the red of the given node down its right children (used for deletion)
	private TreeNode<Key, Value> shiftRedRight(TreeNode<Key, Value> curNode) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);
		//flip colors of both it and children
        this.flipColors(curNode);
        if (isRed(curNode.left.left)) { 
        	curNode = rotateRight(curNode);
            flipColors(curNode);
        }
        return curNode;
    }
	//height method using -1 as null and therefore 0 for a leaf node
	private int height(TreeNode<Key, Value> node) {
		if(node == null) return -1;
		return node.height;
	}
	//determines whether the given node is red
	private boolean isRed(TreeNode<Key, Value> curNode){
		if(curNode==null)
			return false;
		return curNode.color;
	}
	/**
	 * Gets the data at the given key
	 * @param key - the key of node that will be accessed
	 */
	public Value get(Key key) {
		return get(key, root);
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
		else{
			return get(key,curNode.left);
		}
	}
	public boolean contains(Key key) {
		return contains(key,root);
	}
	private boolean contains(Key key, TreeNode<Key, Value> curNode) {
		if(curNode==null)
			return false;
		else if(key.compareTo(curNode.key)==0)
			return true;
		else if(key.compareTo(curNode.key)>0)
			return contains(key,curNode.right);
		else
			return contains(key,curNode.left);

	}
	public TreeNode<Key, Value> findMin(TreeNode<Key, Value> curNode) {
		 if (curNode == null) 
	            return null; 
		 	//compare curnode key to right and left mins to find overall min
	        TreeNode<Key, Value> res = curNode; 
	        TreeNode<Key, Value> leftres = findMin(curNode.left); 
	        TreeNode<Key, Value> rightres = findMin(curNode.right); 
	        
	        if (leftres!=null&&leftres.key.compareTo(res.key)<0) 
	            res = leftres; 
	        if (rightres!=null&&rightres.key.compareTo(res.key)>0) 
	            res = rightres; 
	        return res;
	}
	public TreeNode<Key, Value> getRoot() {
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
		return this.root.height;
	}

}
