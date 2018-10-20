package trees;

import java.util.List;
/**
 * 
 * @author Rajiv Sarvepalli
 *A Avl Tree stores keys and data and is organized by keys and guarantees log n runtime on most operations <br>
 *This class extends BinarySearchTree <br>
 *Implements the interface trees that defines a basis for every binary search tree including some methods<br>
 *that are used by the printing class
 * @param <Key> - the type of key that the tree will sorted by must implement Comparable
 * @param <Value> - the data each TreeNode will store
 */
public class AvlTree<Key extends Comparable<Key>, Value> extends BinarySearchTree<Key, Value> implements Tree<Key, Value>{
	
	public void insert(Key key, Value data) {
		this.root = insert(key, data, root);
	}
	
	public void insert(List<Key> keys, List<Value> values){
		for(int i=0;i<keys.size();i++){
	        insert(keys.get(i),values.get(i));
		}
	}
	
	protected TreeNode<Key, Value> insert(Key key,Value data, TreeNode<Key, Value> curNode) {
		curNode = super.insert(key, data, curNode);
		//block of code:
		if(curNode == null) return null;
	    curNode.height = Math.max(height(curNode.right), height(curNode.left))+1;
		if(curNode.right!=null)
			curNode.right = balance(curNode.right);
		if(curNode.left!=null)
			curNode.left = balance(curNode.left);
		if(curNode==this.root)
			curNode = balance(curNode);
		return (curNode);
	}


	@Override
	public void delete(Key key) {
		this.root = delete(key, this.root);
	}
	protected TreeNode<Key, Value> delete(Key key, TreeNode<Key, Value> curNode) {
		/* Call BST remove before balancing */
		curNode = super.delete(key,  curNode);
		if(curNode == null) return null;
        curNode.height = Math.max(height(curNode.right), height(curNode.left))+1;
		//TODO: Update the height of curNode if necessary and call balance
        if(curNode.right!=null)
			curNode.right = balance(curNode.right);
		if(curNode.left!=null)
			curNode.left = balance(curNode.left);
		if(curNode==this.root)
			curNode = balance(curNode);
		return curNode;
	}
	
	/**
	 * Balances the given node. Assumes it is the lowest unbalanced node if unbalanced
	 * @param node
	 * @return the new root of this subtree
	 */
	private TreeNode<Key, Value> balance(TreeNode<Key, Value> curNode) {
		//if balance not in between -1 to 1 then balanced
		//TODO: Implement this method
		//currently works if put near insert such that this occurs on the newly inserted items starting at inserted item and workingup to root
		if(curNode==null)
			return null;
		//update height to reinforces changes made below tree
        curNode.height = Math.max(height(curNode.right), height(curNode.left))+1;
		int bF = this.balanceFactor(curNode);
		if(bF>1||bF<-1){
			if(bF<-1){
				if(height(curNode.left.left)>=height(curNode.left.right)){
					//curNode changes from this to return statemnet
					curNode =  rotateRight(curNode);
				}
				else{
					curNode.left = rotateLeft(curNode.left);
					curNode = rotateRight(curNode);
				}
			}
			else if(bF>1){
				if(height(curNode.right.right)>=height(curNode.right.left))
					curNode =  rotateLeft(curNode);
				else{
					curNode.right = rotateRight(curNode.right);
					curNode = rotateLeft(curNode);
				}
			}
		}
		//update height to ensure chnages made are actually 
        curNode.height = Math.max(height(curNode.right), height(curNode.left))+1;

		
        return curNode;
		
	}
	private TreeNode<Key, Value> rotateRight(TreeNode<Key, Value> curNode) {
		TreeNode<Key, Value> tempL = curNode.left;
		curNode.left =tempL.right;
		tempL.right = curNode;
        if (this.root==curNode) {
            this.root = tempL;
        }
        curNode.height = Math.max(height(curNode.right), height(curNode.left))+1;
        tempL.height = Math.max(height(tempL.right), height(tempL.left))+1;
		return tempL;
		
	}
	
	private TreeNode<Key, Value> rotateLeft(TreeNode<Key, Value> curNode){
		TreeNode<Key, Value> tempR = curNode.right;
		curNode.right =tempR.left;
		tempR.left = curNode;
        if (this.root==curNode) {
            this.root = curNode.right;
        }
        curNode.height = Math.max(height(curNode.right), height(curNode.left))+1;
        tempR.height = Math.max(height(tempR.right), height(tempR.left))+1;
		return tempR;
	}
	private int balanceFactor(TreeNode<Key, Value> node) {
		return (height(node.right)) - (height(node.left));
	}
	private int height(TreeNode<Key, Value> node) {
		if(node == null) return -1;
		return node.height;
	}
}
